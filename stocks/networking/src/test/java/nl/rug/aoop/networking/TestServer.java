package nl.rug.aoop.networking;


import lombok.SneakyThrows;
import nl.rug.aoop.networking.Client.Client;
import nl.rug.aoop.networking.MessageHandling.MessageHandler;
import nl.rug.aoop.networking.Server.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.InetSocketAddress;

import static org.junit.jupiter.api.Assertions.*;

public class TestServer {
    private Server server;
    private Thread serverThread;
    private InetSocketAddress address;
    private MessageHandler messageHandler;

    @BeforeEach
    public void setUp() throws IOException {
        messageHandler = Mockito.mock(MessageHandler.class);
        server = new Server(52778, messageHandler);
        address = new InetSocketAddress("localhost", 52778);
        serverThread = new Thread(server);
    }

    @Test
    void testConstructor() {
        assertNotNull(server);
        assertNotNull(server.getServerSocket());
        assertNotNull(server.getMessageHandler());
    }

    @Test
     public void testPort() {
        assertEquals(server.getPort(), server.getServerSocket().getLocalPort());
    }

    @SneakyThrows
    @Test
    public void testConnection() {
        MessageHandler messageHandler = Mockito.mock(MessageHandler.class);
        Client client = new Client(address, messageHandler);
    }

    @SneakyThrows
    @Test
    public void testMultipleConnections() {
        MessageHandler messageHandler1 = Mockito.mock(MessageHandler.class);
        MessageHandler messageHandler2 = Mockito.mock(MessageHandler.class);
        MessageHandler messageHandler3 = Mockito.mock(MessageHandler.class);

        Client client1 = new Client(address, messageHandler1);
        Client client2 = new Client(address, messageHandler2);
        Client client3 = new Client(address, messageHandler3);
    }

    @SneakyThrows
    @Test
    public void testRunning() {
        serverThread.start();
        Thread.sleep(1000);
        assertTrue(server.isRunning());
    }

    @SneakyThrows
    @Test
    public void testTerminate() {
        server.terminate();
        assertFalse(server.isRunning());
    }
}
