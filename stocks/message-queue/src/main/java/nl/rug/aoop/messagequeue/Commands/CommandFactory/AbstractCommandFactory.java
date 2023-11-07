package nl.rug.aoop.messagequeue.Commands.CommandFactory;

import nl.rug.aoop.command.CommandHandler;

/**
 * Abstract command factory interface.
 */
public interface AbstractCommandFactory {

    /**
     * Method to create a command handler.
     * @return returns the created command handler.
     */
    CommandHandler createCommandHandler();
}
