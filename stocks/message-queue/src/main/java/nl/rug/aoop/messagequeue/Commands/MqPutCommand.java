package nl.rug.aoop.messagequeue.Commands;

import nl.rug.aoop.command.Command;
import nl.rug.aoop.messagequeue.Messages.Message;
import nl.rug.aoop.messagequeue.Queues.MessageQueue;

import java.util.Map;

/**
 * Message queue put command.
 */
public class MqPutCommand implements Command {

    /**
     * Method to execute a command.
     * @param params Map of names and commands.
     */
    @Override
    public void execute(Map<String, Object> params) {
        MessageQueue queue = (MessageQueue) params.get("Queue");
        String str = (String) params.remove("MqPutCommandMessage");
        Message message = Message.convertToMessage(str);
        queue.enqueue(message);
    }
}