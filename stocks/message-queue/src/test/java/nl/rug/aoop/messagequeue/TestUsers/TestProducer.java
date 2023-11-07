package nl.rug.aoop.messagequeue.TestUsers;

import nl.rug.aoop.messagequeue.Queues.MessageQueue;
import nl.rug.aoop.messagequeue.Queues.OrderedQueue;
import nl.rug.aoop.messagequeue.Queues.UnorderedQueue;
import nl.rug.aoop.messagequeue.Users.Producers.Producer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestProducer {
    private MessageQueue unorderedQueue;
    private MessageQueue orderedQueue;
    private Producer producerUnordered;
    private Producer producerOrdered;
    @BeforeEach
    public void setUp() {
        unorderedQueue = new UnorderedQueue();
        orderedQueue = new OrderedQueue();
        producerUnordered = new Producer(unorderedQueue);
        producerOrdered = new Producer(orderedQueue);
    }

    @Test
    public void testProducerConstructor() {
        assertNotNull(producerUnordered);
        assertNotNull(producerOrdered);
    }
}
