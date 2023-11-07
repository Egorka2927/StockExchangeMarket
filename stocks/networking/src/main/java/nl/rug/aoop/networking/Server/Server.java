package nl.rug.aoop.networking.Server;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.networking.MessageHandling.MessageHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server class.
 */
@Slf4j
public class Server implements Runnable{

    private final int port;
    @Getter
    private int id;
    @Getter
    private ServerSocket serverSocket;
    private boolean running = false;
    private ExecutorService service;
    @Getter
    private MessageHandler messageHandler;
    @Getter
    private List<ClientHandler> clientHandlers;

    /**
     * Server constructor.
     * @param port Port used to connect.
     * @param messageHandler The message handler that handles the messages from the client.
     * @throws IOException When input/output are wrong.
     */
    public Server(int port, MessageHandler messageHandler) throws IOException {
        clientHandlers = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            serverSocket = new ServerSocket(0);
        }
        this.port = port;
        this.messageHandler = messageHandler;
        service = Executors.newCachedThreadPool();
        id = 0;
    }

    /**
     * Run method to keep the server going.
     */
    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                Socket clientSocket = serverSocket.accept();
                log.info("New connection from client");
                ClientHandler clientHandler = new ClientHandler(clientSocket, id, messageHandler);
                clientHandlers.add(clientHandler);
                service.submit(clientHandler);
                id++;
            } catch (IOException e) {
                log.error("Socket error " + e);
            }
        }
    }

    /**
     * Terminate server method.
     */
    public void terminate() {
        running = false;
        service.shutdown();
        service.shutdownNow();
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    public boolean isRunning() {
        return running;
    }
}
