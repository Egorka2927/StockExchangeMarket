package nl.rug.aoop.command;

import java.util.HashMap;
import java.util.Map;

/**
 * Command handler class.
 */
public class CommandHandler {
    private Map<String, Command> commands = new HashMap<>();

    /**
     * Method to add command.
     * @param commandName Name of the command.
     * @param command A command.
     */
    public void addCommand(String commandName, Command command) {
        if (commandName != null && command != null) {
            commands.put(commandName, command);
        }
    }

    /**
     * Method to execute a command.
     * @param commandName Name of the command.
     * @param params Map of commands.
     */
    public void executeCommand(String commandName, Map<String, Object> params) {
        Command command = commands.get(commandName);
        if (command != null) {
            command.execute(params);
        }
    }

    public Map getCommands() {
        return commands;
    }
}
