package com.jk.bstd;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Player class that holds the player's money, health, and level.
 *
 * @author Joseph Chun, Kira Yoon
 * @version 1.0
 */
public class Player {
    /**
     * The default amount of money the player starts with.
     */
    public static final int DEFAULT_MONEY = 200;
    /**
     * The default amount of health the player starts with.
     */
    public static final int DEFAULT_HEALTH = 100;
    /**
     * The default level the player starts with.
     */
    public static final int DEFAULT_LEVEL = 0;
    private static final int DEFAULT_MONEY_LABEL_X = 388;
    private static final int DEFAULT_MONEY_LABEL_Y = 40;
    private static final int DEFAULT_HEALTH_LABEL_X = 530;
    private static final int DEFAULT_HEALTH_LABEL_Y = 40;
    private static final int DEFAULT_LEVEL_LABEL_X = 687;
    private static final int DEFAULT_LEVEL_LABEL_Y = 40;
    private static final int DEFAULT_LABEL_FONT_SIZE = 35;

    private int money;
    private int health;
    private int level;
    private boolean isAlive = true;
    private boolean isSelling = false;
    private boolean isPlaying = false;
    private boolean gameOver = false;
    private final Label moneyLabel = new Label();
    private final Label healthLabel = new Label();
    private final Label levelLabel = new Label();

    /**
     * Create a player object with default values.
     */
    public Player() {
        this.money = DEFAULT_MONEY;
        this.health = DEFAULT_HEALTH;
        this.level = DEFAULT_LEVEL;
    }

    /**
     * Create a player object with custom values.
     *
     * @param money the amount of money the player starts with
     * @param health the amount of health the player starts with
     * @param level the level the player starts with
     */
    public Player(final int money, final int health, final int level) {
        this.money = money;
        this.health = health;
        this.level = level;
    }

    /**
     * Returns the player's money as an int.
     *
     * @return the player's money
     */
    public int getMoney() {
        return money;
    }

    /**
     * Sets the player's money to the given amount.
     *
     * @param money the amount of money the player has
     */
    public void setMoney(final int money) {
        this.money = money;
        updateStats();
    }

    /**
     * Adds money to the player's current amount.
     *
     * @param moneyToAdd the amount of money to add
     */
    public void addMoney(final int moneyToAdd) {
        this.money += moneyToAdd;
        updateStats();
    }

    /**
     * Removes money from the player's current amount.
     *
     * @param moneyToRemove the amount of money to remove
     */
    public void removeMoney(final int moneyToRemove) {
        this.money -= moneyToRemove;
        updateStats();
    }

    /**
     * Returns the player's health as an int.
     *
     * @return the player's health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Reduces the player's health by the given amount.
     *
     * @param damage the amount of health the player has
     */
    public void takeDamage(final int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.isAlive = false;
        }
        updateStats();
    }

    /**
     * Returns the players current level.
     *
     * @return the player's level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the player's level to the given level.
     *
     * @param level the level the player is on
     */
    public void setLevel(final int level) {
        this.level = level;
        updateStats();
    }

    /**
     * Returns whether the player is alive or not.
     *
     * @return true if the player is alive, false otherwise
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Sets the player's alive status to the given boolean.
     *
     * @param alive true if the player is alive, false otherwise
     */
    public void setAlive(final boolean alive) {
        isAlive = alive;
        updateStats();
    }

    /**
     * Returns whether the player is in selling mode or not.
     *
     * @return true if the player is selling, false otherwise
     */
    public boolean isSelling() {
        return isSelling;
    }

    /**
     * Sets the player's selling status to the given boolean.
     *
     * @param selling true if the player is selling, false otherwise
     */
    public void setSelling(final boolean selling) {
        isSelling = selling;
        updateStats();
    }

    /**
     * Returns whether the player is playing or not.
     *
     * @return true if the player is playing, false otherwise
     */
    public boolean isPlaying() {
        return isPlaying;
    }

    /**
     * Sets the player's playing status to the given boolean.
     *
     * @param playing true if the player is playing, false otherwise
     */
    public void setPlaying(final boolean playing) {
        isPlaying = playing;
        updateStats();
    }

    /**
     * Returns whether the game is over or not.
     *
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Sets the game over status to the given boolean.
     *
     * @param gameOver true if the game is over, false otherwise
     */
    public void setGameOver(final boolean gameOver) {
        this.gameOver = gameOver;
        updateStats();
    }

    /**
     * Creates and returns a label for the player's stats.
     *
     * @param labelName the name of the label to update
     * @return the updated label
     */
    public Label createStatsLabel(final String labelName) {
        switch (labelName) {
            case "money" -> {
                moneyLabel.setText(String.valueOf(this.money));
                moneyLabel.setLayoutX(DEFAULT_MONEY_LABEL_X);
                moneyLabel.setLayoutY(DEFAULT_MONEY_LABEL_Y);
                moneyLabel.setFont(Font.font("Munro", FontWeight.BOLD, DEFAULT_LABEL_FONT_SIZE));
                moneyLabel.setTextFill(Color.WHITE);
                return moneyLabel;
            }
            case "health" -> {
                healthLabel.setText(String.valueOf(this.health));
                healthLabel.setLayoutX(DEFAULT_HEALTH_LABEL_X);
                healthLabel.setLayoutY(DEFAULT_HEALTH_LABEL_Y);
                healthLabel.setFont(Font.font("Munro", FontWeight.BOLD, DEFAULT_LABEL_FONT_SIZE));
                healthLabel.setTextFill(Color.WHITE);
                return healthLabel;
            }
            default -> {
                levelLabel.setText(String.valueOf(this.level));
                levelLabel.setLayoutX(DEFAULT_LEVEL_LABEL_X);
                levelLabel.setLayoutY(DEFAULT_LEVEL_LABEL_Y);
                levelLabel.setFont(Font.font("Munro", FontWeight.BOLD, DEFAULT_LABEL_FONT_SIZE));
                levelLabel.setTextFill(Color.WHITE);
                return levelLabel;
            }
        }
    }

    /**
     * Updates the player's stats.
     */
    public void updateStats() {
        Platform.runLater(() -> {
            moneyLabel.setText(String.valueOf(this.money));
            healthLabel.setText(String.valueOf(this.health));
            levelLabel.setText(String.valueOf(this.level));
        });
    }
}
