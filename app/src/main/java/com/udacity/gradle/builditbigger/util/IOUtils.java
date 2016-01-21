package com.udacity.gradle.builditbigger.util;

import java.io.Closeable;
import java.io.IOException;

public class IOUtils {
    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ex) {
                // ignore silently
            }
        }
    }

    // private constructor to prevent instantiation
    private IOUtils() {
    }
}
