package nl.rug.aoop.TestStrategies;

import nl.rug.aoop.Stock.Stock;
import nl.rug.aoop.Strategies.TradingStrategy;
import nl.rug.aoop.messagequeue.Messages.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestTradingStrategy {

    private TradingStrategy tradingStrategy;
    private List<Stock> stocks;
    private Map<String, Integer> traderStocks;
    private double funds;
    private Stock stock;
    private String id;

    @BeforeEach
    public void setUp() {
        stocks = new ArrayList<>();
        traderStocks = new HashMap<>();
        stock = new Stock("NVDA", "NVIDIA Corporation", 10000, 250);
        stocks.add(stock);
        traderStocks.put("NVDA", 5000);
        funds = 25000;
        id = "Bot 1";
        tradingStrategy = new TradingStrategy(stocks, traderStocks, funds, id);
    }

    @Test
    public void testTradingStrategyConstructor() {
        assertEquals(stock, tradingStrategy.getStocks().get(0));
        assertEquals(5000, tradingStrategy.getTraderStocks().get("NVDA"));
        assertEquals(25000, tradingStrategy.getTraderFunds());
        assertEquals("Bot 1", tradingStrategy.getId());
    }

    @Test
    public void testChooseOrderType() {
        assertTrue(tradingStrategy.chooseOrderType() instanceof  Message);
    }

    @Test
    public void testCreateBuyOrder() {
        assertTrue(tradingStrategy.createBuyOrder() instanceof  Message);
    }

    @Test
    public void testCreateSellOrder() {
        assertTrue(tradingStrategy.createSellOrder() instanceof  Message);
    }
}
