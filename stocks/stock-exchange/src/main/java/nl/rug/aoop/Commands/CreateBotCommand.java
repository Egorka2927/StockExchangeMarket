package nl.rug.aoop.Commands;

import nl.rug.aoop.command.Command;
import nl.rug.aoop.Trader.Trader;
import nl.rug.aoop.TraderApplication.TraderApplication;
import nl.rug.aoop.TraderApplication.TraderBot;

import java.io.IOException;
import java.util.Map;

/**
 * Crate bot command class.
 */
public class CreateBotCommand implements Command {
    private TraderApplication traderApplication;

    /**
     * Create bot command contructor.
     * @param traderApplication The trader application.
     */
    public CreateBotCommand(TraderApplication traderApplication) {
        this.traderApplication = traderApplication;
    }

    /**
     * Method to execute the commands.
     * @param params Map of names and commands.
     */
    @Override
    public void execute(Map<String, Object> params) {
        String traderInfo = (String) params.remove("CreateBotCommandMessage");
        Trader trader = Trader.convertToTrader(traderInfo);
        try {
            TraderBot traderBot = new TraderBot(trader, traderApplication.getPort(), traderApplication.getHost());
            traderApplication.getTraderBots().add(traderBot);
            traderApplication.getExecutorService().submit(traderBot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
