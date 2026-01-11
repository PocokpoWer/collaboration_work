package utils;

public final class PrintUtils {

    private PrintUtils() {}

    public static void title(String text) {
        System.out.println(ConsoleColors.BLUE + "=== " + text + " ===" + ConsoleColors.RESET);
    }

    public static void success(String text) {
        System.out.println(ConsoleColors.GREEN + text + ConsoleColors.RESET);
    }

    public static void error(String text) {
        System.out.println(ConsoleColors.RED + text + ConsoleColors.RESET);
    }

    public static void info(String text) {
        System.out.println(ConsoleColors.YELLOW + text + ConsoleColors.RESET);
    }

    public static void line() {
        System.out.println(ConsoleColors.WHITE + "--------------------------------" + ConsoleColors.RESET);
    }

    public static void chatBot(String message) {
        System.out.println(ConsoleColors.PURPLE + "ChatBot: " + message + ConsoleColors.RESET);
    }

    public static void system(String message) {
        System.out.println(ConsoleColors.CYAN + message + ConsoleColors.RESET);
    }
}