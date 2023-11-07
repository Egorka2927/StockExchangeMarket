package nl.rug.aoop.Commands.CommandFactory.StockFactory;

import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.StockApplication.StockApplication;

/**
 * Stock Command Factory interface.
 */
public interface StockAbstractCommandFactory {

    /**
     * Method to create a command handler.
     * @param stockApplication The stock aplication.
     * @return Returns a command handler.
     */
    CommandHandler createCommandHandler(StockApplication stockApplication);
}
