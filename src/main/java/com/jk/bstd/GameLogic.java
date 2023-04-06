package com.jk.bstd;

import com.jk.bstd.entities.Point;
import com.jk.bstd.entities.Tile;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

//TODO: remove, just for test
import javafx.scene.shape.*;
import javafx.animation.*;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.util.ArrayList;

public final class GameLogic {
    private static final int GRID_SIZE = 64;

    private GameLogic() throws IllegalAccessException {
        throw new IllegalAccessException("Cannot instantiate GameLogic");
    }



    private static Point convertToGridPaneXandY(Point point, GridPane gridPane) {
        double offsetX = gridPane.getLayoutX(); // 64
        double offsetY = gridPane.getLayoutY(); // 256 = 64 * 4

        int row = point.getX(); // 0
        int col = point.getY(); // 2

        int newY = (int) (row * GRID_SIZE + offsetY + 32); // 256 + 0 * 64 = 256
        int newX = (int) (col * GRID_SIZE + offsetX + 32); // 64 + 2 * 64 = 192

        return new Point(newX, newY);
    }


    public static Path createPath(ArrayList<Tile> tiles, GridPane gridPane) {
        Path path = new Path();
        path.setStroke(Color.RED);
        path.setStrokeWidth(5);

        Point point = convertToGridPaneXandY(tiles.get(0).getPoint(), gridPane);
        MoveTo start = new MoveTo(point.getX(), point.getY());
        path.getElements().add(start);

        for (int i = 1; i < tiles.size(); i++) {
            point = convertToGridPaneXandY(tiles.get(i).getPoint(), gridPane);
            LineTo line = new LineTo(point.getX(), point.getY());
            path.getElements().add(line);
        }
        System.out.println(path.getElements());
        return path;
    }
}
