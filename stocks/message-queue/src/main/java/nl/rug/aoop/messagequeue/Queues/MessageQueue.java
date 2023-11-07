package nl.rug.aoop.messagequeue.Queues;

import nl.rug.aoop.messagequeue.Messages.Message;

/**
 * MessageQueue interface.
 */
public interface MessageQueue {

    /**
     * Method to place a message in the queue.
     * @param message The message being placed.
     */
    void enqueue(Message message);

    /**
     * Method to remove a message from the queue.
     * @return A message.
     */
    Message dequeue();

    /**
     * Method to get the size of the queue.
     * @return The size of the queue.
     */
    int getSize();
}
