package com.jk.bstd;

import com.jk.bstd.entities.*;
import com.jk.bstd.ui.GameGrid;
import com.jk.bstd.ui.GameView;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public final class LoadGame {
    private LoadGame() throws Exception {
        throw new Exception("Cannot instantiate LoadGame");
    }

    public static JSONObject readSave() {
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader("src/main/resources/save.json");
            JSONObject obj = (JSONObject) parser.parse(reader);
            System.out.println(obj);
            return obj;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException");
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("ParseException");
        }
        return null;
    }

    public static ArrayList<Tile> loadTiles (JSONObject obj) {
        ArrayList<Tile> placedTiles = new ArrayList<>();
        for (Object tile : (ArrayList) obj.get("tiles")) {
            JSONObject tileObj = (JSONObject) tile;
            int x = ((Long) tileObj.get("x")).intValue();
            int y = ((Long) tileObj.get("y")).intValue();
            Point point = new Point(x, y);
            Tile placedTile = new Tile(point);
            placedTiles.add(placedTile);
        }
        return placedTiles;
    }

    public static ArrayList<Tower> loadTowers(JSONObject obj){
        ArrayList<Tower> placedTowers = new ArrayList<>();
        for (Object tower : (ArrayList) obj.get("towers")) {
            JSONObject towerObj = (JSONObject) tower;
            String name = (String) towerObj.get("name");
            int x = ((Long) towerObj.get("x")).intValue();
            int y = ((Long) towerObj.get("y")).intValue();

            Tower placedTower = null;
            Point towerPoint = new Point(x, y);
            switch (name) {
                case "Sprinkler" -> {
                    placedTower = new Sprinkler(towerPoint);
                }
                case "Scarecrow" -> {
                    placedTower = new Scarecrow(towerPoint);

                }
                case "Farmer" -> {
                    placedTower = new Farmer(towerPoint);
                }
                case "Dog" -> {
                    placedTower = new Dog(towerPoint);
                }
            }
            if (placedTower != null) {
            placedTowers.add(placedTower);
            }
        }
        return placedTowers;
    }

    public static Player loadPlayer(JSONObject obj) {
        JSONObject playerObj = (JSONObject) obj.get("player");
        int level = ((Long) playerObj.get("level")).intValue();
        int money = ((Long) playerObj.get("money")).intValue();
        int health = ((Long) playerObj.get("health")).intValue();
        return new Player(money, health, level);
    }


    public static void restoreSave() {
    }
}
