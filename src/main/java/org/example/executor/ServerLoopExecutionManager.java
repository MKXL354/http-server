package org.example.executor;

/**
 * @author Mehdi Kamali
 * @since 27/07/2025
 */
public interface ServerLoopExecutionManager {

    void execute(Runnable command);

    void shutdown();
}
