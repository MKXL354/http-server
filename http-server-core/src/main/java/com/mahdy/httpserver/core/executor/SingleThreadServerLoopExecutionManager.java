package com.mahdy.httpserver.core.executor;

import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
@Component
public class SingleThreadServerLoopExecutionManager implements ServerLoopExecutionManager {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public void execute(Runnable command) {
        executorService.execute(command);
    }

    @Override
    public void shutdown() {
        executorService.shutdown();
    }
}
