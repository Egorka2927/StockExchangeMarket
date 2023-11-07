package nl.rug.aoop.messagequeue.TestMessages;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import nl.rug.aoop.messagequeue.Messages.Message;
import nl.rug.aoop.messagequeue.Messages.MessageAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

public class TestMessageAdapter {

    private Message message;
    private MessageAdapter messageAdapter;
    private String str;
    private LocalDateTime timestamp;

    @BeforeEach
    public void setUp() {
        timestamp = LocalDateTime.of(2023, 10, 11, 15, 46, 31, 234532435);
        messageAdapter = new MessageAdapter();
        message = new Message("Test Header", "Test Body", timestamp);
        str = "{\"header\":\"Test Header\"" +
                ",\"body\":\"Test Body\"" +
                ",\"timestamp\":\"2023-10-11T15:46:31.234532435\"}";
    }

    @Test
    public void testWrite() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Message.class, messageAdapter);
        Gson gsonConv = builder.create();
        String messageStr = gsonConv.toJson(message);
        assertEquals(messageStr, str);
    }

    @Test
    public void testRead() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Message.class, messageAdapter);
        Gson gsonConv = gsonBuilder.create();
        Message message1 = gsonConv.fromJson(str, Message.class);
        assertEquals(message1.getHeader(), message.getHeader());
        assertEquals(message1.getBody(), message.getBody());
        assertEquals(message1.getTimestamp(), message.getTimestamp());
    }
}
