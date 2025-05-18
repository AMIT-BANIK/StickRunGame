package shapes;

import java.awt.*;

public  abstract class Shape implements Locatable {

    // properties
    int x, y;

    abstract double getArea();
    //public abstract void draw(Graphics g);
    //public abstract Shape contains(int x, int y);
   // public abstract void draw(Graphics g);

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
