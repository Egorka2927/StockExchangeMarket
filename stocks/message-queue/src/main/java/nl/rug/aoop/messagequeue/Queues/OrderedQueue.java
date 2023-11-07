package nl.rug.aoop.messagequeue.Queues;

import nl.rug.aoop.messagequeue.Messages.Message;

import java.util.*;

/**
 * Ordered queue class.
 */
public class OrderedQueue implements MessageQueue {
    private PriorityQueue<Message> orderedQueue;

    /**
     * Ordered queue constructor.
     */
    public OrderedQueue() {
        orderedQueue = new PriorityQueue<>(new QueueComparator());
    }

    /**
     * Method to place a message into the queue.
     * @param message The message being placed.
     */
    @Override
    public void enqueue(Message message) {
        if (message != null && message.getHeader() != null && message.getBody() != null) {
            orderedQueue.add(message);
        }
    }

    /**
     * Method to remove a message from the queue.
     * @return Returns the message being removed.
     */
    @Override
    public Message dequeue() {
        if (orderedQueue.isEmpty()) {
            return null;
        } else {
            return orderedQueue.poll();
        }
    }

    /**
     * Method to get the size of the queue.
     * @return Returns the size of the queue.
     */
    @Override
    public int getSize() {
        return orderedQueue.size();
    }
}
