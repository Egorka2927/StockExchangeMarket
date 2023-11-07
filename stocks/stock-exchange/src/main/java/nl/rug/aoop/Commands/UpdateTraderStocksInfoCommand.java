package nl.rug.aoop.Commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import nl.rug.aoop.command.Command;
import nl.rug.aoop.Stock.Stock;
import nl.rug.aoop.TraderApplication.TraderBot;

import java.util.List;
import java.util.Map;

/**
 * Update trader stocks info class.
 */
public class UpdateTraderStocksInfoCommand implements Command {
    private TraderBot traderBot;

    /**
     * Update trader stocks info command constructor.
     * @param traderBot The trader bot.
     */
    public UpdateTraderStocksInfoCommand(TraderBot traderBot) {
        this.traderBot = traderBot;
    }

    /**
     * Method to execute commands.
     * @param params Map of names and commands.
     */
    @Override
    public void execute(Map<String, Object> params) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String stocksInfo = (String) params.remove("UpdateTraderStocksInfoCommandMessage");
        List<Stock> stocks = gson.fromJson(stocksInfo, new TypeToken<List<Stock>>(){}.getType());
        traderBot.setStocks(stocks);
    }
}
