package nl.rug.aoop.messagequeue.Users.Consumers;

import nl.rug.aoop.messagequeue.Messages.Message;
import nl.rug.aoop.messagequeue.Queues.MessageQueue;

/**
 * Consumer class.
 */
public class Consumer implements MQConsumer {
    private MessageQueue messageQueue;

    /**
     * Consumer constructor.
     * @param messageQueue Used to store messages.
     */
    public Consumer(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    @Override
    public Message poll() {
        return messageQueue.dequeue();
    }
}
