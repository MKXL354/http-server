package com.mahdy.httpserver.core.util;

/**
 * @author Mehdi Kamali
 * @since 27/08/2025
 */
public class CommonUtils {

    public static boolean isBlank(String string) {
        return string == null || string.isBlank();
    }

    public static boolean isNotBlank(String string) {
        return !isBlank(string);
    }
}
