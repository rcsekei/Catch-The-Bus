package views;

import views.Level.Pair;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Enemy extends Sprite {

    private int speed = 5;
    private final int dmg;
    private int velx = 0;
    private int vely = speed;
    private final int worth;
    private final int type;
    private boolean isAlive;
    private double health;
    private int counterDir = 0;
    private int timer;
    private int tempVelX;
    private int tempVelY;

    public Enemy(int x, int y, int width, int height, Image image, int dmg, double health, boolean isAlive, int worth, int type) {
        super(x, y, width, height, image);
        this.health = health;
        this.dmg = dmg;
        this.isAlive = isAlive;
        this.worth = worth;
        this.type = type;
        this.timer = 0;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }

    /**
     * Move enemies along to directions (from file) and coordinates (from file)
     *
     * @param cords
     * @param dir
     */
    public void move(ArrayList<Pair> cords, String dir) {
        if (this.timer > 0) {
            this.timer--;
        } else {
            this.x += velx;
            this.y += vely;
        }
        if (turn(cords)) {
            switch (dir.charAt(counterDir)) {
                case 'r':
                    velx = speed;
                    vely = 0;
                    break;
                case 'u':
                    velx = 0;
                    vely = -(speed);
                    break;
                case 'l':
                    velx = -(speed);
                    vely = 0;
                    break;
                case 'd':
                    velx = 0;
                    vely = speed;
                    break;
            }
            counterDir++;
        }

    }

    /**
     * Method tells us if enemies cathed the bus or not
     *
     * @param bus
     * @return
     */
    public boolean collidesBus(Bus bus) {
        Rectangle rect = new Rectangle(x, y, width, height);
        Rectangle otherRect = new Rectangle(bus.x, bus.y, bus.width, bus.height);
        return rect.intersects(otherRect);
    }

    /**
     * If X and Y coordinates are equal then Enemy should change direction
     *
     * @param cords
     * @return
     */
    public boolean turn(ArrayList<Pair> cords) {
        return (cords.get(counterDir).getX() == this.x && cords.get(counterDir).getY() == this.y);
    }

    /**
     * Enemy isn't alive anymore
     */
    public void kill() {
        this.isAlive = false;
    }

    /**
     * Enemy get damage from tower (bullet) and this decrese it's health
     *
     * @param damage
     */
    public void takeDamage(double damage) {
        this.health -= damage;
        this.isAlive = (this.health > 0);
    }

    // GETTER - SETTER
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean getAlive() {
        return this.isAlive;
    }

    public double getHealth() {
        return this.health;
    }

    public int getDmg() {
        return this.dmg;
    }

    public int getWorth() {
        return this.worth;
    }

    public int getType() {
        return this.type;
    }

    public void setTimer(int time) {
        this.timer = time;
    }
}
