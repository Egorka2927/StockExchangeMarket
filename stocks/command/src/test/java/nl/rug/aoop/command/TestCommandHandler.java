package nl.rug.aoop.command;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestCommandHandler {
    private CommandHandler commandHandler;

    @BeforeEach
    public void setUp() {
        commandHandler = new CommandHandler();
    }

    @Test
    public void testAddCommand() {
        Command command = Mockito.mock(Command.class);
        commandHandler.addCommand("MqPutCommand", command);
        assertEquals(commandHandler.getCommands().get("MqPutCommand"), command);
    }

    @Test
    public void testAddNullCommand() {
        commandHandler.addCommand(null, null);
        assertFalse(commandHandler.getCommands().containsKey(null));
    }

    @Test
    public void testExecuteCommand() {
        Command command = Mockito.mock(Command.class);
        commandHandler.addCommand("MqPutCommand", command);
        Map<String, Object> map = new HashMap<>();
        commandHandler.executeCommand("MqPutCommand", map);
        Mockito.verify(command).execute(map);
    }

    @Test
    public void testGetCommands() {
        Command command = Mockito.mock(Command.class);
        Map<String, Command> commands = new HashMap<>();
        commands.put("MqPutCommand", command);
        commandHandler.addCommand("MqPutCommand", command);
        assertEquals(commands, commandHandler.getCommands());
    }
}
