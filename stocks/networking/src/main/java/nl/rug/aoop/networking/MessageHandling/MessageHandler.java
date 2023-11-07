package nl.rug.aoop.networking.MessageHandling;

/**
 * Message handler interface.
 */
public interface MessageHandler {

    /**
     * Method to handle a message.
     * @param message The message being handled.
     */
    void handleMessage(String message);
}
