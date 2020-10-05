package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameStart extends JFrame {

    private final JFrame frame;
    private JPanel start;
    private JLabel menuLabel;
    private JLabel bus;
    private JLabel brand;
    private JLabel logo;
    private JLabel anna;
    private JLabel robi;
    private JLabel peti;
    private final JButton play;
    private final JButton exit;
    private final int ROAD_WIDTH = 100;
    private final int ROAD_HEIGHT = 100;

    public GameStart() throws FileNotFoundException, IOException {
        start = new JPanel();
        start.setBackground(new java.awt.Color(223, 197, 161));

        logo = new JLabel();
        logo.setIcon(new ImageIcon("src/data/pngs/proba.png"));
        
        logo.setHorizontalAlignment(JLabel.CENTER);
        logo.setBounds(380, 50, 910, 200);
        logo.setBackground(new java.awt.Color(221,197,161,50));
        logo.setOpaque(true);
        
        brand = new JLabel();
        brand.setIcon(new ImageIcon("src/data/pngs/logo4.png"));
        brand.setHorizontalAlignment(JLabel.CENTER);
        brand.setBounds(500, 300, 1363, 770);

        bus = new JLabel();
        bus.setIcon(new ImageIcon("src/data/pngs/bus.png"));
        bus.setOpaque(true);
        bus.setBounds(100, 760, 300, 100);
        bus.setBorder(null);
        bus.setBackground(null);

        menuLabel = new JLabel();
        menuLabel.setBounds(600, 275, 400, 250);
        menuLabel.setBackground(new java.awt.Color(255,255,255,100));
        menuLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        menuLabel.setOpaque(true);

        {
            play = new JButton("Play");
            play.setBounds(725, 315, 150, 70);
            play.setBackground(new java.awt.Color(39, 39, 39));
            play.setForeground(new java.awt.Color(233, 233, 233));
            play.setFont(new Font("Courier New", Font.PLAIN, 30));
            //play.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            play.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    startGame();
                }
            });
        }

        {
            exit = new JButton("Exit");
            exit.setBounds(725, 415, 150, 70);
            exit.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            exit.setBackground(new java.awt.Color(39, 39, 39));
            exit.setForeground(new java.awt.Color(233, 233, 233));
            exit.setFont(new Font("Courier New", Font.PLAIN, 30));
            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
        }

        start.setLayout(null);
        start.add(logo);
        start.add(brand);
        start.add(bus);
        start.add(play);
        start.add(exit);
        start.add(menuLabel);
        drawGame("src/data/level1.txt");
        frame = new JFrame("Catch The Bus ~ Anrope Studios©");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1600, 900));
        frame.setResizable(false);

        frame.getContentPane().add(start);
        frame.pack();
        frame.setVisible(true);
    }

    public void startGame() {
        frame.setVisible(false);
        GameGUI game = new GameGUI();
    }

    public void drawGame(String filename) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        int y = 0;
        while ((line = br.readLine()) != null) {
            int x = 0;
            for (char type : line.toCharArray()) {
                if (type == 'r' || type == 'b') {
                    JLabel road = new JLabel();
                    road.setIcon(new ImageIcon("src/data/pngs/road.png"));
                    road.setBounds(x * ROAD_WIDTH, y * ROAD_HEIGHT, ROAD_WIDTH, ROAD_HEIGHT);
                    road.setBorder(null);
                    road.setBackground(null);
                    start.add(road);
                } else if (type == '0') {

                }
                x++;
            }
            y++;
        }

    }
}
