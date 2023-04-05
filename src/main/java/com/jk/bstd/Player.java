package com.jk.bstd;

import com.jk.bstd.entities.Tower;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int money;
    private int health;
    private int level;
    private boolean isAlive;
    private boolean isSelling;
    private List<Tower> towers = new ArrayList<>();

    public Player() {
        this.money = 100;
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

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
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
}
