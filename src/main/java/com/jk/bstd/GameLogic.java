package com.jk.bstd;

import com.jk.bstd.entities.Point;
import com.jk.bstd.entities.Tile;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

import java.util.ArrayList;

public final class GameLogic {
    private GameLogic() throws IllegalAccessException {
        throw new IllegalAccessException("Cannot instantiate GameLogic");
    }

    private static final int GRID_SIZE = 64;

    private static Point convertToGridPaneXandY(Point point, GridPane gridPane) {
        double offsetX = gridPane.getLayoutX(); // 64
        double offsetY = gridPane.getLayoutY(); // 256 = 64 * 4

        int row = point.getX(); // 0
        int col = point.getY(); // 2

        int newY = (int) (row * GRID_SIZE + offsetY); // 256 + 0 * 64 = 256
        int newX = (int) (col * GRID_SIZE + offsetX); // 64 + 2 * 64 = 192

        return new Point(newX, newY);
    }


    public static Path createPath(ArrayList<Tile> tiles, GridPane gridPane) {
        Path path = new Path();
        path.setStroke(Color.RED);
        path.setStrokeWidth(5);
        System.out.println(tiles.get(0).getPoint());

        Point point = convertToGridPaneXandY(tiles.get(0).getPoint(), gridPane);

        MoveTo start = new MoveTo(point.getX(), point.getY());
        path.getElements().add(start);
        LineTo line = new LineTo(0, 0);
        path.getElements().add(line);

//        for (int i = 1; i < tiles.size(); i++) {
//            LineTo line = new LineTo(tiles.get(i).getX() + offsetY, tiles.get(i).getY() + offsetX);
//            path.getElements().add(line);
//            System.out.println("x: " + tiles.get(i).getX() + " y: " + tiles.get(i).getY());
//            System.out.println("path: " + i + " " + line.toString());
//        }
        System.out.println(path.getElements());
        return path;
    }
}
