package views;

import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public final class Level {

    private final int ROAD_WIDTH = 100;
    private final int ROAD_HEIGHT = 100;
    ArrayList<Road> roads;
    ArrayList<Tower> towers;
    ArrayList<Pair> coordinates;
    String directions;
    private Bus bus;

    public Level(String levelPath, String fileName) throws IOException {
        char c = levelPath.substring(levelPath.length() - 5).charAt(0);
        loadLevel(levelPath, c);
        loadCoordinates(fileName);
    }

    /**
     * Load the board from file and place the correct pictures on the frame
     * @param levelPath
     * @param c
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void loadLevel(String levelPath, char c) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(levelPath));
        roads = new ArrayList<>();
        towers = new ArrayList<>();
        int y = 0;
        String line;
        while ((line = br.readLine()) != null) {
            int x = 0;
            for (char type : line.toCharArray()) {
                if (type == 'r') {
                    Image image = new ImageIcon("src/data/pngs/road.png").getImage();
                    roads.add(new Road(x * ROAD_WIDTH, y * ROAD_HEIGHT, ROAD_WIDTH, ROAD_HEIGHT, image));
                } else if (type == '0') {
                    Image image = new ImageIcon("src/data/pngs/x.png").getImage();
                    towers.add(new Tower(x * ROAD_WIDTH + 25, y * ROAD_HEIGHT + 25, ROAD_WIDTH / 2, ROAD_HEIGHT / 2, 0, 0,0,0, image));
                } else if (type == 'b') {
                    if (c == '3' || c == '4') {
                        Image busImage = new ImageIcon("src/data/pngs/bus_r.png").getImage();
                        bus = new Bus(x * ROAD_WIDTH - 20, y * ROAD_HEIGHT - 90, 100, 300, busImage);
                    } else if (c == '2') {
                        Image busImage = new ImageIcon("src/data/pngs/bus.png").getImage();
                        bus = new Bus(x * ROAD_WIDTH - 100, y * ROAD_HEIGHT, 300, 100, busImage);
                    } else if (c == '1' || c == '5') {
                        Image busImage = new ImageIcon("src/data/pngs/bus.png").getImage();
                        bus = new Bus(x * ROAD_WIDTH - 100, y * ROAD_HEIGHT - 40, 300, 100, busImage);
                    }
                }
                x++;
            }
            y++;
        }
    }
    
    /**
     * Get coordinates and directions from file and makes pair from coordinates
     * @param fileName
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void loadCoordinates(String fileName) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        coordinates = new ArrayList<>();
        String line;
        directions = br.readLine();
        while ((line = br.readLine()) != null) {
            String[] pair = line.split(",");
            int p0 = Integer.parseInt(pair[0]);
            int p1 = Integer.parseInt(pair[1]);
            Pair p = new Pair(p0, p1);
            coordinates.add(p);
        }
    }

    public void draw(Graphics g) {
        for (Road road : roads) {
            road.draw(g);
        }
        bus.draw(g);
    }

    public class Pair {

        private final int x;
        private final int y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
    
    /**
     * Reset the game, clear the towers/road/coordinates arraylists
     */
    public void reset(){
        towers.clear();
        roads.clear();
        coordinates.clear();
    }

    // GETTER - SETTER
    public Bus getBus() {
        return this.bus;
    }

    public String getDirections() {
        return directions;
    }

    public ArrayList<Pair> getCoordinates() {

        return coordinates;
    }

    public ArrayList<Tower> getAllTower() {
        return this.towers;
    }
}
