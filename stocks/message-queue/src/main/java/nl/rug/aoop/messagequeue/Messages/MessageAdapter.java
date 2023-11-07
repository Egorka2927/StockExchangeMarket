package nl.rug.aoop.messagequeue.Messages;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Message adapter class.
 */
public class MessageAdapter extends TypeAdapter<Message> {

    /**
     * Method to write.
     * @param jsonWriter The writer.
     * @param message The message being written.
     * @throws IOException Exception if input output fails.
     */
    @Override
    public void write(JsonWriter jsonWriter, Message message) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("header");
        jsonWriter.value(message.getHeader());

        jsonWriter.name("body");
        jsonWriter.value(message.getBody());

        jsonWriter.name("timestamp");
        jsonWriter.value(message.getTimestamp().toString());

        jsonWriter.endObject();

        jsonWriter.close();
    }

    /**
     * Methood to read.
     * @param jsonReader The JSON reader.
     * @return A message.
     * @throws IOException Exception if input output fails.
     */
    @Override
    public Message read(JsonReader jsonReader) throws IOException {
        LocalDateTime timestamp;
        String header;
        String body;

        jsonReader.beginObject();
        jsonReader.nextName();
        header = jsonReader.nextString();
        jsonReader.nextName();
        body = jsonReader.nextString();
        jsonReader.nextName();
        timestamp = LocalDateTime.parse(jsonReader.nextString());
        jsonReader.endObject();

        Message message = new Message(header, body, timestamp);
        return message;
    }
}
