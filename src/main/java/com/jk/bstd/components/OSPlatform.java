package com.jk.bstd.components;

public final class OSPlatform {

        private static final String OS = System.getProperty("os.name").toLowerCase();

        public static boolean isWindows() {
            return (OS.contains("win"));
        }
        public static boolean isMac() {
            return (OS.contains("mac"));
        }
}
