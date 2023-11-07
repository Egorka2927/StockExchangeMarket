package nl.rug.aoop.StockApplication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.Commands.CommandFactory.StockFactory.StockCommandHandlerFactory;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.Commands.CommandMessageHandler;
import nl.rug.aoop.messagequeue.Messages.Message;
import nl.rug.aoop.messagequeue.Queues.ThreadSafeQueue;
import nl.rug.aoop.Stock.Stock;
import nl.rug.aoop.model.Stock.StockDataModel;
import nl.rug.aoop.StockOrder.StockOrder;
import nl.rug.aoop.Trader.Trader;
import nl.rug.aoop.model.StockApplication.StockExchangeDataModel;
import nl.rug.aoop.model.Trader.TraderDataModel;
import nl.rug.aoop.networking.Server.Server;
import nl.rug.aoop.util.YamlLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Stock application class.
 */
@Slf4j
public class StockApplication implements StockExchangeDataModel, Runnable{
    @Getter
    private final Server server;
    private final ThreadSafeQueue messageQueue;
    private boolean isRunning = false;
    private boolean traderInfoSent = false;
    @Getter
    private List<Stock> stocks;
    @Getter
    private List<Trader> connectedTraders;
    @Getter
    private Queue<StockOrder> bids;
    @Getter
    private Queue<StockOrder> asks;
    private final ExecutorService service;
    private final StockCommandHandlerFactory stockCommandHandlerFactory = new StockCommandHandlerFactory();
    private CommandHandler commandHandler;
    private CommandMessageHandler commandMessageHandler;

    /**
     * Stock application constructor.
     * @param port The server port.
     * @throws IOException If input output error.
     */
    public StockApplication(int port) throws IOException {
        connectedTraders = new ArrayList<>();
        stocks = new ArrayList<>();
        bids = new PriorityBlockingQueue<>(1, (stockOrder1, stockOrder2)
                -> StockOrder.compare(stockOrder2, stockOrder1));
        asks = new PriorityBlockingQueue<>(1, StockOrder::compare);
        commandHandler = stockCommandHandlerFactory.createCommandHandler(this);
        messageQueue = new ThreadSafeQueue();
        commandMessageHandler = new CommandMessageHandler(commandHandler, messageQueue);
        server = new Server(port, commandMessageHandler);
        service = Executors.newCachedThreadPool();
        service.submit(server);
        log.info("Server started at port " + server.getPort());
        initializeStocksTraders();
    }

    /**
     * Method to find trader by id.
     * @param id Trader id.
     * @return The trader found.
     */
    public Trader findTraderById(String id) {
        for (Trader trader : connectedTraders) {
            if (trader.getId().equals(id)) {
                return trader;
            }
        }
        return null;
    }

    /**
     * Method to resolve a stock order.
     * @param stockOrder The stock order in question.
     */
    public void resolve(StockOrder stockOrder) {
        if (stockOrder.getOrderType().equals("Sell")) {
            resolveSellOrder(stockOrder);
        } else {
            resolveBuyOrder(stockOrder);
        }
        updateStockPrice(stocks, stockOrder);
    }

    /**
     * Method to get the best possible offer.
     * @param stockOrder The stock order.
     * @param orders The asks / bids queue (depends on the stock order).
     * @return The best offer.
     */
    public StockOrder getBestOffer(StockOrder stockOrder, Queue<StockOrder> orders) {
        for(var iterator : orders) {
            if (stockOrder.getPricePerStock() > iterator.getPricePerStock() &&
                    !stockOrder.getId().equals(iterator.getId()) &&
                    stockOrder.getStockType().equals(iterator.getStockType())) {
                return iterator;
            }
        }
        return null;
    }

    /**
     * Method to resolve a sell order.
     * @param stockOrder The stock order in question.
     */
    public void resolveSellOrder(StockOrder stockOrder) {
        if (bids.isEmpty()) {
            asks.add(stockOrder);
        } else {
            StockOrder bestOffer = getBestOffer(stockOrder, bids);
            if (bestOffer != null) {
                resolveMatchingSellOrder(stockOrder, bestOffer);
            } else {
                asks.add(stockOrder);
            }
        }
    }

    /**
     * Method to resolve a buy order.
     * @param stockOrder The stock order in question.
     */
    public void resolveBuyOrder(StockOrder stockOrder) {
        if (asks.isEmpty()) {
            bids.add(stockOrder);
        } else {
            StockOrder bestOffer = getBestOffer(stockOrder, asks);
            if (bestOffer != null) {
                resolveMatchingBuyOrder(stockOrder, bestOffer);
            } else {
                bids.add(stockOrder);
            }
        }
    }

    /**
     * Method to resolve a matching sell order.
     * @param stockOrder The stock order.
     * @param bestOffer The best offer found.
     */
    public void resolveMatchingSellOrder(StockOrder stockOrder, StockOrder bestOffer) {
        Trader seller = findTraderById(stockOrder.getId());
        Trader buyer = findTraderById(stockOrder.getId());
        resolveMatchingOrder(stockOrder, bestOffer, buyer, seller);
    }

    /**
     * Method to resolve a matching buy order.
     * @param stockOrder The stock order.
     * @param bestOffer The best offer found.
     */
    public void resolveMatchingBuyOrder(StockOrder stockOrder, StockOrder bestOffer) {
        Trader seller = findTraderById(stockOrder.getId());
        Trader buyer = findTraderById(stockOrder.getId());
        resolveMatchingOrder(bestOffer, stockOrder, buyer, seller);
    }

    /**
     * Method to resolve a matching offer.
     * @param stockOrder The stock order.
     * @param bestOffer The best offer.
     * @param buyer The buyer.
     * @param seller The seller.
     */
    public void resolveMatchingOrder(StockOrder stockOrder, StockOrder bestOffer, Trader buyer, Trader seller) {
        int amountPurchased = bestOffer.getAmount(), amountSold = stockOrder.getAmount();
        int amountStocksBuyer, amountStocksSeller;
        if (buyer.getOwnedShares().get(stockOrder.getStockType()) == null) {
            amountStocksBuyer = 0;
        } else {
            amountStocksBuyer = buyer.getOwnedShares().get(stockOrder.getStockType());
        }
        if (seller.getOwnedShares().get(stockOrder.getStockType()) == null) {
            amountStocksSeller = 0;
        } else {
            amountStocksSeller = seller.getOwnedShares().get(stockOrder.getStockType());
        }
        resolveAmounts(stockOrder, buyer, seller, amountSold, amountPurchased, amountStocksBuyer, amountStocksSeller);
        removeRedundantOrders();
        buyer.addTransaction("Stock bought: " + stockOrder.getStockType()
                + ", stock amount: " + stockOrder.getAmount() + ", stock price: " + stockOrder.getPricePerStock());
        seller.addTransaction("Stock bought: " + stockOrder.getStockType()
                + ", stock amount: " + stockOrder.getAmount() + ", stock price: " + stockOrder.getPricePerStock());
    }

    /**
     * Method to resolve the amount difference.
     * @param stockOrder The stock order.
     * @param buyer The buyer.
     * @param seller The seller.
     * @param amountSold The amount sold.
     * @param amountPurchased The amount purchased.
     * @param amountStocksBuyer The amount of stocks the buyer has.
     * @param amountStocksSeller The amount of stocks the seller has.
     */
    public void resolveAmounts(StockOrder stockOrder, Trader buyer, Trader seller, int amountSold, int amountPurchased,
                               int amountStocksBuyer, int amountStocksSeller) {
        double buyerFunds = buyer.getFunds();
        double sellerFunds = seller.getFunds();
        buyer.getOwnedShares().remove(stockOrder.getStockType());
        seller.getOwnedShares().remove(stockOrder.getStockType());
        if (amountSold >= amountPurchased) {
            buyer.getOwnedShares().put(stockOrder.getStockType(), amountStocksBuyer + amountPurchased);
            seller.getOwnedShares().put(stockOrder.getStockType(), amountStocksSeller + amountPurchased);
            buyer.setFunds(buyerFunds - amountPurchased * stockOrder.getPricePerStock());
            seller.setFunds(sellerFunds + amountPurchased * stockOrder.getPricePerStock());
        } else {
            buyer.getOwnedShares().put(stockOrder.getStockType(), amountStocksBuyer + amountSold);
            seller.getOwnedShares().put(stockOrder.getStockType(), amountStocksSeller + amountSold);
            buyer.setFunds(buyerFunds - amountSold * stockOrder.getPricePerStock());
            seller.setFunds(sellerFunds + amountSold * stockOrder.getPricePerStock());
        }
    }

    /**
     * Method to update the price of a stock.
     * @param stocks The stocks list.
     * @param stockOrder The stock order.
     */
    public void updateStockPrice(List<Stock> stocks, StockOrder stockOrder) {
        for (Stock stock : stocks) {
            if (stock.getSymbol().equals(stockOrder.getStockType())) {
                stock.setPrice(stockOrder.getPricePerStock());
            }
        }
    }

    /**
     * Method to check if an order is invalid.
     * @param stockOrder The stock order.
     * @return True if invalid false otherwise.
     */
    public Boolean checkInvalidOrder(StockOrder stockOrder) {
        for (var iterator : connectedTraders) {
            if (iterator.getId().equals(stockOrder.getId())) {
                if (stockOrder.getStockType().equals("Buy")) {
                    if (iterator.getFunds() <= stockOrder.getAmount() * stockOrder.getPricePerStock()) {
                        return false;
                    }
                }
                if (stockOrder.getStockType().equals("Sell")) {
                    if (!iterator.getOwnedShares().containsKey(stockOrder.getStockType()) ||
                            iterator.getOwnedShares().get(stockOrder.getStockType()) < stockOrder.getAmount()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Method to remove invalid orders.
     */
    public void removeRedundantOrders() {
        asks.removeIf(this::checkInvalidOrder);
        bids.removeIf(this::checkInvalidOrder);
    }

    /**
     * Method to inform the clients.
     */
    public void informClients() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String stocksInfo = gson.toJson(stocks);

        for (int i = 1; i < 10; i++) {
            Message stocksMessage = new Message("UpdateTraderStocksInfoCommand", stocksInfo);
            Message putMessage = new Message("MqPutCommand", stocksMessage.convertToJSON());
            server.getClientHandlers().get(i).sendMessage(putMessage.convertToJSON());

            String traderInfo = connectedTraders.get(i - 1).convertInfoToJSON();
            Message traderInfoMessage = new Message("UpdateTraderInfoCommand", traderInfo);
            Message putMessage2 = new Message("MqPutCommand", traderInfoMessage.convertToJSON());
            server.getClientHandlers().get(i).sendMessage(putMessage2.convertToJSON());
        }
    }

    /**
     * Method to send information to the traders.
     */
    public void sendInfoTraders() {
        for (Trader trader : connectedTraders) {
            String traderInfo = trader.convertInfoToJSON();
            Message message = new Message("CreateBotCommand", traderInfo);
            Message putMessage = new Message("MqPutCommand", message.convertToJSON());
            System.out.println("Sent trader info!");
            server.getClientHandlers().get(0).sendMessage(putMessage.convertToJSON());
        }
    }

    /**
     * Method to initialize the traders.
     */
    public void initializeStocksTraders() {
        Path pathStocks = Paths.get("stocks/data/stocks.yaml");
        Path pathTraders = Paths.get("stocks/data/traders.yaml");

        YamlLoader yamlLoaderStocks = new YamlLoader(pathStocks);
        YamlLoader yamlLoaderTraders = new YamlLoader(pathTraders);

        try {
            stocks = yamlLoaderStocks.loadList(Stock.class);
            connectedTraders = yamlLoaderTraders.loadList(Trader.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to run the stock application.
     */
    @Override
    public void run() {
        log.info("Stock application is running");
        isRunning = true;
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (server.getClientHandlers().size() == 10) {
                    System.out.println("Informing traders");
                    informClients();
                }
            }
        };
        timer.schedule(timerTask, 0, 1000);
        while (isRunning) {
            if (server.getClientHandlers().size() == 1 && !traderInfoSent) {
                System.out.println("Sending trader info");
                sendInfoTraders();
                traderInfoSent = true;
            }
            commandMessageHandler.dequeue();
        }
    }

    /**
     * Method to get a stock by its index.
     * @param index The index of the stock that should be accessed.
     * @return The stock.
     */
    @Override
    public StockDataModel getStockByIndex(int index) {
        return stocks.get(index);
    }

    /**
     * Method to get the number of stocks.
     * @return The number.
     */
    @Override
    public int getNumberOfStocks() {
        return stocks.size();
    }

    /**
     * Method to get a trader by its index.
     * @param index The index of the trader that should be accessed.
     * @return The trader.
     */
    @Override
    public TraderDataModel getTraderByIndex(int index) {
        return connectedTraders.get(index);
    }

    /**
     * Method to get the number of traders.
     * @return The number.
     */
    @Override
    public int getNumberOfTraders() {
        return connectedTraders.size();
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}