/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

public class Player {

    private int money = 0;
    private int lives;
    private final int STARTLIVES = 100;
    private final int STARTMONEY = 5000;
    private Player player;

    public Player(int lives, int money) {
        this.lives = lives;
        this.money = money;
    }

    /**
     * Return to start condition lives=100, money = 50
     */
    public void reset() {
        this.lives = STARTLIVES;
        this.money = STARTMONEY;
    }

    /**
     * increase Player's money
     *
     * @param amount
     */
    public void addMoney(int amount) {
        this.money += amount;
    }

    /**
     * Decrease Player's money
     * @param x 
     */
    public void decreaseLife(int x) {
        this.lives = this.lives - x;
    }

    /**
     * Increase Player's lives
     * @param x 
     */
    public void addLife(int x) {
        this.lives = this.lives + x;
    }

    public Player getPlayer() {
        return player;
    }

    public int getMoney() {
        return this.money;
    }

    public int getLives() {
        return this.lives;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
}
