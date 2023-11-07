package nl.rug.aoop.messagequeue.Queues;

import nl.rug.aoop.messagequeue.Messages.Message;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Unordered queue class.
 */
public class UnorderedQueue implements MessageQueue {

    private Queue<Message> unorderedQueue;

    /**
     * Unordered queue constructor.
     */
    public UnorderedQueue() {
        unorderedQueue = new LinkedList<>();
    }

    /**
     * Method to place a message in the queue.
     * @param message The message being placed.
     */
    @Override
    public void enqueue(Message message) {
        if (message != null && message.getHeader() != null && message.getBody() != null) {
            unorderedQueue.add(message);
        }
    }

    /**
     * Message to remove a message from the queue.
     * @return The message being removed.
     */
    @Override
    public Message dequeue() {
        if (unorderedQueue.isEmpty()) {
            return null;
        } else {
            return unorderedQueue.poll();
        }
    }

    /**
     * Method to get the size of the queue.
     * @return The size of the queue.
     */
    @Override
    public int getSize() {
        return unorderedQueue.size();
    }
}
