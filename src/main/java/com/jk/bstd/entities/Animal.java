package com.jk.bstd.entities;

public abstract class Animal extends Entity {
    private int health;
    private int speed;
    private int attack;
    private int goldDropped;
    public Boolean isDead = false;

    public Animal(int health, int speed, int attack, int goldDropped) {
        super(new Point());
        this.health = health;
        this.speed = speed;
        this.attack = attack;
        this.goldDropped = goldDropped;
    }
    abstract void move();
    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.isDead = true;
        }
    }
    public void setAlive(Boolean isDead) {
        this.isDead = isDead;
    }
    public Boolean getAlive() {
        return this.isDead;
    }
    public int getHealth() {
        return this.health;
    }
}
