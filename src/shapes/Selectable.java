package shapes;

public interface Selectable {

    boolean getSelected();

    void setSelected( boolean selected);

    Shape contains( int x, int y);
}
