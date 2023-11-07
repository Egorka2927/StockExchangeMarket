package nl.rug.aoop.Commands.CommandFactory.TraderFactory;

import nl.rug.aoop.Commands.CreateBotCommand;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.Commands.MqPutCommand;
import nl.rug.aoop.TraderApplication.TraderApplication;

/**
 * Trader command handler factory class.
 */
public class TraderCommandHandlerFactory implements TraderAbstractCommandFactory {
    @Override
    public CommandHandler createCommandHandler(TraderApplication traderApplication) {
        CommandHandler commandHandler = new CommandHandler();
        commandHandler.addCommand("MqPutCommand", new MqPutCommand());
        commandHandler.addCommand("CreateBotCommand", new CreateBotCommand(traderApplication));
        return commandHandler;
    }
}
