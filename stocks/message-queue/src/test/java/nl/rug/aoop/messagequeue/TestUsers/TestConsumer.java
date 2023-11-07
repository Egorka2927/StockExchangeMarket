package nl.rug.aoop.messagequeue.TestUsers;

import nl.rug.aoop.messagequeue.Queues.MessageQueue;
import nl.rug.aoop.messagequeue.Queues.OrderedQueue;
import nl.rug.aoop.messagequeue.Queues.UnorderedQueue;
import nl.rug.aoop.messagequeue.Users.Consumers.Consumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestConsumer {
    private MessageQueue unorderedQueue;
    private MessageQueue orderedQueue;
    private Consumer consumerUnordered;
    private Consumer consumerOrdered;
    @BeforeEach
    public void setUp() {
        unorderedQueue = new UnorderedQueue();
        orderedQueue = new OrderedQueue();
        consumerUnordered = new Consumer(unorderedQueue);
        consumerOrdered = new Consumer(orderedQueue);
    }

    @Test
    public void testConsumerConstructor() {
        assertNotNull(consumerUnordered);
        assertNotNull(consumerOrdered);
    }
}
