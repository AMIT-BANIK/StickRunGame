package shapes;

import java.awt.Color;
import java.awt.Graphics;

public class Coins extends Shape implements Selectable {

    private int size = 20;  // size of the icon (width & height)
    private boolean selected = false;  // whether collected or not
    private Color color;

    public Coins(int x, int y) {
        this.x = x;
        this.y = y;
        this.color = Color.YELLOW;  // or use random color
    }

    public void draw(Graphics g) {
        g.setColor(color);
        // draw a simple circle or star
        g.fillOval(x, y, size, size);
    }

    @Override
    public double getArea() {
        return size * size;  // approximate
    }

    @Override
    public Shape contains(int px, int py) {
        if (px >= x && px <= x + size && py >= y && py <= y + size) {
            return this;
        }
        return null;
    }

    @Override
    public boolean getSelected() {
        return selected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getSize() {
        return size;
    }
}
