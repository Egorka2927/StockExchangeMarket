package nl.rug.aoop.command;

import java.util.Map;

/**
 * Command class.
 */
public interface Command {

    /**
     * Method that executes a command.
     * @param params Map of names and commands.
     */
    void execute(Map<String, Object> params);
}
