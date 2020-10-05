package model;

import views.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public final class GameEngine extends JPanel {

    private final int maxWave = 30;
    private final int FPS = 60;
    private static boolean paused = false;
    private static boolean isOver = false;
    private int wave = 1;
    private int levelNum = 1;
    public Timer newFrameTimer;
    public boolean started = false;
    public int speed = 1000;

    private Level level;
    private Player player;
    private ArrayList<Tower> towers;
    private ArrayList<Tower> realTowers;
    private ArrayList<Bullet> bullets;
    private Range range;
    private boolean showTowers = false;
    /*NEW*/
    private static ArrayList<Enemy> enemies;

    public GameEngine() {
        super();
        realTowers = new ArrayList<>();
        bullets = new ArrayList<>();
        range = new Range(0, 0, 0, 0, new ImageIcon("src/data/pngs/circle_range.png").getImage());
        player = new Player(100, 50);
        player.reset();
        restart();
        enemies = startRound(wave);

        newFrameTimer = new Timer(speed / FPS, new NewFrameListener());
        newFrameTimer.start();
    }
    
    /**
     * Read in enemies from txts
     * @param line
     * @return 
     */
    public ArrayList<Enemy> startRound(int line) {
        ArrayList<Enemy> enemies = new ArrayList();
        try {
            File myObj = new File("src/data/waves.txt");
            Scanner myReader = new Scanner(myObj);
            int counter = 0;
            do {
                String data = myReader.nextLine();
                counter++;
            } while (counter != line);
            String data = myReader.nextLine();
            String[] currencies = data.split(" ");
            int startY = -100;
            for (String currencie : currencies) {
                switch (currencie) {
                    case "p":
                        Image pregnantImage = new ImageIcon("src/data/pngs/pregnant.png").getImage();
                        Enemy pregnant = new Enemy(225, startY, 50, 50, pregnantImage, 3, 30, true, 2, 1);
                        enemies.add(pregnant);
                        break;
                    case "i":
                        Image itImage = new ImageIcon("src/data/pngs/it_man.png").getImage();
                        Enemy it = new Enemy(225, startY, 50, 50, itImage, 3, 25, true, 3, 2);
                        enemies.add(it);
                        break;
                    case "a":
                        Image manImage = new ImageIcon("src/data/pngs/man.png").getImage();
                        Enemy man = new Enemy(225, startY, 50, 50, manImage, 2, 20, true, 2, 3);
                        enemies.add(man);
                        break;
                    case "s":
                        Image kidImage = new ImageIcon("src/data/pngs/kid.png").getImage();
                        Enemy kid = new Enemy(225, startY, 50, 50, kidImage, 1, 15, true, 1, 4);
                        enemies.add(kid);
                        break;
                    case "b":
                        Image bossImage = new ImageIcon("src/data/pngs/boss.png").getImage();
                        Enemy boss = new Enemy(225, startY, 50, 50, bossImage, 10, 100, true, 25, 5);
                        enemies.add(boss);
                        break;
                    default:
                        break;
                }
                startY = startY - 100;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return enemies;
    }
    
    /**
     * Load a newLevel
     */
    public void restart() {
        try {
            level = new Level("src/data/level" + getLevelNum() + ".txt", "src/data/coordinates" + getLevelNum() + ".txt");
        } catch (IOException ex) {
            Logger.getLogger(GameEngine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        level.draw(grphcs);
        if (showTowers) {
            towers = new ArrayList<>();
            towers = this.level.getAllTower();
            for (Tower tower : towers) {
                tower.draw(grphcs);
            }
        }
        if(range.getVisible()){
        range.draw(grphcs);
        }
        for (Enemy enemy : enemies) {
            enemy.draw(grphcs);
        }
        for (Tower tower : realTowers) {
            tower.draw(grphcs);
        }
        for (Bullet bullet : bullets) {
            if (bullet.getVisibility() && enemies.size() > 0) {
                bullet.draw(grphcs);
            }
        }

        this.setBackground(new java.awt.Color(223, 197, 161));
    }

    class NewFrameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            if (!GameEngine.paused && !isOver && started) {
                for (int i = 0; i < realTowers.size(); i++) {
                    Tower tower = realTowers.get(i);
                    Bullet bullet = bullets.get(i);
                    tower.shoot(enemies, bullet);
                    if (bullet.getHasDir()) {
                        bullet.move(tower.getFirstEnemy());
                    }
                }
                enemies.forEach((enemy) -> {
                    if (enemy.collidesBus(level.getBus())) {
                        enemy.kill();
                        player.decreaseLife(enemy.getDmg());
                        player.addMoney(-enemy.getWorth());
                        GameGUI.refreshLives(player.getLives());
                        GameGUI.refreshMoney(player.getMoney());
                    } else {
                        enemy.move(level.getCoordinates(), level.getDirections());
                    }
                });
                for (int i = 0; i < enemies.size(); i++) {
                    if (!enemies.get(i).getAlive()) {
                        player.addMoney(enemies.get(i).getWorth());
                        GameGUI.refreshMoney(player.getMoney());
                        GameGUI.refreshImage();
                        enemies.remove(i);
                    }
                }
            }
            if (isOver() && wave < maxWave) {
                nextWave();
                GameGUI.goLevel();
            } else if (isOver() && wave >= maxWave && levelNum != 5) {
                GameGUI.goLevel();
                nextLevel();
            }

            if (player.getLives() <= 0) {
                enemies.clear();
                if ((JOptionPane.showConfirmDialog(null, "Do you want to start a New Game?", "GAME OVER", JOptionPane.YES_NO_OPTION)) == JOptionPane.YES_OPTION) {
                    started = false;
                    setLevelNum(1);
                    wave = 1;
                    enemies = startRound(wave);
                    player.setLives(100);
                    GameGUI.refreshLives(100);
                    player.setMoney(50);
                    GameGUI.refreshMoney(50);
                    restart();
                    repaint();
                } else {
                    System.exit(-1);
                }
            }

            if ((isOver() && wave == maxWave && levelNum == 5)) {
                if ((JOptionPane.showConfirmDialog(null, "Do you want to start a New Game?", "YOU WON", JOptionPane.YES_NO_OPTION)) == JOptionPane.YES_OPTION) {
                    started = false;
                    setLevelNum(1);
                    wave = 1;
                    enemies = startRound(wave);
                    realTowers.clear();
                    bullets.clear();
                    player.setLives(100);
                    GameGUI.refreshLives(100);
                    player.setMoney(50);
                    GameGUI.refreshMoney(50);
                    restart();
                    repaint();
                } else {
                    System.exit(-1);
                }
            }

            repaint();
        }

    }
    
    /**
     * Load new wave until it reaches 30 wave then new lvl loads
     */
    public void nextWave() {
        started = false;
        wave++;
        GameGUI.refreshWaves(wave);
        enemies = startRound(wave);
    }
    
    /**
     * Load new level, if it reaches 5 then lvl 1 again
     */
    public void nextLevel() {
        started = false;
        levelNum++;
        wave = 1;
        clearData();
        player.setMoney(50);
        GameGUI.refreshMoney(50);
        GameGUI.refreshWaves(wave);
        GameGUI.refreshLevel(levelNum);
        restart();
        enemies = startRound(wave);
    }

    /**
     * Clear level/towers/realTowers/bullets arraylists so new level can start
     */
    public void clearData() {
        level.reset();
        towers.clear();
        realTowers.clear();
        bullets.clear();
    }

    /**
     * Depends on tower type it creats new towers and its bullets on frame and decrease Player money
     * @param tower
     * @param type 
     */
    public void addTower(Tower tower, int type) {

        { //tower
            Tower tw;
            switch (type) {
                case 2:
                    player.setMoney(player.getMoney() - 15);
                    tw = tower.createTower(15, 250, 15,type, new ImageIcon("src/data/pngs/disabgrey.png").getImage());
                    break;
                case 3:
                    player.setMoney(player.getMoney() - 20);
                    tw = tower.createTower(20, 350, 20,type, new ImageIcon("src/data/pngs/incoggrey.png").getImage());
                    break;
                default:
                    player.setMoney(player.getMoney() - 10);
                    tw = tower.createTower(10, 150, 10,type, new ImageIcon("src/data/pngs/crowgrey.png").getImage());
                    break;
            }
            realTowers.add(tw);
            GameGUI.refreshMoney(player.getMoney());
            GameGUI.refreshImage();
            this.towers.remove(tower);
        }

        { // Bullet for the tower
            int bulletX = tower.getX() + 15;
            int bulletY = tower.getY() - 15;
            Bullet bullet = new Bullet(bulletX, bulletY, 20, 20, new ImageIcon("src/data/pngs/circle.png").getImage());
            bullets.add(bullet);
        }
    }
    
    /**
     * Depends tower's type, towers disappear from screen and player gets money
     * @param tower
     * @param type 
     */
    public void sellTower(Tower tower, int type) {
        int c = 0;
        for (int i=0; i<realTowers.size(); i++) {
            if ( realTowers.get(i).equals(tower) ) {
                c = i;
            }
        }
        bullets.remove(bullets.get(c));
        realTowers.remove(realTowers.get(c));
        Tower tw = new Tower(tower.getX() + 15, tower.getY() + 15, 50, 50, 0, 0, 0,0, new ImageIcon("src/data/pngs/x.png").getImage());

        player.addMoney(tower.getRefundCost());
        towers.add(tw);
        GameGUI.refreshMoney(player.getMoney());
        GameGUI.refreshImage();
    }
    
    /**
     * Set the towers range
     * @param tower 
     */
    public void setRange(Tower tower) {
        range.setX(tower.getX()-(int)tower.getRange()+40);
        range.setY(tower.getY()-(int)tower.getRange()+40);
        range.setWidth((int)tower.getRange()*2);
        range.setHeight((int)tower.getRange()*2);
        repaint();
    }

    /*
    Getters
     */
    public boolean isOver() {
        return enemies.isEmpty();
    }

    public Level getLevel() {
        return this.level;
    }

    public int getLevelNum() {
        return levelNum;
    }

    public int getMaxWave() {
        return this.maxWave;
    }

    public boolean getPaused() {
        return this.paused;
    }

    public int getPlayerLives() {
        return this.player.getLives();
    }

    public int getPlayerMoney() {
        return this.player.getMoney();
    }

    public Range getRange() {
        return this.range;
    }

    public boolean getShowTowers() {
        return this.showTowers;
    }
    
    /**
     * gives back places of X
     * @return 
     */
    public ArrayList<Tower> getTowers() {
        return this.towers;
    }
    
    /**
     * Gives back REAL towers not places of X
     * @return 
     */
    public ArrayList<Tower> getRealTowers() {
        return this.realTowers;
    }
    public Player getPlayer(){
        return this.player;
    }

    public int getWave() {
        return this.wave;
    }

    /*
    Setters
     */
    /**
     * See places where can be towers or not
     */
    public void changeShowTower() {
        if (!paused) {
            this.showTowers = !this.showTowers;
        }
    }

    public void setLevelNum(int levelNum) {
        this.levelNum = levelNum;
    }

    public static void setPaused(boolean paused) {
        GameEngine.paused = paused;
    }

    public void setRangeVisible(boolean b) {
        range.setVisible(b);
    }

    public void startTimer() {
        started = true;
    }
}
