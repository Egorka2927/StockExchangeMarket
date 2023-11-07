package nl.rug.aoop.messagequeue.TestUsers;
import nl.rug.aoop.messagequeue.Messages.Message;
import nl.rug.aoop.messagequeue.Users.Producers.NetworkProducer;
import nl.rug.aoop.networking.Client.Client;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class TestNetworkProducer {
    private Client client;
    private NetworkProducer networkProducer;
    @BeforeEach
    public void setUp() {
        client = Mockito.mock(Client.class);
        networkProducer = new NetworkProducer(client);
    }
    @Test
    public void testConstructor() {
        assertEquals(networkProducer.getClient(), client);
    }

    @Test
    public void testConvertToNetworkMessage() {
        Message message = new Message("Test Header", "Test Body");
        Message networkMessage = networkProducer.convertToNetworkMessage(message);
        assertEquals(networkMessage.getHeader(), "MqPutCommand");
        assertEquals(message.getTimestamp(), Message.convertToMessage(networkMessage.getBody()).getTimestamp());
    }
}
