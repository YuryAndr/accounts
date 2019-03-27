package ru.andryakov.accounts.emulator.runner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import ru.andryakov.accounts.emulator.request.Request;

public class RequestRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestRunner.class);

    private final Request request;
    private final int numberOfThreads;
    private ExecutorService executor;

    public RequestRunner(Request request, int numberOfThreads) {
        Assert.isTrue(numberOfThreads >= 0,
                "numberOfThreads can not be negative");
        this.request = request;
        this.numberOfThreads = numberOfThreads;
    }

    public void runRequest() {
        if (numberOfThreads == 0) {
            return;
        }
        executor = Executors.newFixedThreadPool(numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++) {
            executor.execute(() -> {
                while (true) {
                    try {
                        request.send();
                    } catch (Exception e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                }
            });
        }
    }
}
