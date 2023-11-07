package nl.rug.aoop.TestTrader;

import nl.rug.aoop.Trader.Trader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestTrader {
    private Trader trader;
    private String id;
    private String name;
    private Map<String, Integer> ownedShares;
    private double funds;
    private List<String> transactions;
    @BeforeEach
    public void setup() {
        id = "Id1";
        name = "Dick (Richard)";
        ownedShares = new HashMap<>();
        ownedShares.put("Dick's sporting goods", 300);
        funds = 1000000;
        transactions = new ArrayList<>();
        trader = new Trader(id, name, funds, ownedShares);
    }
    @Test
    public void testConstructor() {
        assertEquals(id, trader.getId());
        assertEquals(name, trader.getName());
        assertEquals(funds, trader.getFunds());
        assertEquals(ownedShares, trader.getOwnedShares());
    }
}
