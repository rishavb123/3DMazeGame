import java.awt.Graphics;
import java.awt.Color;

public class Explorer extends GameObject {

    public static final int LEFT = 0;
    public static final int UP = 1;
    public static final int RIGHT = 2;
    public static final int DOWN = 3;

    private int direction;
    private Maze maze;

    private int vision = 5;

    private int health;
    private int maxHealth;
    private int moves = 0;

    public Explorer(int x, int y, int health) {
        super(x, y);
        direction = RIGHT;
        this.health = health;
        maxHealth = health;
    }

    public static int flip(int direction) {
        switch(direction) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case RIGHT:
                return LEFT;
            case LEFT:
                return RIGHT;
        }
        return -1;
    }

    public Location nextLocation(int direction) {
        Location nextLoc = new Location(location.getX(), location.getY());
        switch(direction) {
            case LEFT:
                nextLoc.setX(nextLoc.getX() - 1);
                break;
            case RIGHT:
                nextLoc.setX(nextLoc.getX() + 1);
                break;
            case UP:
                nextLoc.setY(nextLoc.getY() - 1);
                break;
            case DOWN:
                nextLoc.setY(nextLoc.getY() + 1);
                break;
        }
        return maze.teleportTo(nextLoc);
    }
    
    public static Location nextLocation(int direction, Location location) {
        Location nextLoc = new Location(location.getX(), location.getY());        
        switch(direction) {
            case LEFT:
                nextLoc.setX(nextLoc.getX() - 1);
                break;
            case RIGHT:
                nextLoc.setX(nextLoc.getX() + 1);
                break;
            case UP:
                nextLoc.setY(nextLoc.getY() - 1);
                break;
            case DOWN:
                nextLoc.setY(nextLoc.getY() + 1);
                break;
        }
        return nextLoc;
    }

    public void move() {
        Location nextLoc = nextLocation(direction);
        if(nextLoc.getX() >= 0 && nextLoc.getX() < maze.getWidth() && nextLoc.getY() >= 0 && nextLoc.getY() < maze.getHeight() && (maze.get(nextLoc) == null || maze.get(nextLoc) instanceof PhaseObject)) {
            maze.set(location, null);
            maze.set(nextLoc, this);
            location = nextLoc;
            health--;
            moves++;
        }
    }

    public void moveBack() {
        Location nextLoc = nextLocation(flip(direction));
        if(nextLoc.getX() >= 0 && nextLoc.getX() < maze.getWidth() && nextLoc.getY() >= 0 && nextLoc.getY() < maze.getHeight() && (maze.get(nextLoc) == null || maze.get(nextLoc) instanceof PhaseObject)) {
            maze.set(location, null);
            maze.set(nextLoc, this);
            location = nextLoc;
            health--;
        }
    }

    public boolean isDead() {
        return health <= 0;
    }

    public void damage(int d) {
        health -= d;
    }

    public double getAlpha() {
        double a = (1 - (double) health / maxHealth) * 0.7;
        return a > 0.7? 0.7 : a < 0? 0: a;
    }

    public void turnLeft() {
        direction = direction == 0? DOWN: direction - 1;
    }

    public void turnRight() {
        direction = (direction + 1) % 4;
    }

    public static int turnLeft(int direction) {
        return direction == 0? DOWN: direction - 1;
    }

    public static int turnRight(int direction) {
        return (direction + 1) % 4;
    }

    public void draw(Graphics g) {
        g.setColor(new Color(255, 0, 255));
        int x = location.getX();
        int y = location.getY();
        g.fillOval(x * Wall.width + (int)(0.05 * Wall.width), y * Wall.height + (int)(0.05 * Wall.height), (int)(0.9 * Wall.width), (int)(0.9 * Wall.height));
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }
    
    public int getDirection() {
        return direction;
    }

    /**
     * @return the maxHealth
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    public int getHealth() { 
        return health;
    }

    /**
     * @return the vision
     */
    public int getVision() {
        return vision;
    }

    public int getMoves() {
        return moves;
    }

    /**
     * @param vision the vision to set
     */
    public void setVision(int vision) {
        this.vision = vision;
    }

}