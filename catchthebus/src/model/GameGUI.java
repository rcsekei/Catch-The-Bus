package model;

import views.Tower;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class GameGUI extends JFrame {

    private final JFrame frame;
    private static JLabel lives;
    private final JLabel coin;
    private final JLabel heart;
    private static JLabel money;
    private final JButton pause;
    private static JLabel level;
    private static JButton tower;
    private static JButton tower2;
    private static JButton tower3;
    private static JLabel towerCost;
    private static JLabel towerCost2;
    private static JLabel towerCost3;
    private static JButton startWave;
    private static JButton fastWave;
    private static JButton goLevel;
    private static JLabel roundCounter;

    //NEW
    private final JLabel pBg;
    private final JLabel pWindow;
    private final JButton pResume;
    private final JButton pExit;
    private static GameEngine gameArea;
    private ArrayList<Tower> towers;
    private ArrayList<Tower> realTowers;
    private int type;
    Tower temp;
    private final int maxWave;
    private JLabel upgradeLabel;
    private JButton xBtn;
    private JButton delBtn;
    private JButton upgBtn;
    private JButton evolveBtn1;
    private JButton evolveBtn2;
    private JLabel twLevel;
    private JLabel twPower;
    private JLabel twRange;
    private JLabel twCost;
    private JLabel twRefCost;

    //upgrade label
    private int uLabelX;
    private int uLabelY;

    public GameGUI() {
        gameArea = new GameEngine();
        maxWave = gameArea.getMaxWave();
        towers = new ArrayList<>();
        realTowers = new ArrayList<>();
        upgradeLabel = new JLabel();
        twLevel = new JLabel();
        twPower = new JLabel();
        twRange = new JLabel();
        twCost = new JLabel();
        twRefCost = new JLabel();
        xBtn = new JButton();
        delBtn = new JButton();
        upgBtn = new JButton("Upgrade");
        evolveBtn1 = new JButton();
        evolveBtn2 = new JButton();
        towers = gameArea.getLevel().getAllTower();
        gameArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                towers = gameArea.getTowers();
                if (!gameArea.getPaused()) {
                    if (gameArea.getShowTowers()) {
                        boolean found = false;
                        int x = e.getX();
                        int y = e.getY();
                        for (Tower tower : towers) {
                            if (tower.getX() + 50 > x && tower.getX() < x && tower.getY() < y && tower.getY() + 50 > y) {
                                temp = tower;
                                found = true;
                            }
                        }
                        if (found) {
                            gameArea.addTower(temp, type);
                            gameArea.changeShowTower();
                        }
                    } else {
                        /*TODO upgrade*/
                        realTowers = gameArea.getRealTowers();
                        boolean found = false;
                        int x = e.getX();
                        int y = e.getY();
                        for (Tower tower : realTowers) {
                            if (tower.getX() + 50 > x && tower.getX() < x && tower.getY() < y && tower.getY() + 50 > y) {
                                temp = tower;
                                found = true;
                            }
                        }
                        if (found) {
                            arrangeUpgradeMenu(e.getX(), e.getY());
                            upgradeLabel.setOpaque(true);
                            upgradeLabel.setBackground(new java.awt.Color(220, 220, 220));
                            upgradeLabel.setBounds(uLabelX, uLabelY, 200, 300);
                            upgradeLabel.setBorder(BorderFactory.createLineBorder(Color.black));
                            upgradeLabel.setVisible(true);

                            gameArea.setRange(temp);
                            gameArea.setRangeVisible(true);

                            twLevel.setText("Lvl: " + temp.getLevel());
                            twLevel.setFont(new Font("Courier New", Font.BOLD, 20));
                            twLevel.setOpaque(true);
                            twLevel.setBackground(new java.awt.Color(229, 223, 215));
                            twLevel.setBounds(upgradeLabel.getBounds().x, upgradeLabel.getBounds().y, 200, 60);
                            twLevel.setBorder(BorderFactory.createLineBorder(Color.black));
                            twLevel.setHorizontalAlignment(SwingConstants.CENTER);
                            twLevel.setVisible(true);

                            twPower.setText("Power: " + (int) temp.getPower() + "(+" + (Math.round((temp.getPower() * 0.1) * 10) / 10.0) + ")");
                            twPower.setFont(new Font("Courier New", Font.ITALIC, 16));
                            twPower.setOpaque(true);
                            twPower.setBackground(new java.awt.Color(220, 220, 220));
                            twPower.setBounds(upgradeLabel.getBounds().x + 15, upgradeLabel.getBounds().y + 75, 150, 40);
                            twPower.setBorder(null);
                            twPower.setVisible(true);

                            twRange.setText("Range: " + (int) temp.getRange());
                            twRange.setFont(new Font("Courier New", Font.ITALIC, 16));
                            twRange.setOpaque(true);
                            twRange.setBackground(new java.awt.Color(220, 220, 220));
                            twRange.setBounds(upgradeLabel.getBounds().x + 15, upgradeLabel.getBounds().y + 115, 150, 40);
                            twRange.setBorder(null);
                            twRange.setVisible(true);

                            twCost.setText("Cost: " + temp.getUpgradeCost());
                            twCost.setFont(new Font("Courier New", Font.ITALIC, 16));
                            twCost.setOpaque(true);
                            twCost.setBackground(new java.awt.Color(220, 220, 220));
                            twCost.setBounds(upgradeLabel.getBounds().x + 15, upgradeLabel.getBounds().y + 155, 150, 40);
                            twCost.setBorder(null);
                            twCost.setVisible(true);

                            twRefCost.setText("(+" + temp.getRefundCost() + ")");
                            twRefCost.setFont(new Font("Courier New", Font.ITALIC, 10));
                            twRefCost.setOpaque(true);
                            twRefCost.setBackground(new java.awt.Color(220, 220, 220));
                            twRefCost.setBounds(upgradeLabel.getBounds().x + 130, upgradeLabel.getBounds().y + 275, 40, 20);
                            twRefCost.setBorder(null);
                            twRefCost.setVisible(true);

                            delBtn.setIcon(new ImageIcon("src/data/pngs/bin.png"));
                            delBtn.setOpaque(true);
                            delBtn.setBounds(upgradeLabel.getBounds().x + 130, upgradeLabel.getBounds().y + 230, 40, 40);
                            delBtn.setBackground(new java.awt.Color(220, 0, 0));
                            delBtn.setBorder(BorderFactory.createLineBorder(Color.black));
                            delBtn.setVisible(true);

                            if (temp.getLevel() != 5) {
                                upgBtn.setOpaque(true);
                                upgBtn.setFont(new Font("Courier New", Font.BOLD, 14));
                                upgBtn.setForeground(new java.awt.Color(233, 233, 233));
                                upgBtn.setBounds(upgradeLabel.getBounds().x + 30, upgradeLabel.getBounds().y + 230, 80, 40);
                                upgBtn.setBackground(new java.awt.Color(153, 130, 96));
                                upgBtn.setBorder(BorderFactory.createLineBorder(Color.black));
                                upgBtn.setVisible(true);
                            } else {
                                evolveBtn1.setVisible(true);
                                evolveBtn2.setVisible(true);
                                upgBtn.setVisible(false);
                                delBtn.setBounds(upgradeLabel.getBounds().x + 145, upgradeLabel.getBounds().y + 220, 40, 40);
                                twRefCost.setBounds(upgradeLabel.getBounds().x + 145, upgradeLabel.getBounds().y + 260, 40, 20);
                            }
                            xBtn.setIcon(new ImageIcon("src/data/pngs/xBtn.png"));
                            xBtn.setOpaque(true);
                            xBtn.setBounds(upgradeLabel.getBounds().x + 165, upgradeLabel.getBounds().y + 5, 30, 30);
                            xBtn.setBorder(null);
                            xBtn.setBackground(new java.awt.Color(229, 223, 215));
                            xBtn.setVisible(true);

                            xBtn.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    twLevel.setVisible(false);
                                    twPower.setVisible(false);
                                    twRange.setVisible(false);
                                    twCost.setVisible(false);
                                    twRefCost.setVisible(false);
                                    upgradeLabel.setVisible(false);
                                    xBtn.setVisible(false);
                                    delBtn.setVisible(false);
                                    upgBtn.setVisible(false);
                                    evolveBtn1.setVisible(false);
                                    evolveBtn2.setVisible(false);
                                    gameArea.setRangeVisible(false);
                                }
                            });
                        } else {
                            twLevel.setVisible(false);
                            twPower.setVisible(false);
                            twRange.setVisible(false);
                            twCost.setVisible(false);
                            twRefCost.setVisible(false);
                            upgradeLabel.setVisible(false);
                            xBtn.setVisible(false);
                            delBtn.setVisible(false);
                            upgBtn.setVisible(false);
                            evolveBtn1.setVisible(false);
                            evolveBtn2.setVisible(false);
                            gameArea.setRangeVisible(false);
                        }
                    }
                }
            }
        });
        {//Lives Label
            heart = new JLabel();
            heart.setIcon(new ImageIcon("src/data/pngs/heart.png"));
            heart.setBounds(640, 25, 35, 30);
            heart.setBorder(null);
            heart.setBackground(null);

            lives = new JLabel(Integer.toString(gameArea.getPlayerLives()));
            lives.setHorizontalAlignment(JLabel.CENTER);
            lives.setPreferredSize(new Dimension(150, 40));
            lives.setLayout(null);
            lives.setBounds(635, 20, 150, 40);
            lives.setVisible(true);
            lives.setBorder(BorderFactory.createLineBorder(Color.black));
            lives.setOpaque(true);
            lives.setBackground(new java.awt.Color(189, 189, 189));
        }

        {//Money Label
            coin = new JLabel();
            coin.setIcon(new ImageIcon("src/data/pngs/dollar.png"));
            coin.setBounds(820, 25, 35, 30);
            coin.setBorder(null);
            coin.setBackground(null);

            money = new JLabel(Integer.toString(gameArea.getPlayerMoney()));
            money.setHorizontalAlignment(JLabel.CENTER);
            money.setPreferredSize(new Dimension(150, 40));
            money.setLayout(null);
            money.setBounds(815, 20, 150, 40);
            money.setVisible(true);
            money.setBorder(BorderFactory.createLineBorder(Color.black));
            money.setOpaque(true);
            money.setBackground(new java.awt.Color(189, 189, 189));
        }

        {//Pause Button
            pause = new JButton();
            pause.setIcon(new ImageIcon("src/data/pngs/pause.png"));
            pause.setBounds(1540, 20, 40, 40);
            pause.setBorder(null);
            pause.setBackground(null);

            pause.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pauseGame();
                }
            });
        }
        delBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                twLevel.setVisible(false);
                twPower.setVisible(false);
                twRange.setVisible(false);
                twCost.setVisible(false);
                upgradeLabel.setVisible(false);
                xBtn.setVisible(false);
                delBtn.setVisible(false);
                upgBtn.setVisible(false);
                twRefCost.setVisible(false);
                evolveBtn1.setVisible(false);
                evolveBtn2.setVisible(false);
                gameArea.setRangeVisible(false);
                gameArea.sellTower(temp, type);
            }
        });
        upgBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameArea.getPlayerMoney() >= temp.getUpgradeCost()) {
                    temp.upgrade(gameArea.getPlayer(), 0);
                    twLevel.setText("Lvl: " + temp.getLevel());
                    twPower.setText("Power: " + (int) temp.getPower() + "\n(+" + (Math.round((temp.getPower() * 0.1) * 10) / 10.0) + ")");
                    twRange.setText("Range: " + (int) temp.getRange());
                    twCost.setText("Cost: " + temp.getUpgradeCost());
                    twRefCost.setText("(+" + temp.getRefundCost() + ")");
                    refreshMoney(gameArea.getPlayerMoney());
                    if (temp.getLevel() == 5) {
                        upgBtn.setVisible(false);
                        switch (temp.getType()) {
                            case 2: {
                                evolveBtn1.setText("FAGYASZT");
                                evolveBtn2.setText("GYORS TÜZELŐ");

                                break;
                            }
                            case 3: {
                                evolveBtn1.setText("5. GYILKOS");
                                evolveBtn2.setText("SASSZEM");
                                break;
                            }
                            default: {
                                evolveBtn1.setText("MINDENKIT ÖL");
                                evolveBtn2.setText("DUPLA SEBZÉS");
                                break;
                            }
                        }
                        evolveBtn1.setOpaque(true);
                        evolveBtn1.setFont(new Font("Courier New", Font.BOLD, 14));
                        evolveBtn1.setForeground(new java.awt.Color(233, 233, 233));
                        evolveBtn1.setBounds(upgradeLabel.getBounds().x + 15, upgradeLabel.getBounds().y + 195, 115, 40);
                        evolveBtn1.setBackground(new java.awt.Color(65, 42, 42));
                        evolveBtn1.setBorder(BorderFactory.createLineBorder(Color.black));
                        evolveBtn1.setVisible(true);

                        evolveBtn2.setOpaque(true);
                        evolveBtn2.setFont(new Font("Courier New", Font.BOLD, 14));
                        evolveBtn2.setForeground(new java.awt.Color(233, 233, 233));
                        evolveBtn2.setBounds(upgradeLabel.getBounds().x + 15, upgradeLabel.getBounds().y + 245, 115, 40);
                        evolveBtn2.setBackground(new java.awt.Color(65, 42, 42));
                        evolveBtn2.setBorder(BorderFactory.createLineBorder(Color.black));
                        evolveBtn2.setVisible(true);
                        delBtn.setBounds(upgradeLabel.getBounds().x + 145, upgradeLabel.getBounds().y + 220, 40, 40);
                        twRefCost.setBounds(upgradeLabel.getBounds().x + 145, upgradeLabel.getBounds().y + 260, 40, 20);
                    }
                    refreshImage();
                }
            }
        });
        evolveBtn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameArea.getPlayerMoney() >= temp.getUpgradeCost()) {
                    temp.upgrade(gameArea.getPlayer(), 1);
                    twLevel.setText("Lvl: " + temp.getLevel());
                    twPower.setText("Power: " + (int) temp.getPower() + " (+" + (Math.round((temp.getPower() * 0.1) * 10) / 10.0) + ")");
                    twRange.setText("Range: " + (int) temp.getRange());
                    twCost.setText("Cost: " + temp.getUpgradeCost());
                    twRefCost.setText("(+" + temp.getRefundCost() + ")");
                    upgBtn.setVisible(true);
                    evolveBtn1.setVisible(false);
                    evolveBtn2.setVisible(false);
                    delBtn.setBounds(upgradeLabel.getBounds().x + 130, upgradeLabel.getBounds().y + 230, 40, 40);
                    twRefCost.setBounds(upgradeLabel.getBounds().x + 130, upgradeLabel.getBounds().y + 275, 40, 20);
                }
                refreshImage();
            }
        });
        evolveBtn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameArea.getPlayerMoney() >= temp.getUpgradeCost()) {
                    temp.upgrade(gameArea.getPlayer(), 2);
                    twLevel.setText("Lvl: " + temp.getLevel());
                    twPower.setText("Power: " + (int) temp.getPower() + " (+" + (Math.round((temp.getPower() * 0.1) * 10) / 10.0) + ")");
                    twRange.setText("Range: " + (int) temp.getRange());
                    twCost.setText("Cost: " + temp.getUpgradeCost());
                    twRefCost.setText("(+" + temp.getRefundCost() + ")");
                    upgBtn.setVisible(true);
                    evolveBtn1.setVisible(false);
                    evolveBtn2.setVisible(false);
                    delBtn.setBounds(upgradeLabel.getBounds().x + 130, upgradeLabel.getBounds().y + 230, 40, 40);
                    twRefCost.setBounds(upgradeLabel.getBounds().x + 130, upgradeLabel.getBounds().y + 275, 40, 20);
                }
                refreshImage();
            }
        });

        {//Level Label
            level = new JLabel("Level " + Integer.toString(gameArea.getLevelNum()));
            level.setFont(new Font("Courier New", Font.BOLD, 20));
            level.setHorizontalAlignment(JLabel.CENTER);
            level.setFont(new Font("Courier New", Font.BOLD, 14));
            level.setBounds(10, 20, 100, 40);
            level.setOpaque(true);
            level.setBorder(BorderFactory.createLineBorder(Color.black, 1));
            level.setBackground(new java.awt.Color(189, 189, 189));
        }

        {//Tower1 Button + Cost Label
            /*AFROMAGYAR CSOPORT*/
            tower = new JButton();
            tower.setIcon(affordable(10, 1));
            tower.setBounds(1432, 780, 50, 50);
            tower.setBorder(null);
            tower.setBackground(null);
            tower.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (gameArea.getPlayerMoney() >= 10) {
                        gameArea.changeShowTower();
                        type = 1;
                    }
                }
            });

            towerCost = new JLabel("10");
            towerCost.setHorizontalAlignment(JLabel.CENTER);
            towerCost.setBounds(1432, 835, 50, 20);
            towerCost.setBorder(BorderFactory.createLineBorder(Color.black));
            towerCost.setOpaque(true);
            towerCost.setBackground(new java.awt.Color(189, 189, 189));

            /*KEREKESSZÉKES*/
            tower2 = new JButton();
            tower2.setIcon(affordable(15, 2));
            tower2.setBounds(1370, 780, 50, 50);
            tower2.setBorder(null);
            tower2.setBackground(null);
            tower2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (gameArea.getPlayerMoney() >= 15) {
                        gameArea.changeShowTower();
                        type = 2;
                    }
                }
            });

            towerCost2 = new JLabel("15");
            towerCost2.setHorizontalAlignment(JLabel.CENTER);
            towerCost2.setBounds(1370, 835, 50, 20);
            towerCost2.setBorder(BorderFactory.createLineBorder(Color.black));
            towerCost2.setOpaque(true);
            towerCost2.setBackground(new java.awt.Color(189, 189, 189));

            /*ELLENŐR*/
            tower3 = new JButton();
            tower3.setIcon(affordable(20, 3));
            tower3.setBounds(1308, 780, 50, 50);
            tower3.setBorder(null);
            tower3.setBackground(null);
            tower3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (gameArea.getPlayerMoney() >= 20) {
                        gameArea.changeShowTower();
                        type = 3;
                    }
                }
            });
            towerCost3 = new JLabel("20");
            towerCost3.setHorizontalAlignment(JLabel.CENTER);
            towerCost3.setBounds(1308, 835, 50, 20);
            towerCost3.setBorder(BorderFactory.createLineBorder(Color.black));
            towerCost3.setOpaque(true);
            towerCost3.setBackground(new java.awt.Color(189, 189, 189));

        }
        { //start round 
            startWave = new JButton();
            startWave.setIcon(new ImageIcon("src/data/pngs/play.png"));
            startWave.setOpaque(true);
            startWave.setBounds(1483, 740, 90, 90);
            startWave.setBorder(null);
            startWave.setBackground(null);

            fastWave = new JButton();
            fastWave.setIcon(new ImageIcon("src/data/pngs/play2.png"));
            fastWave.setOpaque(true);
            fastWave.setBounds(1483, 740, 90, 90);
            fastWave.setBorder(null);
            fastWave.setBackground(null);

            goLevel = new JButton();
            goLevel.setIcon(new ImageIcon("src/data/pngs/go.png"));
            goLevel.setOpaque(true);
            goLevel.setBounds(1483, 740, 90, 90);
            goLevel.setBorder(null);
            goLevel.setBackground(null);

            goLevel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gameArea.startTimer();
                    goLevel.setVisible(false);
                    startWave.setVisible(true);
                    fastWave.setVisible(false);
                }
            });

            startWave.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (gameArea.started) {
                        gameArea.newFrameTimer.setDelay(300 / 60);
                        startWave.setVisible(false);
                        fastWave.setVisible(true);
                    } else {
                        gameArea.startTimer();
                        startWave.setVisible(true);
                        fastWave.setVisible(false);
                    }
                }
            });

            fastWave.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gameArea.newFrameTimer.setDelay(16);
                    fastWave.setVisible(false);
                    startWave.setVisible(true);
                }
            });

            roundCounter = new JLabel(Integer.toString(gameArea.getWave()) + "/" + maxWave + " rounds");
            roundCounter.setHorizontalAlignment(JLabel.CENTER);
            roundCounter.setBounds(1488, 835, 90, 20);
            roundCounter.setBorder(BorderFactory.createLineBorder(Color.black));
            roundCounter.setOpaque(true);
            roundCounter.setBackground(new java.awt.Color(189, 189, 189));

        }

        {/*PAUSE MENU*/

            pBg = new JLabel();
            pBg.setBounds(0, 0, 1600, 900);
            pBg.setBackground(new java.awt.Color(10, 10, 10, 200));

            pBg.setOpaque(true);

            //window
            pWindow = new JLabel();
            pWindow.setBounds(500, 275, 600, 350);
            pWindow.setBackground(new java.awt.Color(223, 197, 161, 150));
            pWindow.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            pWindow.setOpaque(true);

            //resume btn
            pResume = new JButton("Resume");
            pResume.setFont(new Font("Courier New", Font.BOLD, 20));
            pResume.setBounds(600, 375, 400, 50);
            pResume.setBackground(new java.awt.Color(189, 189, 189));
            pResume.setHorizontalAlignment(JLabel.CENTER);//NEW
            pResume.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    resumeGame();
                }
            });

            //exit btn
            pExit = new JButton("Exit");
            pExit.setFont(new Font("Courier New", Font.BOLD, 20));
            pExit.setBounds(600, 475, 400, 50);
            pExit.setBackground(new java.awt.Color(189, 189, 189));
            pExit.setHorizontalAlignment(JLabel.CENTER);
            pExit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });

        }
        gameArea.setLayout(null);
        gameArea.add(xBtn);
        gameArea.add(delBtn);
        gameArea.add(upgBtn);
        gameArea.add(evolveBtn1);
        gameArea.add(evolveBtn2);
        gameArea.add(twLevel);
        gameArea.add(twPower);
        gameArea.add(twRange);
        gameArea.add(twCost);
        gameArea.add(twRefCost);
        gameArea.add(upgradeLabel);
        gameArea.add(coin);
        gameArea.add(level);
        gameArea.add(heart);
        gameArea.add(lives);
        gameArea.add(money);
        gameArea.add(pause);
        gameArea.add(tower);
        gameArea.add(towerCost);
        gameArea.add(tower2);
        gameArea.add(towerCost2);
        gameArea.add(tower3);
        gameArea.add(towerCost3);
        gameArea.add(goLevel);
        gameArea.add(startWave);
        gameArea.add(fastWave);
        gameArea.add(roundCounter);

        frame = new JFrame("Catch The Bus ~ Anrope Studios©");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1600, 900));
        frame.setResizable(false);

        frame.getContentPane().add(gameArea);

        frame.pack();
        frame.setVisible(true);
    }

    public void pauseGame() {
        gameArea.setPaused(true);  //Megállítja a játékot
        gameArea.remove(pause);
        gameArea.add(pResume);
        gameArea.add(pExit);
        gameArea.add(pWindow);

    }

    public void resumeGame() {
        gameArea.setPaused(false);  //Elindítja a játékot
        gameArea.remove(pWindow);
        gameArea.remove(pResume);
        gameArea.remove(pExit);
        gameArea.add(pause);
    }

    public void arrangeUpgradeMenu(int eX, int eY) {
        if (eX > 200 && eY > 300) {
            uLabelX = temp.getX() - 200;
            uLabelY = temp.getY() - 300;
        } else if (eX < 400 && eY < 350) {
            uLabelX = temp.getX() + 75;
            uLabelY = temp.getY() + 75;
        } else if (eX > 300 && eY < 350) {
            uLabelX = temp.getX() - 180;
            uLabelY = temp.getY() + 80;
        } else if (eX < 350 && eY > 300) {
            uLabelX = temp.getX() + 80;
            uLabelY = temp.getY() - 295;
        }
    }

    public static void refreshLives(int l) {
        lives.setText(Integer.toString(l));
    }

    public static void refreshWaves(int w) {
        roundCounter.setText(Integer.toString(w) + "/30 rounds");
    }

    public static void refreshLevel(int a) {
        level.setText("Level " + Integer.toString(a));
    }

    public static void refreshMoney(int a) {
        money.setText(Integer.toString(a));
    }

    public static void refreshCost1(int c) {
        towerCost.setText(Integer.toString(c));
    }

    public static void refreshCost2(int c) {
        towerCost2.setText(Integer.toString(c));
    }

    public static void refreshCost3(int c) {
        towerCost3.setText(Integer.toString(c));
    }

    public static ImageIcon affordable(int x, int type) {
        if (gameArea.getPlayerMoney() < x) {
            switch (type) {
                case 1:
                    return new ImageIcon("src/data/pngs/crowred.png");
                case 2:
                    return new ImageIcon("src/data/pngs/disabred.png");
                case 3:
                    return new ImageIcon("src/data/pngs/incogred.png");
                default:
                    return new ImageIcon("src/data/pngs/x.png");
            }
        } else {
            switch (type) {
                case 1:
                    return new ImageIcon("src/data/pngs/crowgrey.png");
                case 2:
                    return new ImageIcon("src/data/pngs/disabgrey.png");
                case 3:
                    return new ImageIcon("src/data/pngs/incoggrey.png");
                default:
                    return new ImageIcon("src/data/pngs/x.png");
            }
        }
    }

    public static void refreshImage() {
        tower.setIcon(affordable(10, 1));
        tower2.setIcon(affordable(15, 2));
        tower3.setIcon(affordable(20, 3));
    }

    public static void goLevel() {
        gameArea.newFrameTimer.setDelay(1000 / 60);
        goLevel.setVisible(true);
        startWave.setVisible(false);
        fastWave.setVisible(false);
    }
}
