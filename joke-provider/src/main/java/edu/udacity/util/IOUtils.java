package edu.udacity.util;

import java.io.Closeable;
import java.io.IOException;

public class IOUtils {
    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ex) {
                // ignore the exception
            }
        }
    }
    // private constructor to prevent instantiation
    private IOUtils() {
    }
}
