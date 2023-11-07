module stock.exchange {
    requires static lombok;
    exports nl.rug.aoop.Stock;
    opens nl.rug.aoop.Stock to com.fasterxml.jackson.databind, com.google.gson;
    exports nl.rug.aoop.Trader;
    opens nl.rug.aoop.Trader to com.fasterxml.jackson.databind, com.google.gson;
    exports nl.rug.aoop.StockApplication;
    opens nl.rug.aoop.StockApplication to com.fasterxml.jackson.databind, com.google.gson;
    exports nl.rug.aoop.TraderApplication;
    opens nl.rug.aoop.TraderApplication to com.fasterxml.jackson.databind, com.google.gson;
    exports nl.rug.aoop.StockOrder;
    opens nl.rug.aoop.StockOrder to com.fasterxml.jackson.databind, com.google.gson;
    requires org.slf4j;
    requires com.google.gson;
    requires java.desktop;
    requires com.fasterxml.jackson.core;
    requires util;
    requires networking;
    requires messagequeue;
    requires com.formdev.flatlaf;
    requires stock.market.ui;
    requires org.mockito;
    requires command;
}