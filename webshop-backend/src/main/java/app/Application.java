package app;

import controller.ConsoleMenu;
import exceptions.FailedToDeleteException;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Application {
    public static void main(String[] args) throws FailedToDeleteException, IOException, InterruptedException {
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        ConsoleMenu consolemenu = new ConsoleMenu();
        consolemenu.start();
    }
}