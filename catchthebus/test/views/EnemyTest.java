package views;

import java.awt.Image;
import javax.swing.ImageIcon;
import org.junit.Test;
import static org.junit.Assert.*;

public class EnemyTest {
    
    /* TODO
    @Test
    public void TestMove() {
        
    }*/
    
    @Test
    public void TestCollideBus() {
        Image itImage = new ImageIcon("src/data/pngs/it_man.png").getImage();
        Enemy enemy = new Enemy(200, 200, 50, 50, itImage, 3, 25, true, 3, 2);
        Image busImage = new ImageIcon("src/data/pngs/bus.png").getImage();
        Bus bus = new Bus(200,200,300,100,busImage);
        assertTrue("Collide Test Enemy-Bus",enemy.collidesBus(bus));
    }
    
    @Test
    public void TestCollideBusF() {
        Image itImage = new ImageIcon("src/data/pngs/it_man.png").getImage();
        Enemy enemy = new Enemy(100, 100, 50, 50, itImage, 3, 25, true, 3, 2);
        Image busImage = new ImageIcon("src/data/pngs/bus.png").getImage();
        Bus bus = new Bus(200,200,300,100,busImage);
        assertFalse("Collide Test Enemy-Bus False",enemy.collidesBus(bus));
    }
    
    /* TODO
    @Test
    public void TestTurn() {
    } */
    
    @Test
    public void TestKill() {
        Image itImage = new ImageIcon("src/data/pngs/it_man.png").getImage();
        Enemy enemy = new Enemy(200, 200, 50, 50, itImage, 3, 25, true, 3, 2);
        enemy.kill();
        assertFalse("Kill Enemy", enemy.getAlive());
    }
    
    @Test
    public void TestTakeDamage() {
        Image itImage = new ImageIcon("src/data/pngs/it_man.png").getImage();
        Enemy enemy = new Enemy(200, 200, 50, 50, itImage, 3, 25, true, 3, 2);
        enemy.takeDamage(20);
        assertTrue("Take Damage -20", 5 == enemy.getHealth());
        assertTrue("Take Damage Alive == True", enemy.getAlive());
    }
    
    @Test
    public void TestTakeMDamage() {
        Image itImage = new ImageIcon("src/data/pngs/it_man.png").getImage();
        Enemy enemy = new Enemy(200, 200, 50, 50, itImage, 3, 25, true, 3, 2);
        enemy.takeDamage(-50);
        assertTrue("Take Damage 50", 75 == enemy.getHealth());
        assertTrue("Take Damage Alive == True", enemy.getAlive());
    }
    
    @Test
    public void TestTakeFDamage() {
        Image itImage = new ImageIcon("src/data/pngs/it_man.png").getImage();
        Enemy enemy = new Enemy(200, 200, 50, 50, itImage, 3, 25, true, 3, 2);
        enemy.takeDamage(50);
        assertTrue("Take Damage -50", -25 == enemy.getHealth());
        assertFalse("Take Damage Alive == False", enemy.getAlive());
    }
    
}
