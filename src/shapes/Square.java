package shapes;

public class Square extends Rectangle {

    // properties
    protected int side;

    // constructors
    public Square( int side) {

        super(side, side);
        this.side = side;
    }
    public int getSide() {
        return side;
    }
}
