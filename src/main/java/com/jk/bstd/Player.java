package com.jk.bstd;

import com.jk.bstd.entities.Tower;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {
    private int money;
    private int health;
    private int level;
    private boolean isAlive = true;
    private boolean isSelling = false;
    private boolean isPlaying = false;
    private List<Tower> towers = new ArrayList<>();
    Label stats;
    private Label moneyLabel = new Label();
    private Label healthLabel = new Label();
    private Label levelLabel = new Label();

    public Player() {
        this.money = 200;
        this.health = 100;
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

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
        updateStats();
    }

    public Label createStatsLabel(String labelName) {
        switch (labelName) {
            case "money" -> {
                moneyLabel.setText(String.valueOf(this.money));
                moneyLabel.setLayoutX(388);
                moneyLabel.setLayoutY(40);
                moneyLabel.setFont(Font.font("Munro", FontWeight.BOLD, 35));
                moneyLabel.setTextFill(Color.WHITE);
                return moneyLabel;
            }
            case "health" -> {
                healthLabel.setText(String.valueOf(this.health));
                healthLabel.setLayoutX(530);
                healthLabel.setLayoutY(40);
                healthLabel.setFont(Font.font("Munro", FontWeight.BOLD, 35));
                healthLabel.setTextFill(Color.WHITE);
                return healthLabel;
            }
            case "level" -> {
                levelLabel.setText(String.valueOf(this.level));
                levelLabel.setLayoutX(687);
                levelLabel.setLayoutY(40);
                levelLabel.setFont(Font.font("Munro", FontWeight.BOLD, 35));
                levelLabel.setTextFill(Color.WHITE);
                return levelLabel;
            }
        }
        return null;
    }

    public void updateStats() {
        Platform.runLater(() -> {
            moneyLabel.setText(String.valueOf(this.money));
            healthLabel.setText(String.valueOf(this.health));
            levelLabel.setText(String.valueOf(this.level));
        });
    }
}
