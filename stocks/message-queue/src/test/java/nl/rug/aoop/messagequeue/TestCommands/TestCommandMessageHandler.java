package nl.rug.aoop.messagequeue.TestCommands;
import static org.junit.jupiter.api.Assertions.*;

import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.Commands.CommandMessageHandler;
import nl.rug.aoop.messagequeue.Messages.Message;
import nl.rug.aoop.messagequeue.Queues.MessageQueue;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

public class TestCommandMessageHandler {
    private CommandHandler commandHandler;
    private MessageQueue messageQueue;
    private CommandMessageHandler commandMessageHandler;

    @BeforeEach
    public void setUp() {
        commandHandler = new CommandHandler();
        messageQueue = Mockito.mock(MessageQueue.class);
        commandMessageHandler = new CommandMessageHandler(commandHandler, messageQueue);
    }
    @Test
    public void testConstructor() {
        assertEquals(commandHandler, commandMessageHandler.getCommandHandler());
        assertEquals(messageQueue, commandMessageHandler.getMap().get("Queue"));
    }
    @Test
    public void testHandleMessage() {
        Message message = new Message("Test Header", "Test Body");
        Message networkMessage = new Message("Test Network Header", message.convertToJSON());
        commandMessageHandler.handleMessage(networkMessage.convertToJSON());
        assertEquals((Message.convertToMessage((String)commandMessageHandler.getMap().get(networkMessage.getHeader()
                + "Message"))).getTimestamp(), message.getTimestamp());
    }
}
