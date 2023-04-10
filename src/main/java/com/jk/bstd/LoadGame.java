package com.jk.bstd;

import com.jk.bstd.entities.Tile;
import com.jk.bstd.entities.Tower;
import com.jk.bstd.entities.Point;
import com.jk.bstd.entities.Sprinkler;
import com.jk.bstd.entities.Scarecrow;
import com.jk.bstd.entities.Farmer;
import com.jk.bstd.entities.Dog;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is used to load the game.
 * It is a utility class and cannot be instantiated.
 *
 * @author Joseph Chun, Kira Yoon
 * @version 1.0
 */
public final class LoadGame {
    private LoadGame() throws Exception {
        throw new Exception("Cannot instantiate LoadGame");
    }
    /**
     * Loads the game from a JSON file.
     *
     * @return the game that was loaded as a JSONObject
     */
    public static JSONObject readSave() {
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader("src/main/resources/save.json");
            return (JSONObject) parser.parse(reader);
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
    /**
     * Loads the tiles from a JSONObject.
     *
     * @param obj the JSONObject to load the tiles from
     * @return an ArrayList of tiles that were placed by the player
     */
    public static ArrayList<Tile> loadTiles(final JSONObject obj) {
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
    /**
     * Loads the towers from a JSONObject.
     *
     * @param obj the JSONObject to load the towers from
     * @return an ArrayList of towers that were placed by the player
     */
    public static ArrayList<Tower> loadTowers(final JSONObject obj) {
        ArrayList<Tower> placedTowers = new ArrayList<>();
        for (Object tower : (ArrayList) obj.get("towers")) {
            JSONObject towerObj = (JSONObject) tower;
            String name = (String) towerObj.get("name");
            Tower placedTower = null;
            Point towerPoint = new Point(((Long) towerObj.get("x")).intValue(), ((Long) towerObj.get("y")).intValue());
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
                default -> {
                    System.out.println("Invalid tower name");
                }
            }
            if (placedTower != null) {
            placedTowers.add(placedTower);
            }
        }
        return placedTowers;
    }
    /**
     * Loads the player from a JSONObject.
     *
     * @param obj the JSONObject to load the player from
     * @return the player that was loaded
     */
    public static Player loadPlayer(final JSONObject obj) {
        JSONObject playerObj = (JSONObject) obj.get("player");
        int level = ((Long) playerObj.get("level")).intValue();
        int money = ((Long) playerObj.get("money")).intValue();
        int health = ((Long) playerObj.get("health")).intValue();
        return new Player(money, health, level);
    }
}
