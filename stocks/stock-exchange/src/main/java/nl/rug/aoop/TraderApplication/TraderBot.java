package nl.rug.aoop.TraderApplication;

import lombok.Getter;
import lombok.Setter;
import nl.rug.aoop.Commands.CommandFactory.TraderBotFactory.TraderBotCommandHandlerFactory;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.Commands.CommandMessageHandler;
import nl.rug.aoop.messagequeue.Messages.Message;
import nl.rug.aoop.messagequeue.Queues.ThreadSafeQueue;
import nl.rug.aoop.Stock.Stock;
import nl.rug.aoop.Strategies.TradingStrategy;
import nl.rug.aoop.Trader.Trader;
import nl.rug.aoop.networking.Client.Client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Trader bot class.
 */
public class TraderBot implements Runnable{
    @Setter
    private Trader trader;
    private Client client;
    @Getter
    @Setter
    private List<Stock> stocks;
    private ExecutorService executorService;
    private final TraderBotCommandHandlerFactory traderBotCommandHandlerFactory = new TraderBotCommandHandlerFactory();
    private ThreadSafeQueue messageQueue;
    private CommandHandler commandHandler;
    private CommandMessageHandler commandMessageHandler;
    private boolean isRunning = false;

    /**
     * Trader bot constructor.
     * @param host The host to connect.
     * @param trader The trader.
     * @param port The port to connect.
     * @throws IOException Wrong input output.
     */
    public TraderBot(Trader trader, int port, String host) throws IOException {
        stocks = new ArrayList<>();
        this.trader = trader;
        commandHandler = traderBotCommandHandlerFactory.createCommandHandler(this);
        messageQueue = new ThreadSafeQueue();
        commandMessageHandler = new CommandMessageHandler(commandHandler, messageQueue);
        executorService = Executors.newCachedThreadPool();
        this.client = new Client(new InetSocketAddress(host, port), commandMessageHandler);
        executorService.submit(client);
    }

    /**
     * Method to send a stock order.
     */
    public void sendStockOrder() {
        TradingStrategy strategy = new TradingStrategy(stocks, trader.getOwnedShares(),
                trader.getFunds(), trader.getId());
        Message stockOrderMessage = strategy.chooseOrderType();
        Message putMessage = new Message("MqPutCommand", stockOrderMessage.convertToJSON());
        client.sendMessage(putMessage.convertToJSON());
    }

    /**
     * Method to run the client.
     */
    @Override
    public void run() {
        System.out.println("Trader bot " + trader.getId() + " running");
        isRunning = true;
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            private int count = 1;
            @Override
            public void run() {
                if (!stocks.isEmpty()) {
                    System.out.println("Sending a stock order " + count);
                    sendStockOrder();
                    count++;
                }
            }
        };
        int timeWaitMin = 1;
        int timeWaitMax = 4;
        int randomTimeWait = timeWaitMin + (int)(Math.random() * ((timeWaitMax - timeWaitMin) + 1));
        timer.schedule(timerTask, randomTimeWait * 1000, 1000);
        while (isRunning) {
            commandMessageHandler.dequeue();
        }
    }
}