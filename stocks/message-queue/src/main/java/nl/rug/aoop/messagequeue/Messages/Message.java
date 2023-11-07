package nl.rug.aoop.messagequeue.Messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Message class.
 */
@Getter
public class Message implements Comparable<Message> {
    private final String header;
    private final String body;
    private final LocalDateTime timestamp;

    /**
     * Message constructor.
     * @param messageHeader The header of the message.
     * @param messageBody The body of the message.
     */
    public Message(String messageHeader, String messageBody) {
        this.header = messageHeader;
        this.body = messageBody;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Second message constructor.
     * @param messageHeader Header of message.
     * @param messageBody Body of message.
     * @param timestamp Timestamp of message.
     */
    public Message(String messageHeader, String messageBody, LocalDateTime timestamp) {
        this.header = messageHeader;
        this.body = messageBody;
        this.timestamp = timestamp;
    }

    /**
     * Method that converts a String to JSON.
     * @return An output string.
     */
    public String convertToJSON() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(getClass(), new MessageAdapter());
        Gson gson = gsonBuilder.disableHtmlEscaping().create();
        return gson.toJson(this);
    }

    /**
     * Method that converts a String to a Message.
     * @param str The string being converted.
     * @return The converted message.
     */
    public static Message convertToMessage(String str) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Message.class, new MessageAdapter());
        Gson gson = gsonBuilder.disableHtmlEscaping().create();
        Message message = gson.fromJson(str, Message.class);
        return message;
    }

    @Override
    public int compareTo(Message o) {
        return getTimestamp().compareTo(o.getTimestamp());
    }
}