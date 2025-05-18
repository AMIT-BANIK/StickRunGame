package mainGame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import shapes.Drawable;
import shapes.Square;
import shapes.Triangle;

public class Obstacle extends Square implements Drawable{

    private int R, G, B;
    private boolean scored;
    private boolean isTriangle;

    public Obstacle( int x, int y) {

        super( 20);
        setLocation(x, y);
        scored = false;

        isTriangle = new Random().nextBoolean();

        R = (int) (Math.random() * 256);
        G = (int) (Math.random() * 256);
        B = (int) (Math.random() * 256);

    }

    @Override
    public void draw(Graphics2D g) {

        g.setColor( new Color( R, G, B));
        if (isTriangle) {
            int[] xPoints = {getX(), getX() + side / 2, getX() + side};
            int[] yPoints = {getY() + side, getY(), getY() + side};
            g.fillPolygon(xPoints, yPoints, 3);
        } else {
            g.fillRect(getX(), getY(), side, side);
        }
    }
    public boolean isScored() {
        return scored;
    }

    public void setScored(boolean scored) {
        this.scored = scored;
    }
}