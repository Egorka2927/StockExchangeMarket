package nl.rug.aoop.Strategies;

import lombok.Getter;
import nl.rug.aoop.Stock.Stock;

import java.util.List;
import java.util.Map;

/**
 * Strategy abstarct class.
 */
public abstract class Strategy {

    /**
     * List of stocks.
     */
    @Getter
    protected List<Stock> stocks;

    /**
     * Map of trader stocks.
     */
    @Getter
    protected Map<String, Integer> traderStocks;

    /**
     * Trader funds.
     */
    @Getter
    protected double traderFunds;

    /**
     * Strategy constructor.
     * @param stocks List of stocks.
     * @param traderStocks Map of trader stocks.
     * @param traderFunds Trader funds.
     */
    public Strategy(List<Stock> stocks, Map<String, Integer> traderStocks, double traderFunds) {
        this.stocks = stocks;
        this.traderStocks = traderStocks;
        this.traderFunds = traderFunds;
    }
}
