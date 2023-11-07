package nl.rug.aoop.TraderApplication;

import lombok.Getter;
import nl.rug.aoop.Commands.CommandFactory.TraderFactory.TraderCommandHandlerFactory;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.Commands.CommandMessageHandler;
import nl.rug.aoop.messagequeue.Queues.ThreadSafeQueue;
import nl.rug.aoop.networking.Client.Client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Trader application class.
 */
public class TraderApplication implements Runnable{
    private final int port;
    @Getter
    private final String host;
    private List<TraderBot> traderBots;
    @Getter
    private ExecutorService executorService;
    private final TraderCommandHandlerFactory traderCommandHandlerFactory = new TraderCommandHandlerFactory();
    private ThreadSafeQueue messageQueue;
    private CommandHandler commandHandler;
    private CommandMessageHandler commandMessageHandler;
    private Client client;
    private boolean isRunning = false;

    /**
     * Trader application constructor.
     * @param host The client host.
     * @param port The client port.
     * @throws IOException Wrong input output.
     */
    public TraderApplication(String host, int port) throws IOException {
        this.port = port;
        this.host = host;
        commandHandler = traderCommandHandlerFactory.createCommandHandler(this);
        messageQueue = new ThreadSafeQueue();
        commandMessageHandler = new CommandMessageHandler(commandHandler, messageQueue);
        traderBots = new ArrayList<>();
        executorService = Executors.newCachedThreadPool();
        client = new Client(new InetSocketAddress(host, port), commandMessageHandler);
        executorService.submit(client);
    }

    /**
     * Method to run the trader application.
     */
    @Override
    public void run() {
        isRunning = true;
        System.out.println("Trader application is running");
        while (isRunning) {
            commandMessageHandler.dequeue();
        }
    }

    public List<TraderBot> getTraderBots() {
        return traderBots;
    }

    public int getPort() {
        return port;
    }
}
