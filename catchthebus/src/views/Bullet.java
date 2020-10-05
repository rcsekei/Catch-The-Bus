package views;

import java.awt.Image;
import java.awt.Rectangle;

public class Bullet extends Sprite {

    private boolean visible;
    private int dirX;
    private int dirY;
    private boolean hasDir;
    private final int defX;
    private final int defY;

    public Bullet(int x, int y, int height, int width, Image image) {
        super(x, y, width, height, image);
        defX = x;
        defY = y;
        visible = false;
        hasDir = false;
    }
    
    /**
     * Make bullet visible
     */

    @Override
    public void show() {
        visible = true;
    }
    
    /**
     * Bullet moovement, depends on directions, It follows the enemy
     * @param enemy 
     */
    public void move(Enemy enemy) {
        dirX = enemy.getX();
        dirY = enemy.getY();
        if (getHasDir()) {
            if (x < dirX) {
                x += 25;
            } else if (x > dirX) {
                x -= 25;
            }
            if (y < dirY) {
                y += 25;
            } else if (y > dirY) {
                y -= 25;
            }
        }
        if ((Math.abs(x - dirX) < 25 && Math.abs(y - dirY) < 25) || collideEnemy(enemy)){
            hit();
        }
    }
    
    /**
     * Bullet hit cath the enemy
     */

    public void hit() {
        visible = false;
        x = defX;
        y = defY;
        hasDir = false;
    }
    
    /**
     * Bullet colled with enemy
     * @param enemy
     * @return 
     */

    public boolean collideEnemy(Enemy enemy) {
        Rectangle rect = new Rectangle(x, y, width, height);
        Rectangle otherRect = new Rectangle(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
        return rect.intersects(otherRect);
    }
    
    // GETTER - SETTER

    public boolean getVisibility() {
        return visible;
    }

    public void setHasDir(int x, int y) {
        hasDir = true;
    }
    public boolean getHasDir(){
        return hasDir;
    }
}
