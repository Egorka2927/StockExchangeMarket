package nl.rug.aoop.Commands.CommandFactory.TraderFactory;

import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.TraderApplication.TraderApplication;

/**
 * Trader command factory interface.
 */
public interface TraderAbstractCommandFactory {

    /**
     * Method to create a command handler.
     * @param traderApplication The trader appliation.
     * @return A command handler.
     */
    CommandHandler createCommandHandler(TraderApplication traderApplication);
}
