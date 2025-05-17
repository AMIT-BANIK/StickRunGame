package shapes;

public  abstract class Shape implements Locatable {

    // properties
    int x, y;

    abstract double getArea();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setLocation( int x, int y) {

        this.x = x;
        this.y = y;
    }
}
