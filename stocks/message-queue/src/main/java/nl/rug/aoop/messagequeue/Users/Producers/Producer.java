package nl.rug.aoop.messagequeue.Users.Producers;

import nl.rug.aoop.messagequeue.Messages.Message;
import nl.rug.aoop.messagequeue.Queues.MessageQueue;

/**
 * Producer class.
 */
public class Producer implements MQProducer {

    private MessageQueue messageQueue;

    /**
     * Producer constructor.
     * @param messageQueue The message queue used by the producer.
     */
    public Producer(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    /**
     * Method to put the message in the message queue.
     * @param message The message being put.
     */
    @Override
    public void put(Message message) {
        messageQueue.enqueue(message);
    }
}
