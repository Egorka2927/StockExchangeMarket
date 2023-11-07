package nl.rug.aoop.Commands.CommandFactory.StockFactory;

import nl.rug.aoop.Commands.ResolveStockOrderCommand;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.Commands.MqPutCommand;
import nl.rug.aoop.StockApplication.StockApplication;

/**
 * Stock command handler factory class.
 */
public class StockCommandHandlerFactory implements StockAbstractCommandFactory {
    @Override
    public CommandHandler createCommandHandler(StockApplication stockApplication) {
        CommandHandler commandHandler = new CommandHandler();
        commandHandler.addCommand("MqPutCommand", new MqPutCommand());
        commandHandler.addCommand("ResolveStockOrderCommand", new ResolveStockOrderCommand(stockApplication));
        return commandHandler;
    }
}
