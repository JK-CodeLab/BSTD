package com.jk.bstd.entities;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

/**
 * An animal class for the enemies.
 *
 * @author Joseph Chun, Kira Yoon
 * @version 1.0
 */
public abstract class Animal extends Entity {
    private int health;
    private final int attack;
    private final int goldDropped;
    private Boolean isDead = false;

    /**
     * Constructor for Animal.
     *
     * @param health the health of the animal
     * @param attack the attack of the animal
     * @param goldDropped the gold dropped by the animal
     */
    public Animal(final int health, final int attack, final int goldDropped) {
        super(new Point());
        this.health = health;
        this.attack = attack;
        this.goldDropped = goldDropped;
    }

    /**
     * Returns an image of the animal.
     *
     * @param currPoint the current point of the animal
     * @param nextPoint the next point of the animal
     * @return image of the animal
     */
    public abstract Image getImg(Point2D currPoint, Point2D nextPoint);

    /**
     * Reduce the health of the animal by the damage.
     * If the health is less than or equal to 0, the animal is dead.
     *
     * @param damage the amount of damage to take
     */
    public void takeDamage(final int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.isDead = true;
        }
    }

    /**
     * Returns the health of the animal.
     *
     * @return the health of the animal
     */
    public Boolean getIsDead() {
        return this.isDead;
    }

    /**
     * Returns the gold dropped by the animal.
     *
     * @return the gold dropped by the animal
     */
    public int getGoldDropped() {
        return goldDropped;
    }

    /**
     * Returns the attack of the animal.
     *
     * @return the attack of the animal
     */
    public int getAttack() {
        return attack;
    }
}
