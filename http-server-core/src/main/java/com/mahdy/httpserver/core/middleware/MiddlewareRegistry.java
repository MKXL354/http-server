package com.mahdy.httpserver.core.middleware;

/**
 * @author Mehdi Kamali
 * @since 01/08/2025
 */
public interface MiddlewareRegistry {

    void fillRegistry();

    PreProcessMiddleware getPreProcessMiddlewareChainStart();

    PostProcessMiddleware getPostProcessMiddlewareChainStart();
}
