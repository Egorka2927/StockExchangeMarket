package nl.rug.aoop.messagequeue.TestQueues;

import nl.rug.aoop.messagequeue.Messages.Message;
import nl.rug.aoop.messagequeue.Queues.MessageQueue;
import nl.rug.aoop.messagequeue.Queues.UnorderedQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestUnorderedQueue {

    MessageQueue queue = null;

    @BeforeEach
    public void setUp() {
        queue = new UnorderedQueue();
    }

    @Test
    public void testQueueConstructor() {
        assertNotNull(queue);
        assertEquals(0, queue.getSize());
    }

    @Test
    public void testQueueMethods() {
        List<Method> methods = List.of(queue.getClass().getDeclaredMethods());
        List<String> names = new ArrayList<>();
        
        for (Method m: methods) {
            names.add(m.getName());
        }

        assertTrue(names.contains("enqueue"));
        assertTrue(names.contains("dequeue"));
        assertTrue(names.contains("getSize"));
    }

    @Test
    public void testEnqueue() {
        Message message = new Message("header", "body");

        queue.enqueue(message);

        assertEquals(message, queue.dequeue());
    }

    @Test
    public void testNullInsertion() {
        queue.enqueue(null);
        assertEquals(0, queue.getSize());
    }

    @Test
    public void testNullHeader() {
        Message message = new Message(null, "Body");
        queue.enqueue(message);
        assertEquals(0, queue.getSize());
    }

    @Test
    public void testNullBody() {
        Message message = new Message("Header", null);
        queue.enqueue(message);
        assertEquals(0, queue.getSize());
    }

    @Test
    public void testNullHeaderBody() {
        Message message = new Message(null, null);
        queue.enqueue(message);
        assertEquals(0, queue.getSize());
    }

    @Test
    public void testDequeue() {
        assertNull(queue.dequeue());

        Message message1 = new Message("header", "body");
        Message message2 = new Message("header", "body");
        Message message3 = new Message("header", "body");

        queue.enqueue(message1);
        queue.enqueue(message2);
        queue.enqueue(message3);

        Message message = queue.dequeue();
        assertEquals(message, message1);
        assertEquals(2, queue.getSize());
    }

    @Test
    public void testGetSize() {
        Message message1 = new Message("header", "body");
        Message message2 = new Message("header", "body");
        Message message3 = new Message("header", "body");

        assertEquals(0, queue.getSize());

        queue.enqueue(message3);
        queue.enqueue(message1);
        queue.enqueue(message2);

        assertEquals(3, queue.getSize());

        queue.dequeue();
        assertEquals(2, queue.getSize());
        queue.dequeue();
        assertEquals(1, queue.getSize());
        queue.dequeue();
        assertEquals(0, queue.getSize());
    }

    @Test
    public void testQueueOrdering() {
        Message message1 = new Message("header", "body");
        Message message2 = new Message("header", "body");
        Message message3 = new Message("header", "body");

        queue.enqueue(message3);
        queue.enqueue(message1);
        queue.enqueue(message2);

        assertEquals(message3, queue.dequeue());
        assertEquals(message1, queue.dequeue());
        assertEquals(message2, queue.dequeue());
    }

}
