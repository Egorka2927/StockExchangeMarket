module stock.market.ui {
    requires static lombok;
    exports nl.rug.aoop.initialization;
    exports nl.rug.aoop.model.Stock;
    exports nl.rug.aoop.model.Trader;
    exports nl.rug.aoop.model.StockApplication;
    requires org.slf4j;
    requires java.desktop;
    requires util;
    requires com.formdev.flatlaf;
    requires java.net.http;
    requires jdk.httpserver;
    requires com.google.gson;
    opens nl.rug.aoop.webview.data to com.google.gson;
}