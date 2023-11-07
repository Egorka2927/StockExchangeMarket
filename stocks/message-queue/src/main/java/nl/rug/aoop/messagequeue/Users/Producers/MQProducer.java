package nl.rug.aoop.messagequeue.Users.Producers;

import nl.rug.aoop.messagequeue.Messages.Message;

/**
 * Message queue producer interface.
 */
public interface MQProducer {

    /**
     * Method to put a messsage.
     * @param message The message being put.
     */
    void put(Message message);
}
