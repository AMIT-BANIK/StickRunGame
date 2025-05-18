package mainGame;

import javax.swing.*;

public class Game extends JFrame {

    private JPanel GamePanel;

    public Game() {

        GamePanel = new GamePanel();

        setDefaultCloseOperation( EXIT_ON_CLOSE);
        setTitle( "Run to Overcome Obstacle");
        setLocation(500, 200);
        add( GamePanel);
        pack();
        setResizable(false);
        setVisible( true);

    }

    public static void main( String[] args) {

        new Game();
    }

}