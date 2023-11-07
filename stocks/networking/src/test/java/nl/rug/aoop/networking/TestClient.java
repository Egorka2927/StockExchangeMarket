package nl.rug.aoop.networking;

import lombok.SneakyThrows;
import nl.rug.aoop.networking.Client.Client;
import nl.rug.aoop.networking.MessageHandling.MessageHandler;

import static org.junit.jupiter.api.Assertions.*;

import nl.rug.aoop.networking.Server.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;

public class TestClient {
    private InetSocketAddress address;
    private Server server;
    private Client client;
    private MessageHandler messageHandler;
    private Thread clientThread;

    @BeforeEach
    public void setUp() throws IOException {
        messageHandler = Mockito.mock(MessageHandler.class);
        server = new Server(52778, messageHandler);
        address = new InetSocketAddress("localhost", 52778);
        messageHandler = Mockito.mock(MessageHandler.class);
        client = new Client(address, messageHandler);
        clientThread = new Thread(client);
    }

    @Test
    public void testClientConstructor() {
        assertNotNull(client);
        assertNotNull(client.getSocket());
        assertEquals(client.getMessageHandler(), messageHandler);
    }

    @Test
    public void testClientSendMessage(){
        String message = "Hello world";
        client.sendMessage(message);
    }

    @Test
    public void testAddress() {
        assertEquals(client.getAddress().getPort(), client.getSocket().getPort());
    }

    @SneakyThrows
    @Test
    public void testClientRun() {
        clientThread.start();
        Thread.sleep(1000);
        assertTrue(client.isRunning());
    }

    @Test
    public void testTerminate() {
        client.terminate();
        assertFalse(client.isRunning());
    }
}
