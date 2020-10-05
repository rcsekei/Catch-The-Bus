package views;

import java.awt.Image;
import javax.swing.ImageIcon;
import model.Player;
import org.junit.Test;
import static org.junit.Assert.*;

public class TowerTest {

    @Test
    public void TestCreateTower() {
        Image img = new ImageIcon("src/data/pngs/crowgrey.png").getImage();
        Tower tower = new Tower(200, 200, 50, 50, 20, 20,0,1, img);
        assertTrue("CreateTower x value", 185 == tower.createTower(20, 20,0,1, img).getX());
        assertTrue("CreateTower y value", 185 == tower.createTower(20, 20,0,1, img).getY());
    }

    /*@Test
    public void TestRefundCost() {
        Player player = new Player(100,50);
    }*/
 /*@Test
    public voide TestUpgrade() {
    }*/
    @Test
    public void TestInRange() {
        Image img = new ImageIcon("src/data/pngs/crowgrey.png").getImage();
        Tower tower = new Tower(200, 200, 50, 50, 20, 20,0,1, img);
        Image itImage = new ImageIcon("src/data/pngs/it_man.png").getImage();
        Enemy enemy = new Enemy(200, 200, 50, 50, itImage, 3, 25, true, 3, 2);
        assertTrue("Enemy in range True", tower.inRange(enemy));
    }

    @Test
    public void TestInRangeF() {
        Image img = new ImageIcon("src/data/pngs/crowgrey.png").getImage();
        Tower tower = new Tower(200, 200, 50, 50, 20, 20,0,1, img);
        Image itImage = new ImageIcon("src/data/pngs/it_man.png").getImage();
        Enemy enemy = new Enemy(700, 700, 50, 50, itImage, 3, 25, true, 3, 2);
        assertFalse("Enemy in range False", tower.inRange(enemy));
    }

    @Test
    public void TestUpgrade(){
        Player instance = new Player(100,50);
        Image img1 = new ImageIcon("src/data/pngs/crowgrey.png").getImage();
        Tower t1 = new Tower(200, 200, 50, 50, 20, 20,0,1, img1);
        Image img2 = new ImageIcon("src/data/pngs/crowgrey.png").getImage();
        Tower t2 = new Tower(200, 200, 50, 50, 20, 20,0,1, img2);
        
        t1.upgrade(instance, 0);
        
        assertTrue("t1's power higher than t2's", t1.getPower() > t2.getPower());
    }
    
    @Test
    public void TestEvolve(){
        Player instance = new Player(100,5000);
        Image img1 = new ImageIcon("src/data/pngs/crowgrey.png").getImage();
        Tower t1 = new Tower(200, 200, 50, 50, 20, 20,0,1, img1);
        t1.upgrade(instance, 0);
        t1.upgrade(instance, 0);
        t1.upgrade(instance, 0);
        t1.upgrade(instance, 0);
        t1.upgrade(instance, 2);
        assertTrue("t1's is lvl 6", t1.getLevel() == 6);
    }
    @Test
    public void TestEvolve2(){
        Player instance = new Player(100,5000);
        Image img1 = new ImageIcon("src/data/pngs/crowgrey.png").getImage();
        Tower t1 = new Tower(200, 200, 50, 50, 20, 20,0,1, img1);
        
        t1.upgrade(instance, 0);
        
        assertTrue("Player's money decreased", instance.getMoney() < 5000);
    }
    @Test
    public void TestEvolve3(){
        Player instance = new Player(100,5000);

        Image img2 = new ImageIcon("src/data/pngs/crowgrey.png").getImage();
        Tower t2 = new Tower(200, 200, 50, 50, 20, 20,0,1, img2);

        t2.upgrade(instance, 0);
        t2.upgrade(instance, 0);
        t2.upgrade(instance, 0);
        t2.upgrade(instance, 0);
        t2.upgrade(instance, 1);
        
        
        assertTrue("t2 is evolved", t2.getEvolved());
    }
}
