package nl.rug.aoop.StockApplication;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.initialization.WebViewFactory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Stock application main class.
 */
@Slf4j
public class StockApplicationMain {

    /**
     * The main method.
     * @param args The arguments.
     */
    public static void main(String[] args) {
        int port = Integer.parseInt(System.getenv("STOCK_EXCHANGE_PORT"));
        try {
            ExecutorService executorService = Executors.newCachedThreadPool();
            StockApplication stockApplication = new StockApplication(port);
            WebViewFactory webViewFactory = new WebViewFactory();
            webViewFactory.createView(stockApplication);
            executorService.submit(stockApplication);
        } catch (IOException e) {
            log.error("The server has not started", e);
        }
    }
}
