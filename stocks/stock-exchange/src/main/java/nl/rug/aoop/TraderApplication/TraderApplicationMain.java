package nl.rug.aoop.TraderApplication;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ConnectException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Trader application main class.
 */
@Slf4j
public class TraderApplicationMain{

    /**
     * Main method.
     * @param args Arguments.
     */
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        int port = Integer.parseInt(System.getenv("STOCK_EXCHANGE_PORT"));
        String host = System.getenv("STOCK_EXCHANGE_HOST");
        try {
            TraderApplication traderApplication = new TraderApplication(host, port);
            executorService.submit(traderApplication);
        } catch (ConnectException e) {
            log.error("Server is not running");
        } catch (IOException e) {
            log.error("Trader application has not connected");
        }
    }
}
