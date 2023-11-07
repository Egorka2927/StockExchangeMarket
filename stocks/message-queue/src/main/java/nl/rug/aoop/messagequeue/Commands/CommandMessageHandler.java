package nl.rug.aoop.messagequeue.Commands;

import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.Messages.Message;
import nl.rug.aoop.messagequeue.Queues.MessageQueue;
import nl.rug.aoop.networking.MessageHandling.MessageHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Command message handler class.
 */
public class CommandMessageHandler implements MessageHandler {
    private CommandHandler commandHandler;
    private Map<String, Object> map = new HashMap<>();
    private MessageQueue messageQueue;

    /**
     * Command message handler constructor.
     * @param commandHandler The command handler.
     * @param messageQueue The message queue.
     */
    public CommandMessageHandler(CommandHandler commandHandler, MessageQueue messageQueue) {
        this.commandHandler = commandHandler;
        this.messageQueue = messageQueue;
        map.put("Queue", messageQueue);
    }

    /**
     * Method to handle a message.
     * @param jsonMessage The message being handled.
     */
    @Override
    public void handleMessage(String jsonMessage) {
        Message messageFromNetworkProducer = Message.convertToMessage(jsonMessage);
        String messageToHandle = messageFromNetworkProducer.getBody();
        map.put(messageFromNetworkProducer.getHeader() + "Message", messageToHandle);
        commandHandler.executeCommand(messageFromNetworkProducer.getHeader(), map);
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    /**
     * Method to dequeue from the queue and handle the message.
     */
    public void dequeue() {
        Message message = messageQueue.dequeue();
        if (message != null) {
            handleMessage(message.convertToJSON());
        }
    }

    public Map getMap() {
        return map;
    }
}

