package nl.rug.aoop.messagequeue.Users.Producers;

import nl.rug.aoop.messagequeue.Messages.Message;
import nl.rug.aoop.networking.Client.Client;

/**
 * Network producer class.
 */
public class NetworkProducer implements MQProducer {
    private final Client client;

    /**
     * Network producer constructor.
     * @param client The client.
     */
    public NetworkProducer(Client client) {
        this.client = client;
    }

    /**
     * Method to convert the message to a network message.
     * @param message The message being converted.
     * @return The converted message.
     */
    public Message convertToNetworkMessage(Message message) {
        Message networkMessage = new Message("MqPutCommand", message.convertToJSON());
        return networkMessage;
    }

    /**
     * Method to send a message.
     * @param message The message being put.
     */
    @Override
    public void put(Message message) {
        Message networkMessage = convertToNetworkMessage(message);
        client.sendMessage(networkMessage.convertToJSON());
    }

    public Client getClient() {
        return client;
    }
}