package nl.rug.aoop.networking;

import lombok.SneakyThrows;
import nl.rug.aoop.networking.Client.Client;
import nl.rug.aoop.networking.MessageHandling.MessageHandler;
import nl.rug.aoop.networking.Server.ClientHandler;
import nl.rug.aoop.networking.Server.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class TestClientHandler {
    private ClientHandler clientHandler;
    private InetSocketAddress address;
    private Client client;
    private Thread clientThread;

    private Server server;

    private Thread clientHandlerThread;

    private Socket socket;

    private MessageHandler messageHandler;

    private int id;

    @BeforeEach
    void setUp() throws IOException {
        messageHandler = Mockito.mock(MessageHandler.class);
        server = new Server(52778, messageHandler);
        address = new InetSocketAddress("localhost", 52778);
        client = new Client(address, messageHandler);
        clientThread = new Thread(client);
        id = server.getId();
        socket = client.getSocket();
        clientHandler = new ClientHandler(socket, id, messageHandler);
        clientHandlerThread = new Thread(clientHandler);
    }
    @Test
    void testConstructor() {
        assertNotNull(clientHandler);
        assertNotNull(socket);
        assertEquals(clientHandler.getMessageHandler(), messageHandler);
    }

    @Test
    void testId() {
        assertEquals(server.getId(), clientHandler.getId());
    }

    @SneakyThrows
    @Test
    void testHandlerRunning() {
        clientHandlerThread.start();
        Thread.sleep(1000);
        assertTrue(clientHandler.isRunning());
    }

    @Test
    void testHandlerTerminate() {
        clientHandler.terminate();
        assertFalse(clientHandler.isRunning());
    }
}
