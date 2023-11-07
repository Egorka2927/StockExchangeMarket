package nl.rug.aoop.Strategies;

import lombok.Getter;
import nl.rug.aoop.messagequeue.Messages.Message;
import nl.rug.aoop.Stock.Stock;
import nl.rug.aoop.StockOrder.StockOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Our current trading strategy class. We can have multiple.
 */
public class TradingStrategy extends Strategy{

    @Getter
    private String id;

    /**
     * Trading strategy constructor.
     * @param stocks Stocks list.
     * @param traderStocks Trader stocks map.
     * @param traderFunds Trader funds.
     * @param id Id.
     */
    public TradingStrategy(List<Stock> stocks, Map<String, Integer> traderStocks, double traderFunds, String id) {
        super(stocks, traderStocks, traderFunds);
        this.id = id;
    }

    /**
     * Method to randomly choose an order type.
     * @return A message with the command for header and the stock order as body.
     */
    public Message chooseOrderType() {
        int min = 0;
        int max = 1;
        int type = min + (int)(Math.random() * ((max - min) + 1));

        if (type == 0) {
            return createBuyOrder();
        }

        return createSellOrder();
    }

    /**
     * Method to create a buy order.
     * @return The buy order as a message.
     */
    public Message createBuyOrder() {
        int min = 0;
        int max = stocks.size() - 1;
        int randomStockIndex = min + (int) (Math.random() * ((max - min) + 1));
        Stock randomStock = stocks.get(randomStockIndex);

        double dMin = randomStock.getPrice() * 1.5;
        double dMax = randomStock.getPrice() * 1.9;
        double randomPricePerStock = dMin + (Math.random() * ((dMax - dMin) + 1));

        min = 1;
        max = (int) (traderFunds / randomPricePerStock);
        int randomAmount = min + (int) (Math.random() * ((max - min) + 1));

        StockOrder stockOrder = new StockOrder("Buy", randomStock.getSymbol(),
                randomAmount, randomPricePerStock, id);
        return new Message("ResolveStockOrderCommand", stockOrder.convertToJson());
    }

    /**
     * Method to create a sell order.
     * @return The sell order as a messge.
     */
    public Message createSellOrder() {
        int min = 0;
        int max = traderStocks.size() - 1;
        int randomStockIndex = min + (int)(Math.random() * ((max - min) + 1));

        String randomStockKey = null;
        List<String> keys = new ArrayList<>(traderStocks.keySet());

        randomStockKey = keys.get(randomStockIndex);

        Stock randomStock = null;

        for (Stock stock : stocks) {
            if (stock.getSymbol().equals(randomStockKey)) {
                randomStock = stock;
            }
        }

        min = 1;
        max = traderStocks.get(randomStock.getSymbol());
        int randomAmount = min + (int)(Math.random() * ((max - min) + 1));

        double dMin = randomStock.getPrice() * 0.5;
        double dMax = randomStock.getPrice() * 0.9;
        double randomPricePerStock = dMin + (Math.random() * ((dMax - dMin) + 1));

        StockOrder stockOrder = new StockOrder("Sell", randomStock.getSymbol(),
                randomAmount, randomPricePerStock, id);
        return new Message("ResolveStockOrderCommand", stockOrder.convertToJson());
    }
}
