package io.moonman.emergingtechnology.util;

public class Console {
    public static void debug(Object object) {
        System.out.println(object.toString());
    }

    public static void log(String text) {
        System.out.println(text);
    }
}