package views;

import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import model.Player;

public class Tower extends Sprite {

    private final int buyingCost;
    private int refundCost;
    private int upgradeCost;
    private int level;
    private final int maxlevel;
    private final double lastAttack;
    private double range;
    private double power;
    private int timer;
    private Enemy firstEnemy;
    private Bullet bullet;
    private int type;
    private int countShoot;
    private boolean evolved;
    private int evolvedPath;
    private int timeShoot;

    public Tower(int x, int y, int width, int height, double dmg, double range, int worth, int type, Image image) {
        super(x, y, width, height, image);
        this.refundCost = worth;
        this.buyingCost = 15;
        this.power = dmg;
        this.range = range;
        this.lastAttack = 0;
        this.countShoot = 0;
        this.type = type;
        this.timer = 0;
        this.level = 1;
        this.maxlevel = 10;
        this.setBasicUpgradeCost(type);
        this.evolved = false;
        this.evolvedPath = 0;
        this.timeShoot = 100;
    }

    /**
     * Method to create new Tower object
     *
     * @param dmg
     * @param range
     * @param image
     * @return
     */
    public Tower createTower(double dmg, double range, int worth, int type, Image image) { //Creates real towers instead of X-es
        return new Tower(x - 15, y - 15, 80, 80, dmg, range, worth, type, image);
    }

    /**
     * refund money to the player, when it sells tower
     *
     * @param player
     */
    public void refundTower(Player player) {
        player.addMoney(refundCost);
    }

    /**
     * If the player has got enough money and the tower is not at max lvl ->
     * UPGRADE
     *
     * @param tower
     * @param player
     * @return
     */
    public boolean upgrade(Player player, int evolvePath) {
        if (level < maxlevel && player.getMoney() >= this.getUpgradeCost()) {
            if (level != 5) {
                this.setPower(getPower() * 1.1);
            } else {
                switch (this.getType()) {
                    case 2:
                        //disab
                        if (evolvePath == 1) {
                            //1. evolve
                            //FAGYASZT
                            this.setPower(0);
                            evolved = true;
                            this.image = new ImageIcon("src/data/pngs/frodisabgrey.png").getImage();
                        } else if (evolvePath == 2) {
                            //2. evolve
                            //GYORSAN LŐ
                            this.setPower(getPower() / 3);
                            timeShoot = 10;
                            evolved = true;
                            this.image = new ImageIcon("src/data/pngs/gyorsdisab.png").getImage();
                        }
                        break;
                    case 3:
                        //incog
                        if (evolvePath == 1) {
                            //1. evolve
                            /*MINDEN 5. lövésnél azonnal öl*/
                            evolved = true;
                            this.image = new ImageIcon("src/data/pngs/hs.png").getImage();
                        } else if (evolvePath == 2) {
                            //2. evolve
                            //SASSZEM
                            this.setRange(getRange() * 2.0);
                            this.image = new ImageIcon("src/data/pngs/eye.png").getImage();

                        }
                        break;
                    case 1:
                        //afromagyarok
                        if (evolvePath == 1) {
                            //1. evolve
                            //MINDENKIT ÖL
                            this.setPower(getPower() * 0.2);
                            evolved = true;
                            this.image = new ImageIcon("src/data/pngs/mindenkitsebez.png").getImage();
                        } else if (evolvePath == 2) {
                            //2. evolve
                            //DUPLA SEBZÉS
                            this.setPower(getPower() * 2.0);
                            this.image = new ImageIcon("src/data/pngs/duplasebzes.png").getImage();
                        }
                        break;
                }
                this.evolvedPath = evolvePath;
                //level += 1;
            }
            level += 1;
            
            player.addMoney(-1 * this.getUpgradeCost());
            this.setUpgradeCost((int) (this.getUpgradeCost() * this.getLevel() * 0.5));
            this.increaseRefoundCost();
            return true;
        }
        return false;
    }

    /**
     * Depends on timer, tower shoot bullet to the first enemy that is in
     * tower's range
     *
     * @param enemies
     * @param bullet
     */
    public void shoot(ArrayList<Enemy> enemies, Bullet bullet) {
        boolean found = false;
        if (timer < timeShoot) {
            timer++;
        } else {
            if (!evolved) {
                bullet.show();
                int i = 0;
                while (!found && i < enemies.size()) {
                    Enemy enemy = enemies.get(i);
                    if (inRange(enemy)) {
                        found = true;
                        firstEnemy = enemy;
                        bullet.setHasDir(firstEnemy.getX(), firstEnemy.getY());
                        if (bullet.getVisibility()) {
                            hit();
                        }
                        timer = 0;
                    }
                    i++;
                }
            } else {

                if (evolvedPath == 1) {
                    int i;
                    switch (this.getType()) {
                        
                        case 1:
                            //MINDENKIT SEBEZ - DONE
                            for (Enemy enemy : enemies) {
                                enemy.takeDamage(this.getPower());
                            }
                            break;
                        case 2:
                            //FAGYASZT - DONE
                            bullet.show();
                            i = 0;
                            while (!found && i < enemies.size()) {
                                Enemy enemy = enemies.get(i);
                                if (inRange(enemy)) {
                                    found = true;
                                    firstEnemy = enemy;
                                    bullet.setHasDir(firstEnemy.getX(), firstEnemy.getY());
                                    if (bullet.getVisibility()) {
                                        firstEnemy.setTimer(50);
                                        hit();
                                    }
                                }
                                i++;
                            }
                            break;

                        case 3:
                            //MINDEN ÖTÖDIK EGYBŐL ÖL - DONE
                            if (countShoot != 5) {
                                bullet.show();
                                i = 0;
                                while (!found && i < enemies.size()) {
                                    Enemy enemy = enemies.get(i);
                                    if (inRange(enemy)) {
                                        found = true;
                                        countShoot++;
                                        firstEnemy = enemy;
                                        bullet.setHasDir(firstEnemy.getX(), firstEnemy.getY());
                                        if (bullet.getVisibility()) {
                                            hit();
                                        }
                                    }
                                    i++;
                                }
                            } else {
                                bullet.show();
                                i = 0;
                                while (!found && i < enemies.size()) {
                                    Enemy enemy = enemies.get(i);
                                    if (inRange(enemy)) {
                                        found = true;
                                        countShoot++;
                                        firstEnemy = enemy;
                                        bullet.setHasDir(firstEnemy.getX(), firstEnemy.getY());
                                        if (bullet.getVisibility()) {
                                            hit();
                                            firstEnemy.kill();
                                            countShoot = 0;
                                        }
                                    }
                                    i++;
                                }
                            }
                            break;
                    }
                    timer = 0;
                    found = false;
                } else {
                    switch (this.getType()) {
                        case 2:
                            //GYORSAN SHOOT SHOOT - DONE
                            bullet.show();
                            int i = 0;
                            while (!found && i < enemies.size()) {
                                Enemy enemy = enemies.get(i);
                                if (inRange(enemy)) {
                                    found = true;
                                    firstEnemy = enemy;
                                    bullet.setHasDir(firstEnemy.getX(), firstEnemy.getY());
                                    if (bullet.getVisibility()) {
                                        hit();
                                    }
                                    timer = 0;
                                }
                                i++;
                            }
                            break;
                    }
                }
            }
        }
    }

    /**
     * Enemy is in tower's range or not
     *
     * @param target
     * @return
     */
    public boolean inRange(Enemy target) {
        double a = Math.abs(target.getX() - this.x);
        double b = Math.abs(target.getY() - this.y);

        double z = Math.sqrt(a * a + b * b);

        return (z < this.getRange());
    }

    /**
     * if the bullet collides with the enemy, it takes damage
     */
    public void hit() {
        firstEnemy.takeDamage(this.power);
    }
    // GETTER - SETTER

    public int getBuyingCost() {
        return buyingCost;
    }

    public int getRefundCost() {
        return refundCost;
    }

    public int getUpgradeCost() {
        return this.upgradeCost;
    }

    public void setBasicUpgradeCost(int type) {
        switch (this.type) {
            default:
                this.upgradeCost = 0;
                break;
            case 1:
                this.upgradeCost = 6;
                break;
            case 2:
                this.upgradeCost = 10;
                break;
            case 3:
                this.upgradeCost = 15;
                break;

        }
    }

    public void setUpgradeCost(int upgradeCost) {
        this.upgradeCost = upgradeCost;
    }

    public int getLevel() {
        return this.level;
    }

    public int getMaxlevel() {
        return maxlevel;
    }

    public double getLastAttack() {
        return lastAttack;
    }

    public double getRange() {
        return this.range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public double getPower() {
        return power;
    }

    public Bullet getBullet() {
        return this.bullet;
    }

    public Enemy getFirstEnemy() {
        return firstEnemy;
    }

    public Image getImage() {
        return image;
    }

    public void increaseRefoundCost() {
        this.refundCost += (this.getUpgradeCost() / 2);
    }

    /*NEW*/
    public int getCountShoot() {
        return countShoot;
    }

    public int getType() {
        return type;
    }
    public boolean getEvolved(){
        return evolved;
    }

}
