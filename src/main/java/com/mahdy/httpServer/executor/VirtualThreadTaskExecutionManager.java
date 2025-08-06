package com.mahdy.httpServer.executor;

import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Component
public class VirtualThreadTaskExecutionManager implements TaskExecutionManager {

    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

    @Override
    public void execute(Runnable command) {
        executorService.execute(command);
    }

    @Override
    public void shutdown() {
        executorService.shutdown();
    }
}
