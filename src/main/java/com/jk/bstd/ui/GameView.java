package com.jk.bstd.ui;

import javafx.stage.Stage;

public class GameView extends View {

    public GameView() {
        super();
        createBackground("gameScreen/gameBg.png");
    }

    public void createNewGame(Stage mainMenu) {
        mainMenu.hide();
        super.getMainStage().show();
    }

    public void loadGame(Stage mainMenu) {
        mainMenu.hide();
        super.getMainStage().show();
    }
}
