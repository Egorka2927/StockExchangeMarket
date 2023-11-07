package nl.rug.aoop.messagequeue.TestCommands;

import nl.rug.aoop.messagequeue.Commands.MqPutCommand;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;

import nl.rug.aoop.messagequeue.Messages.Message;
import nl.rug.aoop.messagequeue.Queues.MessageQueue;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

public class TestMqPutCommand {
    private MqPutCommand mqPutCommand;
    private Map<String, Object> map;

    @BeforeEach
    public void setUp() {
        mqPutCommand = new MqPutCommand();
        map = new HashMap<>();
    }

    @Test
    public void testExecute() {
        MessageQueue messageQueue = Mockito.mock(MessageQueue.class);
        Message message = new Message("Test Header", "Test Body");
        map.put("Queue", messageQueue);
        map.put("MqPutCommandMessage", message.convertToJSON());
        mqPutCommand.execute(map);
        Mockito.verify(messageQueue, times(1)).enqueue(any(Message.class));
    }
}
