package com.jk.bstd;

import com.jk.bstd.entities.Tower;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int money;
    private int health;
    private int level;
    private boolean isAlive = true;
    private boolean isSelling = false;
    private List<Tower> towers = new ArrayList<>();
    Label stats;

    public Player() {
        this.money = 200;
        this.health = 10;
        this.level = 0;
    }

    public Player(int money, int health, int level) {
        this.money = money;
        this.health = health;
        this.level = level;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
        updateStats();
    }

    public void addMoney(int money) {
        this.money += money;
        updateStats();
    }

    public void removeMoney(int money) {
        this.money -= money;
        updateStats();
    }

    public int getHealth() {
        return health;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.isAlive = false;
        }
        updateStats();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        updateStats();
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
        updateStats();
    }

    public boolean isSelling() {
        return isSelling;
    }

    public void setSelling(boolean selling) {
        isSelling = selling;
        updateStats();
    }

    public Label getStats() {
        stats = new Label();
        stats.setText("Money: " + this.money + "\nHealth: " + this.health + "\nLevel: " + this.level);
        stats.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 25));
        stats.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        stats.setLayoutX(630);
        stats.setLayoutY(30);
        return stats;
    }

    public void updateStats() {
        Platform.runLater(() -> {
            stats.setText("Money: " + this.money + "\nHealth: " + this.health + "\nLevel: " + this.level);
        });
    }

    public void resetLevel() {
        this.money = 200;
        this.health = 100;
        this.isAlive = true;
        this.isSelling = false;
        updateStats();
    }
}
