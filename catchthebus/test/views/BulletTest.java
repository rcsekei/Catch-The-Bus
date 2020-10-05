package views;

import java.awt.Image;
import javax.swing.ImageIcon;
import org.junit.Test;
import static org.junit.Assert.*;

public class BulletTest {
    
    @Test
    public void TestShow() {
        Bullet bullet = new Bullet(200, 200, 20, 20, new ImageIcon("src/data/pngs/circle.png").getImage());
        bullet.show();
        assertTrue("Show visibility value", true == bullet.getVisibility());
    }
    
    @Test
    public void TestMove() {
        Image itImage = new ImageIcon("src/data/pngs/it_man.png").getImage();
        Enemy enemy = new Enemy(200, 200, 50, 50, itImage, 3, 25, true, 3, 2);
        Bullet bullet = new Bullet(200, 200, 20, 20, new ImageIcon("src/data/pngs/circle.png").getImage());
        bullet.move(enemy);
        assertTrue("Move visibility value", false == bullet.getVisibility());
        assertTrue("Move hasDir value", false == bullet.getHasDir());
        assertTrue("Move x value", 200 == bullet.x);
        assertTrue("Move y value", 200 == bullet.y);
    }
    
    @Test
    public void TestHit() {
        Bullet bullet = new Bullet(200, 200, 20, 20, new ImageIcon("src/data/pngs/circle.png").getImage());
        bullet.hit();
        assertTrue("Hit visibility value", false == bullet.getVisibility());
        assertTrue("Hit hasDir value", false == bullet.getHasDir());
        assertTrue("Hit x value", 200 == bullet.x);
        assertTrue("Hit y value", 200 == bullet.y);
    }
    
    @Test
    public void TestCollideEnemy() {
        Image itImage = new ImageIcon("src/data/pngs/it_man.png").getImage();
        Enemy enemy = new Enemy(200, 200, 50, 50, itImage, 3, 25, true, 3, 2);
        Bullet bullet = new Bullet(200, 200, 20, 20, new ImageIcon("src/data/pngs/circle.png").getImage());
        assertTrue("Collide Test Enemy-Bullet",bullet.collideEnemy(enemy));
    }
    
    @Test
    public void TestCollideEnemyF() {
        Image itImage = new ImageIcon("src/data/pngs/it_man.png").getImage();
        Enemy enemy = new Enemy(100, 100, 50, 50, itImage, 3, 25, true, 3, 2);
        Bullet bullet = new Bullet(200, 200, 20, 20, new ImageIcon("src/data/pngs/circle.png").getImage());
        assertFalse("Collide Test Enemy-Bullet False",bullet.collideEnemy(enemy));
    }
    
    
}
