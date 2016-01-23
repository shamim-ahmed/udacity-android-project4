package edu.udacity.util;

public class StringUtils {
    public static boolean isBlank(String str) {
        return str == null || str.trim().equals("");
    }

    /**
     * Returns true when the string is composed of digist only
     * @param str the input string
     * @return boolean
     */
    public static boolean isNumeric(String str) {
        if (isBlank(str)) {
            return false;
        }

        char[] charArray = str.toCharArray();

        for (char c : charArray) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }

    // private constructor to prevent instantiation
    private StringUtils() {
    }
}
