package nl.rug.aoop.Commands.CommandFactory.TraderBotFactory;

import nl.rug.aoop.Commands.UpdateTraderInfoCommand;
import nl.rug.aoop.Commands.UpdateTraderStocksInfoCommand;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.Commands.MqPutCommand;
import nl.rug.aoop.TraderApplication.TraderBot;

/**
 * Trader bot command handler factory class.
 */
public class TraderBotCommandHandlerFactory implements TraderBotAbstractCommandFactory {
    @Override
    public CommandHandler createCommandHandler(TraderBot traderBot) {
        CommandHandler commandHandler = new CommandHandler();
        commandHandler.addCommand("MqPutCommand", new MqPutCommand());
        commandHandler.addCommand("UpdateTraderStocksInfoCommand",
                new UpdateTraderStocksInfoCommand(traderBot));
        commandHandler.addCommand("UpdateTraderInfoCommand", new UpdateTraderInfoCommand(traderBot));
        return commandHandler;
    }
}
