package com.mahdy.httpServer.executor;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public interface TaskExecutionManager {

    void execute(Runnable command);

    void shutdown();
}
