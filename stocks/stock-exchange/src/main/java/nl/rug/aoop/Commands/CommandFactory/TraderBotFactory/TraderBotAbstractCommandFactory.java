package nl.rug.aoop.Commands.CommandFactory.TraderBotFactory;

import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.TraderApplication.TraderBot;

/**
 * The trader bot command factory interface.
 */
public interface TraderBotAbstractCommandFactory {

    /**
     * Method to create a command handler.
     * @param traderBot The trader bot.
     * @return A command handler.
     */
    CommandHandler createCommandHandler(TraderBot traderBot);
}
