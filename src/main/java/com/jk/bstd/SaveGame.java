package com.jk.bstd;

import com.jk.bstd.entities.*;
import com.jk.bstd.ui.GameGrid;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public final class SaveGame {
    private SaveGame() throws IllegalAccessException {
        throw new IllegalAccessException("Cannot instantiate GameLogic");
    }

    @SuppressWarnings("unchecked")
    private static JSONArray saveTowers(ArrayList<Tower> towers) {
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
    private static JSONObject savePlayer(Player player) {
        JSONObject save = new JSONObject();
        save.put("level", player.getLevel());
        save.put("money", player.getMoney());
        save.put("health", player.getHealth());
        return save;
    }

    @SuppressWarnings("unchecked")
    private static JSONArray saveTiles(ArrayList<Tile> placedTiles) {
        JSONArray pathArray = new JSONArray();
        for (Tile tile : placedTiles) {
            JSONObject pointObject = new JSONObject();
            pointObject.put("x", tile.getX());
            pointObject.put("y", tile.getY());
            pathArray.add(pointObject);
        }
        return pathArray;
    }

    @SuppressWarnings("unchecked")
    public static void saveGame(Player player, ArrayList<Tower> towers, ArrayList<Tile> placedTiles) {
        try {
            JSONObject save = new JSONObject();
            save.put("player", savePlayer(player));
            save.put("towers", saveTowers(towers));
            save.put("tiles", saveTiles(placedTiles));
            FileWriter file = new FileWriter("src/main/resources/save.json");
            file.write(save.toString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

