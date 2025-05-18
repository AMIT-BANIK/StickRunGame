package mainGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import shapes.Collectible;
import shapes.Rectangle;
import shapes.ShapeContainer;
import shapes.Square;

public class GamePanel extends JPanel {

    // constants
    public static final int BASEY = 250;
    public static final int MINGAP = 170;
    public static final int MAXGAP = 340;

    // properties
    private Image backgroundImage;
    private ShapeContainer obstacles;
    private ShapeContainer collectibles;
    private Timer obstacleTimer, runnerTimer, jumpTimer;
    private static int randomGap = (int) ( Math.random() * (MAXGAP - MINGAP)) + MINGAP;
    private ArrayList<Image> runnerGif;
    private int index;
    private int heightOfJump;
    private boolean flag;
    private static int jumpCount = 0;
    private JLabel scoreLabel;
    private int score;

    private Rectangle border;
    private boolean isGameOver;
    private int obstacleSpeed;
    private Timer obstacleTimer1;
    private JLabel highScoreLabel;
    private int highScore;

    private void clearAllTimers() {
        if (obstacleTimer != null) {
            for (ActionListener l : obstacleTimer.getActionListeners()) {
                obstacleTimer.removeActionListener(l);
            }
        }

        if (obstacleTimer1 != null) {
            for (ActionListener l : obstacleTimer1.getActionListeners()) {
                obstacleTimer1.removeActionListener(l);
            }
        }

        if (jumpTimer != null) {
            for (ActionListener l : jumpTimer.getActionListeners()) {
                jumpTimer.removeActionListener(l);
            }
        }

        if (runnerTimer != null) {
            for (ActionListener l : runnerTimer.getActionListeners()) {
                runnerTimer.removeActionListener(l);
            }
        }
    }

    // constructors
    public GamePanel() {

        setPreferredSize( new Dimension( 1000, 300));
        setFocusable(true);
        setLayout(null);

        scoreLabel = new JLabel();
        highScoreLabel = new JLabel();

        scoreLabel.setBounds(20, 20, 200, 20);
        highScoreLabel.setBounds(20, 40, 200, 20);

        add(scoreLabel);
        add(highScoreLabel);

        init();

        this.addMouseListener( new JumpMouseListener());
        this.addKeyListener( new JumpKeyListener());
    }

    private void init() {
        if (obstacleTimer != null) obstacleTimer.stop();
        if (obstacleTimer1 != null) obstacleTimer1.stop();
        if (jumpTimer != null) jumpTimer.stop();
        if (runnerTimer != null) runnerTimer.stop();

        clearAllTimers();

        score = 0;
        scoreLabel.setText("Score: " + score);
        highScore = HighScore.loadHighScore();
        highScoreLabel.setText("High Score: " + highScore);

        obstacleSpeed = 6;
        index = 0;
        heightOfJump = 0;
        flag = false;
        jumpCount = 0;
        isGameOver = false;

        border = new Rectangle(35, 60);
        border.setLocation(50, BASEY - 80 - heightOfJump);

        obstacles = new ShapeContainer();
        obstacles.add(new Obstacle(780, BASEY - 20));

        collectibles = new ShapeContainer();

        backgroundImage = new ImageIcon(this.getClass().getResource("/images/bc.png")).getImage();

        runnerGif = new ArrayList<>();
        for (int i = 11; i < 17; i++) {
            runnerGif.add((new ImageIcon(this.getClass().getResource("/images/tmp-" + i + ".gif")).getImage()));
        }

        obstacleTimer = new Timer(obstacleSpeed, new TimerActionListener());
        obstacleTimer1 = new Timer(obstacleSpeed, new TimerActionListener());
        runnerTimer = new Timer(32, new RunnerActionListener());
        jumpTimer = new Timer(3, new JumpActionListener());

        obstacleTimer.start();
        obstacleTimer1.start();
        runnerTimer.start();
    }

    public void paintComponent( Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = ( Graphics2D) g;

        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        g2.drawImage( runnerGif.get( index), 30, BASEY - 80 - heightOfJump, 80, 80, this);

        g2.fillRect(0, BASEY, getWidth(), 8);
        border.setLocation(50, BASEY - 80 - heightOfJump);

        Iterator i = obstacles.iterator();
        while( i.hasNext()) {
            ( (Obstacle) i.next() ).draw(g2);
        }

        // Draw collectibles
        Iterator j = collectibles.iterator();
        while (j.hasNext()) {
            ((Collectible) j.next()).draw(g);
        }

    }

    class TimerActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < obstacles.size(); i++) {
                Obstacle obstacle = (Obstacle) obstacles.getShape(i);
                obstacle.setLocation(obstacle.getX() - 1, obstacle.getY());

                if (obstacle.getX() == -10) {
                    obstacle.setSelected(true);
                }

                if (!obstacle.isScored() && obstacle.getX() + obstacle.getSide() < border.getX()) {
                    score++;
                    obstacle.setScored(true);
                    scoreLabel.setText("Score: " + score);
                }
            }

            if (obstacles.size() > 0) {
                Obstacle lastObstacle = (Obstacle) obstacles.getShape(obstacles.size() - 1);
                if (800 - lastObstacle.getX() == randomGap) {
                    obstacles.add(new Obstacle(780, BASEY - 20));
                    randomGap = (int) (Math.random() * (MAXGAP - MINGAP)) + MINGAP;
                }
            } else {

                obstacles.add(new Obstacle(780, BASEY - 20));
                randomGap = (int) (Math.random() * (MAXGAP - MINGAP)) + MINGAP;
            }

            obstacles.remove();

           //collectable generateing time logic
            if (collectibles.size() == 0 && Math.random() < 0.01) {
                int xPos = 800;
                int yPos;
                if (Math.random() < 0.5) {
                    yPos = BASEY - 100 - (int)(Math.random() * 60);
                } else {
                    yPos = BASEY - 20 - (int)(Math.random() * 10);
                }

                collectibles.add(new Collectible(xPos, yPos));
            }

            // Move collectibles
            for (int i = 0; i < collectibles.size(); i++) {
                Collectible collectible = (Collectible) collectibles.getShape(i);
                collectible.setLocation(collectible.getX() - 1, collectible.getY());

                if (collectible.getX() < -collectible.getSize()) {
                    collectibles.remove();  // remove selected collectibles, so mark selected first
                }

                if (!collectible.getSelected() && border.contains(collectible.getX() + collectible.getSize()/2, collectible.getY() + collectible.getSize()/2) != null) {
                    collectible.setSelected(true);
                    score += 5;  // increase score by 5 on collection
                    scoreLabel.setText("Score: " + score);
                }
            }

            collectibles.remove();

            if (obstacles.size() > 0 && !isGameOver) {
                Obstacle firstObstacle = (Obstacle) obstacles.getShape(0);

                for (int i = 0; i < 20; i++) {
                    if (border.contains(firstObstacle.getX() + i, firstObstacle.getY()) != null) {
                        isGameOver = true;
                        break;
                    }
                }
            }

            if (isGameOver) {
                runnerTimer.stop();
                for (ActionListener l : runnerTimer.getActionListeners()) {
                    runnerTimer.removeActionListener(l);
                }

                obstacleTimer.stop();
                for (ActionListener l : obstacleTimer.getActionListeners()) {
                    obstacleTimer.removeActionListener(l);
                }

                jumpTimer.stop();
                for (ActionListener l : jumpTimer.getActionListeners()) {
                    jumpTimer.removeActionListener(l);
                }

                if (score > highScore) {
                    HighScore.saveHighScore(score);
                    highScore = score;
                    highScoreLabel.setText("High Score: " + highScore);
                }

                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        scoreLabel.getText() + "\nPlay again?",
                        "Game Over!",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    init();
                } else {
                    System.exit(0);
                }
            }

            repaint();
        }
    }

    class RunnerActionListener implements ActionListener {
        public void actionPerformed( ActionEvent e) {

            if (index >= runnerGif.size() - 1)
                index = 0;
            else
                index++;

        }
    }

    class JumpMouseListener extends MouseAdapter {
        public void mouseClicked( MouseEvent e) {

            jumpTimer.setDelay(3);
            if (!jumpTimer.isRunning()) {
                jumpTimer.start();
            }
        }

    }

    class JumpActionListener implements ActionListener {

        public void actionPerformed( ActionEvent e) {

            if ( jumpCount == 65) {
                flag = true;
            }
            if ( flag) {
                jumpCount--;
                if ( jumpCount == 0) {
                    jumpTimer.stop();
                    flag = false;
                }
            }
            else {
                jumpCount++;
            }
            heightOfJump = 1 * jumpCount;
        }
    }

    class JumpKeyListener extends KeyAdapter {
        public void keyPressed( KeyEvent e){
            if ( e.getExtendedKeyCode() == e.VK_UP) {
                jumpTimer.setDelay(3);
                if (!jumpTimer.isRunning()) {
                    jumpTimer.start();
                }
            }
            else if ( e.getExtendedKeyCode() == e.VK_DOWN) {
                //	jumpTimer.stop();
                jumpTimer.setDelay(2);

            }
            else if ( e.getExtendedKeyCode() == e.VK_RIGHT) {
                if ( obstacleSpeed > 1) {
                    obstacleSpeed--;
                    obstacleTimer.setDelay( obstacleSpeed);
                }

            }
            else if ( e.getExtendedKeyCode() == e.VK_LEFT) {
                if ( obstacleSpeed < 5) {
                    obstacleSpeed++;
                    obstacleTimer.setDelay( obstacleSpeed);
                }
            }
        }
    }
}