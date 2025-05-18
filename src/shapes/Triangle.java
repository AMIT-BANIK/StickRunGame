package shapes;

import java.awt.Color;
import java.awt.Graphics2D;

public class Triangle extends Square {

    private Color color;

    public Triangle(int side) {
        super(side);
        // generate random color
        int R = (int) (Math.random() * 256);
        int G = (int) (Math.random() * 256);
        int B = (int) (Math.random() * 256);
        color = new Color(R, G, B);
    }

    public Color getColor() {
        return color;
    }

    //@Override
    public void draw(Graphics2D g) {
        int x = getX();
        int y = getY();

        int[] xPoints = { x, x + side / 2, x + side };
        int[] yPoints = { y + side, y, y + side };

        g.setColor(getColor());
        g.fillPolygon(xPoints, yPoints, 3);
    }
}
