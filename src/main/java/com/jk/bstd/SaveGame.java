package com.jk.bstd;

import com.jk.bstd.entities.Tower;
import com.jk.bstd.entities.Tile;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is used to save the game.
 * It is a utility class and cannot be instantiated.
 *
 * @author Joseph Chun, Kira Yoon
 * @version 1.0
 */
public final class SaveGame {
    private SaveGame() throws IllegalAccessException {
        throw new IllegalAccessException("Cannot instantiate GameLogic");
    }

    @SuppressWarnings("unchecked")
    private static JSONArray saveTowers(final ArrayList<Tower> towers) {
        JSONArray towerArray = new JSONArray();
        for (Tower tower : towers) {
            JSONObject towerObject = new JSONObject();
            String name = tower.getName();
            towerObject.put("name", name);
            towerObject.put("x", tower.getPoint().getX());
            towerObject.put("y", tower.getPoint().getY());
            towerArray.add(towerObject);
        }
        return towerArray;
    }

    @SuppressWarnings("unchecked")
    private static JSONObject savePlayer(final Player player) {
        JSONObject save = new JSONObject();
        save.put("level", player.getLevel());
        save.put("money", player.getMoney());
        save.put("health", player.getHealth());
        return save;
    }

    @SuppressWarnings("unchecked")
    private static JSONArray saveTiles(final ArrayList<Tile> placedTiles) {
        JSONArray pathArray = new JSONArray();
        for (Tile tile : placedTiles) {
            JSONObject pointObject = new JSONObject();
            pointObject.put("x", tile.getX());
            pointObject.put("y", tile.getY());
            pathArray.add(pointObject);
        }
        return pathArray;
    }
    /**
     * Saves the game to a JSON file.
     *
     * @param player the player object
     * @param towers a list of towers
     * @param tiles a list of tiles
     * @return true if the game was saved successfully, false otherwise
     */
    @SuppressWarnings("unchecked")
    public static boolean saveGame(final Player player, final ArrayList<Tower> towers, final ArrayList<Tile> tiles) {
        try {
            JSONObject save = new JSONObject();
            save.put("player", savePlayer(player));
            save.put("towers", saveTowers(towers));
            save.put("tiles", saveTiles(tiles));
            FileWriter file = new FileWriter("src/main/resources/save.json");
            file.write(save.toString());
            file.flush();
            file.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

