package model;

import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerTest {
    
    /**
     * Test of addMoney method, of class Player.
     */
    @Test
    public void testAddMoney() {
        int amount = 0;
        Player instance = new Player(100,50);
        instance.addMoney(amount);
        assertTrue("Player add 0 money:", 50 == instance.getMoney());
    }
    @Test
    public void testAddMoney5() {
        int amount = 5;
        Player instance = new Player(100,50);
        instance.addMoney(amount);
        assertTrue("Player add 5 money:", 55 == instance.getMoney());
    }
    @Test
    public void testAddMoneyM5() {
        int amount = -10;
        Player instance = new Player(100,50);
        instance.addMoney(amount);
        assertTrue("Player add -5 money:", 40 == instance.getMoney());
    }

    /**
     * Test of decreaseLife method, of class Player.
     */
    @Test
    public void testDecreaseLife() {
        int x = 0;
        Player instance = new Player(100,50);
        instance.decreaseLife(x);
        assertTrue("Player decrease 0 lives:", 100 == instance.getLives());
        
    }
    @Test
    public void testDecreaseLife12() {
        int x = 12;
        Player instance = new Player(100,50);
        instance.decreaseLife(x);
        assertTrue("Player decrease 12 lives:", 88 == instance.getLives());
        
    }
    @Test
    public void testDecreaseLifeM5() {
        int x = -5;
        Player instance = new Player(100,50);
        instance.decreaseLife(x);
        assertTrue("Player decrease +5 lives:", 105 == instance.getLives());
        
    }

    /**
     * Test of addLife method, of class Player.
     */
    @Test
    public void testAddLife() {
        int x = 0;
        Player instance = new Player(100,50);
        instance.addLife(x);
        assertTrue("Player add 0 lives:", 100 == instance.getLives());

    }
    @Test
    public void testAddLife20() {
        int x = 20;
        Player instance = new Player(100,50);
        instance.addLife(x);
        assertTrue("Player add 20 lives:", 120 == instance.getLives());

    }
    @Test
    public void testAddLifeM20() {
        int x = -20;
        Player instance = new Player(100,50);
        instance.addLife(x);
        assertTrue("Player add -20 lives:", 80 == instance.getLives());

    }  
}
