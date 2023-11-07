package nl.rug.aoop.networking.Client;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.networking.MessageHandling.MessageHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

/**
 * Client class.
 */
@Slf4j
public class Client implements Runnable{
    @Getter
    private InetSocketAddress address;
    @Getter
    private boolean running = false;
    private boolean connected = false;
    @Getter
    private Socket socket;
    @Getter
    private BufferedReader input;
    @Getter
    private PrintWriter output;
    @Getter
    private MessageHandler messageHandler;

    /**
     * Client contructor.
     * @param address Address used to connect.
     * @param messageHandler Handles incoming messages.
     * @throws IOException When input/output are wrong.
     */
    public Client(InetSocketAddress address, MessageHandler messageHandler) throws IOException {
        this.address = address;
        this.messageHandler = messageHandler;
        initialiseSocket(address);
    }

    /**
     * Method used to initialise a socket.
     * @param address Address to connect to.
     * @throws IOException When input/output are wrong.
     */
    private void initialiseSocket(InetSocketAddress address) throws IOException {
        socket = new Socket();
        socket.connect(address, 1000);
        if (!socket.isConnected()) {
            log.error("Socket has not been connected to a port " + address.getPort());
            throw new IOException("Socket has not been connected");
        }
        connected = true;
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                String serverResponse = input.readLine();
                messageHandler.handleMessage(serverResponse);
            } catch (SocketException e) {
                log.error("Server has been closed");
                terminate();
            } catch (IOException e) {
                log.error("Could not read the response from server");
            }
        }
    }

    /**
     * Method to print a string message.
     * @param message The message being printed.
     */
    public void sendMessage(String message) {
        if (message != null) {
            output.println(message);
        } else {
            log.error("Message cannot be null");
        }
    }

    /**
     * Method used to terminate the loop.
     */
    public void terminate() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }
}