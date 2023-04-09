package com.jk.bstd.components;

/**
 * Class to identify the operating system.
 * This is a utility class, and should not be instantiated.
 *
 * @author Joseph Chun, Kira Yoon
 * @version 1.0
 */
public final class OSPlatform {
    private static final String OS = System.getProperty("os.name").toLowerCase();
    /**
     * Private constructor to prevent instantiation.
     *
     * @throws IllegalAccessException if the constructor is called
     */
    private OSPlatform() throws IllegalAccessException {
        throw new IllegalAccessException("Cannot instantiate GameLogic");
    }
    /**
     * Returns true if the operating system is Windows.
     *
     * @return true if the operating system is Windows
     */
    public static boolean isWindows() {
        return (OS.contains("win"));
    }
    /**
     * Returns true if the operating system is Mac.
     *
     * @return true if the operating system is Mac
     */
    public static boolean isMac() {
        return (OS.contains("mac"));
    }
}
