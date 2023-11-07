package nl.rug.aoop.Commands;

import nl.rug.aoop.command.Command;
import nl.rug.aoop.Trader.Trader;
import nl.rug.aoop.TraderApplication.TraderBot;

import java.util.Map;

/**
 * Update trader info command class.
 */
public class UpdateTraderInfoCommand implements Command {
    private TraderBot traderBot;

    /**
     * Update trader info command constructor.
     * @param traderBot The trader bot
     */
    public UpdateTraderInfoCommand(TraderBot traderBot) {
        this.traderBot = traderBot;
    }

    /**
     * Method to execute commands.
     * @param params Map of names and commands.
     */
    @Override
    public void execute(Map<String, Object> params) {
        String traderInfo = (String) params.remove("UpdateTraderInfoCommandMessage");
        Trader trader = Trader.convertToTrader(traderInfo);
        traderBot.setTrader(trader);
    }
}
