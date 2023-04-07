package com.jk.bstd;

import com.jk.bstd.ui.MainMenuView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        try {
            MainMenuView mainMenuView = new MainMenuView();
            stage = mainMenuView.getMainStage();
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (OSPlatform.isWindows()) {
            System.out.println("Windows");
        } else if (OSPlatform.isMac()) {
            System.out.println("Mac");
        } else {
            System.out.println("idk");
        }
        launch();
    }
}

class OSPlatform {
    private static String OS = System.getProperty("os.name").toLowerCase();
    public static boolean isWindows() {
        return (OS.contains("win"));
    }

    public static boolean isMac() {
        return (OS.contains("mac"));
    }
}
