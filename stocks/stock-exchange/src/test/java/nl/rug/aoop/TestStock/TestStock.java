package nl.rug.aoop.TestStock;

import nl.rug.aoop.Stock.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestStock {

    private Stock stock;
    private String symbol;
    private String name;
    private long sharesOutstanding;
    private double price;

    @BeforeEach
    public void setUp() {
        symbol = "NVDA";
        name = "NVIDIA Corporation";
        sharesOutstanding = 10000;
        price = 250;
        stock = new Stock(symbol, name, sharesOutstanding, price);
    }

    @Test
    public void testStockConstructor() {
        assertEquals("NVDA", stock.getSymbol());
        assertEquals("NVIDIA Corporation", stock.getName());
        assertEquals(10000, stock.getSharesOutstanding());
        assertEquals(250, stock.getPrice());
    }
}
