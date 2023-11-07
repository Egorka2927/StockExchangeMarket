package nl.rug.aoop.Trader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;
import nl.rug.aoop.model.Trader.TraderDataModel;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The trader class.
 */
public class Trader implements TraderDataModel {
    @Getter
    private final String name;
    @Getter
    private final String id;
    @Getter
    private Map<String, Integer> ownedShares;
    @Getter
    @Setter
    private double funds;
    private List<String> transactions;

    /**
     * The trader constructor.
     * @param id Trader id.
     * @param name Trader name.
     * @param funds Trader funds.
     * @param ownedShares Trader map of shares.
     */
    @ConstructorProperties({"id", "name", "funds", "ownedShares"})
    public Trader(String id, String name, double funds, Map<String, Integer> ownedShares) {
        this.id = id;
        this.name = name;
        this.funds = funds;
        this.ownedShares = ownedShares;
        transactions = new ArrayList<>();
    }

    /**
     * Method to convert to JSON.
     * @return The converted string.
     */
    public String convertInfoToJSON() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.disableHtmlEscaping().create();
        return gson.toJson(this);
    }

    /**
     * Method to convert from JSON.
     * @param str The JSON string.
     * @return The converted trader.
     */
    public static Trader convertToTrader(String str) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Trader trader = gson.fromJson(str, Trader.class);
        return trader;
    }

    /**
     * Method to add a transaction.
     * @param transaction The transaction being added.
     */
    public void addTransaction(String transaction) {
        transactions.add(transaction);
    }

    /**
     * Getter for id.
     * @return The id string.
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Getter for name.
     * @return The name string.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Gettet for funds.
     * @return The funds.
     */
    @Override
    public double getFunds() {
        return funds;
    }

    /**
     * Getter for stocks list.
     * @return The stocks list.
     */
    @Override
    public List<String> getOwnedStocks() {
        List<String> listOfStocks = new ArrayList<>();
        for (String key : ownedShares.keySet()) {
            listOfStocks.add(key);
        }
        return listOfStocks;
    }

    @Override
    public int getNumberOfOwnedShares(String stockSymbol) {
        return ownedShares.get(stockSymbol);
    }
}
