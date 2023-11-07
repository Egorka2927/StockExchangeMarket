package nl.rug.aoop.TestStrategies;

import nl.rug.aoop.Stock.Stock;
import nl.rug.aoop.Strategies.Strategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class TestStrategy {

    Strategy strategy;
    private List<Stock> stocks;
    private Map<String, Integer> traderStocks;
    private double funds;
    Stock stock1;
    Stock stock2;

    @BeforeEach
    public void setUp() {
        stocks = new ArrayList<>();
        traderStocks = new HashMap<>();
        stock1 = new Stock("NVDA", "NVIDIA Corporation", 10000, 250);
        stock2 = new Stock("AMD", "Advanced Micro Devices Corporation", 5000, 140);
        stocks.add(stock1);
        stocks.add(stock2);
        traderStocks.put("NVDA", 2500);
        traderStocks.put("AMD", 1000);
        funds = 100000;
        strategy = new Strategy(stocks, traderStocks, funds) {
        };
    }

    @Test
    public void testStrategyConstructor() {
        assertEquals(stock1, strategy.getStocks().get(0));
        assertEquals(stock2, strategy.getStocks().get(1));
        assertEquals(2500, strategy.getTraderStocks().get("NVDA"));
        assertEquals(1000, strategy.getTraderStocks().get("AMD"));
        assertEquals(100000, strategy.getTraderFunds());
    }
}
