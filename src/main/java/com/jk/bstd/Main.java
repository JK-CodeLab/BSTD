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
        launch();
    }
}
