public class Location {

    private int x;
    private int y;

    /**
     * @param x the x position
     * @param y the y position
     */
    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return the x position
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y position
     */
    public int getY() {
        return y;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Checks if two locations are equal
     * @param loc the location to compare to
     */
    public boolean equals(Location loc) {
        return x == loc.getX() && y == loc.getY();
    }

}