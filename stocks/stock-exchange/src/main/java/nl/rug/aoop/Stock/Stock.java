package nl.rug.aoop.Stock;

import lombok.Getter;
import lombok.Setter;
import nl.rug.aoop.model.Stock.StockDataModel;

import java.beans.ConstructorProperties;

/**
 * The stock class.
 */
public class Stock implements StockDataModel {
    @Getter
    private final String symbol;
    @Getter
    private final String name;
    @Getter
    private long sharesOutstanding;
    @Setter
    private double price;
    private double marketCap;

    /**
     * Stock constructor.
     * @param symbol The stock sybol.
     * @param name The stock name.
     * @param sharesOutstanding The stock ammount.
     * @param price The stock price.
     */
    @ConstructorProperties({"symbol", "name", "sharesOutstanding", "initialPrice"})
    public Stock(String symbol, String name, long sharesOutstanding, double price) {
        this.symbol = symbol;
        this.name = name;
        this.sharesOutstanding = sharesOutstanding;
        this.price = price;
        this.marketCap = sharesOutstanding * price;
    }

    /**
     * Getter for symbol.
     * @return The symbol string.
     */
    @Override
    public String getSymbol() {
        return symbol;
    }

    /**
     * Getter fot name.
     * @return The name string.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Getter for amount.
     * @return The amount.
     */
    @Override
    public long getSharesOutstanding() {
        return sharesOutstanding;
    }

    /**
     * Getter for marketCap.
     * @return The marketCap.
     */
    @Override
    public double getMarketCap() {
        return marketCap;
    }

    /**
     * Getter for price.
     * @return The stock price.
     */
    @Override
    public double getPrice() {
        return price;
    }
}
