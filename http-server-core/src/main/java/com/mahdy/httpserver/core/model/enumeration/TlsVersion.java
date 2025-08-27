package com.mahdy.httpserver.core.model.enumeration;

import lombok.Getter;

import java.util.Arrays;

/**
 * @author Mehdi Kamali
 * @since 27/08/2025
 */
@Getter
public enum TlsVersion {

    TLS1_1("TLSv1.1"),
    TLS1_2("TLSv1.2"),
    TLS1_3("TLSv1.3");

    private final String version;

    TlsVersion(String version) {
        this.version = version;
    }

    public static TlsVersion of(String version) {
        return Arrays.stream(TlsVersion.values()).filter(v -> v.version.equals(version)).findFirst().orElse(null);
    }
}
