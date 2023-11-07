package nl.rug.aoop.TestStockOrder;

import nl.rug.aoop.StockOrder.StockOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestStockOrder {

    private StockOrder stockOrder;
    private String orderType;
    private String stockType;
    private int amount;
    private double pricePerStock;
    private String id;

    @BeforeEach
    public void setUp() {
        orderType = "Buy";
        stockType = "NVDA";
        amount = 1000;
        pricePerStock = 250;
        id = "Bot 1";
        stockOrder = new StockOrder(orderType, stockType, amount, pricePerStock, id);
    }

    @Test
    public void testStockOrderConstructor() {
        assertEquals("Buy", stockOrder.getOrderType());
        assertEquals("NVDA", stockOrder.getStockType());
        assertEquals(1000, stockOrder.getAmount());
        assertEquals(250, stockOrder.getPricePerStock());
        assertEquals("Bot 1", stockOrder.getId());
    }
}
