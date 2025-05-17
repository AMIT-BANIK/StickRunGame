package mainGame;

import javax.swing.*;

public class Game extends JFrame {

    private JPanel GamePanel;

    public Game() {

        GamePanel = new GamePanel();

        setDefaultCloseOperation( EXIT_ON_CLOSE);
        setTitle( "Runner Game v_1.4");
        setLocation(500, 200);
        add( GamePanel);
        pack();
        setResizable(false);
        setVisible( true);
        //setResizable( false);

    }

    public static void main( String[] args) {

        new Game();
    }

}