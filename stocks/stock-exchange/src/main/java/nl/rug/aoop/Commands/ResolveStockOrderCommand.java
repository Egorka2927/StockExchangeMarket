package nl.rug.aoop.Commands;

import nl.rug.aoop.command.Command;
import nl.rug.aoop.StockApplication.StockApplication;
import nl.rug.aoop.StockOrder.StockOrder;

import java.util.Map;

/**
 * Resolve stock order command class.
 */
public class ResolveStockOrderCommand implements Command {
    private StockApplication stockApplication;

    /**
     * Resolve stock order command constructor.
     * @param stockApplication The stock application.
     */
    public ResolveStockOrderCommand(StockApplication stockApplication) {
        this.stockApplication = stockApplication;
    }

    /**
     * Method to execute commands.
     * @param params Map of names and commands.
     */
    @Override
    public void execute(Map<String, Object> params) {
        String stockOrderInfo = (String) params.remove("ResolveStockOrderCommandMessage");
        StockOrder stockOrder = StockOrder.convertFromJson(stockOrderInfo);
        stockApplication.resolve(stockOrder);
    }
}
