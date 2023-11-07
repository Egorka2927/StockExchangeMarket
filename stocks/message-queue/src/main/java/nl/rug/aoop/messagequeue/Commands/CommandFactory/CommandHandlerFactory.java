package nl.rug.aoop.messagequeue.Commands.CommandFactory;

import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.Commands.MqPutCommand;

/**
 * Command handler factory class.
 */
public class CommandHandlerFactory implements AbstractCommandFactory{

    @Override
    public CommandHandler createCommandHandler() {
        CommandHandler commandHandler = new CommandHandler();
        commandHandler.addCommand("MqPutCommand", new MqPutCommand());
        return commandHandler;
    }
}
