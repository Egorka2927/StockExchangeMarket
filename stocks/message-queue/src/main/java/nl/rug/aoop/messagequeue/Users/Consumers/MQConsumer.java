package nl.rug.aoop.messagequeue.Users.Consumers;

import nl.rug.aoop.messagequeue.Messages.Message;

/**
 * Message queue consumer interface.
 */
public interface MQConsumer {

    /**
     * Method to get the message.
     * @return A message.
     */
    Message poll();
}
