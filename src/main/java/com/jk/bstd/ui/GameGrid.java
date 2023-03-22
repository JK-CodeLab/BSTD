package com.jk.bstd.ui;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class GameGrid {
    private final GridPane gameGrid;
    private static final int ROWS = 8;
    private static final int COLUMNS = 15;

    public GameGrid() {
        gameGrid = new GridPane();

        // TODO: Remove this line (for testing purposes)
        gameGrid.setGridLinesVisible(true);

        gameGrid.setPrefSize(960, 120);
        gameGrid.setLayoutY(192);
        gameGrid.setLayoutX(0);

        initGrid();
    }

    private void initGrid() {
        for (int i = 0; i < ROWS; i++) {
            RowConstraints row = new RowConstraints(64);
            gameGrid.getRowConstraints().add(row);
        }

        for (int i = 0; i < COLUMNS; i++) {
            ColumnConstraints column = new ColumnConstraints(64);
            gameGrid.getColumnConstraints().add(column);
        }
    }

    public GridPane getGameGrid() {
        return gameGrid;
    }
}
