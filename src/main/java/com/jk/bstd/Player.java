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
    private boolean isAlive;
    private boolean isSelling;
    private List<Tower> towers = new ArrayList<>();
    Label Stats;

    public Player() {
        this.money = 200;
        this.health = 100;
        this.level = 1;
        this.isAlive = true;
        this.isSelling = false;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void addMoney(int money) {
        this.money += money;
    }

    public void removeMoney(int money) {
        this.money -= money;
    }

    public int getHealth() {
        return health;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isSelling() {
        return isSelling;
    }

    public void setSelling(boolean selling) {
        isSelling = selling;
    }

    public Label getStats() {
        Label stats = new Label();
        stats.setText("Money: " + this.money + "\nHealth: " + this.health + "\nLevel: " + this.level);
        stats.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        stats.setLayoutX(30);
        stats.setLayoutY(30);
        return stats;
    }

    public void updateStats() {
        Platform.runLater(() -> {
            Stats.setText("Money: " + this.money + "\nHealth: " + this.health + "\nLevel: " + this.level);
        });
    }


}
