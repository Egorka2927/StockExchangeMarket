package nl.rug.aoop.StockOrder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;

/**
 * The stock order class.
 */
public class StockOrder {

    private String orderType;
    private String stockType;
    @Setter
    private int amount;
    private double pricePerStock;
    @Getter
    private String id;

    /**
     * The stock order constructor.
     * @param orderType The order type.
     * @param stockType The stock type.
     * @param amount The stock amount.
     * @param pricePerStock The stock price.
     * @param id The stock id.
     */
    public StockOrder(String orderType, String stockType, int amount, double pricePerStock, String id) {
        this.orderType = orderType;
        this.stockType = stockType;
        this.amount = amount;
        this.pricePerStock = pricePerStock;
        this.id = id;
    }

    /**
     * Method to convert to JSON.
     * @return The converted string.
     */
    public String convertToJson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.disableHtmlEscaping().create();
        return gson.toJson(this);
    }

    /**
     * Method to convert from JSON>.
     * @param str The JSON string.
     * @return The converted  stock order.
     */
    public static StockOrder convertFromJson(String str) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.disableHtmlEscaping().create();
        StockOrder stockOrder = gson.fromJson(str, StockOrder.class);
        return stockOrder;
    }

    /**
     * Comparator method.
     * @param stockOrder1 First order.
     * @param stockOrder2 Second order.
     * @return The result of the comparison.
     */
    public static int compare(StockOrder stockOrder1, StockOrder stockOrder2) {
        if (stockOrder1.getPricePerStock() < stockOrder1.getPricePerStock()) {
            return 1;
        } else if (stockOrder1.getPricePerStock() > stockOrder2.getPricePerStock()) {
            return -1;
        } else {
            return 0;
        }
    }

    public String getOrderType() {
        return orderType;
    }

    public String getStockType() {
        return stockType;
    }

    public int getAmount() {
        return amount;
    }

    public double getPricePerStock() {
        return pricePerStock;
    }
}
