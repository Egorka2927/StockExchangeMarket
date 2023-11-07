package nl.rug.aoop.networking.Server;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.networking.MessageHandling.MessageHandler;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

/**
 * Client handler class.
 */
@Slf4j
public class ClientHandler implements Runnable{
    @Getter
    private Socket socket;
    @Getter
    private int id;
    private BufferedReader input;
    @Getter
    private PrintWriter output;
    private boolean running = false;
    @Getter
    private MessageHandler messageHandler;

    /**
     * Client handler constructor.
     * @param socket Socket used to connect.
     * @param id Client handler id.
     * @param messageHandler The message handler that handles the messages from the client.
     * @throws IOException When input/output are wrong.
     */
    public ClientHandler(Socket socket, int id, MessageHandler messageHandler) throws IOException {
        this.id = id;
        this.socket = socket;
        this.messageHandler = messageHandler;
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(socket.getOutputStream(), true);
    }

    /**
     * Run method that keeps going.
     */
    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                String clientResponse = input.readLine();
                if (clientResponse == null) {
                    terminate();
                    break;
                }
                messageHandler.handleMessage(clientResponse);
            } catch (SocketException e) {
                log.error("User has disconnected");
                terminate();
            } catch (IOException e) {
                log.error("Could not read the message from client with id " + id);
            }
        }
    }

    /**
     * Method to send a message.
     * @param message The message being sent.
     */
    public void sendMessage(String message) {
        if (message != null) {
            output.println(message);
        } else {
            log.error("Message cannot be null");
        }
    }

    /**
     * Terminate method.
     */
    public void terminate() {
        running = false;
        try {
            socket.close();
        } catch (IOException e) {
            log.error("Could not close the socket", e);
        }
    }

    public boolean isRunning() {
        return running;
    }
}
