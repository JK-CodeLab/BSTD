package com.jk.bstd;

import com.jk.bstd.Main;
/**
 * com.jk.bstd.Game class that launches the game.
 *
 * @author Joseph Chun, Kira Yoon
 * @version 1.0
 */
public final class Game {
    private Game() throws IllegalAccessException {
        throw new IllegalAccessException("Cannot instantiate GameLogic");
    }
    /**
     * com.jk.bstd.Game method that launches the game.
     *
     * @param args the arguments passed in
     */
    public static void main(final String[] args) {
        Main.main(args);
    }
}
