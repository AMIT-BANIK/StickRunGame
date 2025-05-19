package mainGame;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import shapes.*;

public class GamePanel extends JPanel {

    // constants
    public static final int BASEY = 250;
    public static final int MINGAP = 170;
    public static final int MAXGAP = 340;

    // properties
    private Image backgroundImage;
    private ShapeContainer obstacles;
    private ShapeContainer Coins;
    private Timer obstacleTimer, playerTimer, jumpTimer;
    private static int randomGap = (int) ( Math.random() * (MAXGAP - MINGAP)) + MINGAP;
    private ArrayList<Image> playerGif;
    private int index;
    private int heightOfJump;
    private boolean flag;
    private static int jumpCount = 0;
    private JLabel scoreLabel;
    private int score;
    private Rectangle player;
    private boolean isGameOver;
    private int obstacleSpeed;
    private Timer obstacleTimer1;
    private JLabel highScoreLabel;
    private int highScore;
    private boolean isRightArrowPressed ;

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

        this.addKeyListener(new GameKey(this));

    }
    public Timer getJumpTimer() {
        return jumpTimer;
    }

    public Timer getObstacleTimer() {
        return obstacleTimer;
    }

    public Timer getplayerTimer() {
        return playerTimer;
    }

    public int getObstacleSpeed() {
        return obstacleSpeed;
    }

    public void setObstacleSpeed(int speed) {
        this.obstacleSpeed = speed;
    }


    private void init() {
        if (obstacleTimer != null) obstacleTimer.stop();
        if (obstacleTimer1 != null) obstacleTimer1.stop();
        if (jumpTimer != null) jumpTimer.stop();
        if (playerTimer != null) playerTimer.stop();
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

        player = new Rectangle(35, 60);
        player.setLocation(50, BASEY - 80 - heightOfJump);

        obstacles = new ShapeContainer();
        obstacles.add(new Obstacle(780, BASEY - 20));

        Coins = new ShapeContainer();

        backgroundImage = new ImageIcon(this.getClass().getResource("/images/bc.png")).getImage();

        playerGif = new ArrayList<>();
        for (int i = 11; i < 17; i++) {
            playerGif.add((new ImageIcon(this.getClass().getResource("/images/tmp-" + i + ".gif")).getImage()));
        }

        playerTimer = new Timer(100, new RunnerActionListener());
        obstacleTimer = new Timer(obstacleSpeed, new TimerActionListener());
        jumpTimer = new Timer(3, new JumpActionListener());

        setFocusable(true);
        requestFocusInWindow();
        revalidate();
        repaint();
    }

    public void paintComponent( Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = ( Graphics2D) g;

        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        g2.drawImage( playerGif.get( index), 30, BASEY - 80 - heightOfJump, 80, 80, this);

        g2.fillRect(0, BASEY, getWidth(), 8);
        player.setLocation(50, BASEY - 80 - heightOfJump);

        //Draw obstacle
        for (Object shape : obstacles) {
            ((Obstacle) shape).draw(g2);
        }

        // Draw Coins
        for (Object shape : Coins) {
            ((Coins) shape).draw(g);
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

                if (!obstacle.isScored() && obstacle.getX() + obstacle.getSide() < player.getX()) {
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
            if (Coins.size() == 0 && Math.random() < 0.01) {
                int xPos = 800;
                int yPos = BASEY - 80 - (int)(Math.random() * 30);
                Coins.add(new Coins(xPos, yPos));
            }
            // Move collectibles
            for (int i = 0; i < Coins.size(); i++) {
                Coins collectible = (Coins) Coins.getShape(i);
                collectible.setLocation(collectible.getX() - 1, collectible.getY());

                if (collectible.getX() < -collectible.getSize()) {
                    Coins.remove();
                }
                if (!collectible.getSelected() && player.contains(collectible.getX() + collectible.getSize()/2, collectible.getY() + collectible.getSize()/2) != null) {
                    collectible.setSelected(true);
                    score += 5;  // increase score by 5 on collection
                    scoreLabel.setText("Score: " + score);
                }
            }

            Coins.remove();

            if (!isGameOver && obstacles.size() > 0) {
                Obstacle firstObstacle = (Obstacle) obstacles.getShape(0);

                for (int i = 0; i < 20; i++) {
                    if (player.contains(firstObstacle.getX() + i, firstObstacle.getY()) != null) {
                        System.out.println("Collision detected!");
                        isGameOver = true;
                        break;
                    }
                }
            }

            if (isGameOver) {
                playerTimer.stop();
                for (ActionListener l : playerTimer.getActionListeners()) {
                    playerTimer.removeActionListener(l);
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
                    //HighScore.saveHighScore(score);
                    highScore = score;
                    highScoreLabel.setText("High Score: " + highScore);
                }

                int confirm = JOptionPane.showConfirmDialog(
                        GamePanel.this,
                        scoreLabel.getText() + "\nPlay again?",
                        "Game Over!",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    SwingUtilities.invokeLater(() -> {
                        init();
                        setFocusable(true);
                        requestFocusInWindow();
                        revalidate();
                        repaint();
                    });
                } else {
                    System.exit(0);
                }
            }
            repaint();
        }
    }

    class RunnerActionListener implements ActionListener {
        public void actionPerformed( ActionEvent e) {

            if (index >= playerGif.size() - 1)
                index = 0;
            else
                index++;
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
            heightOfJump = 2 * jumpCount;
        }
    }
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

        if (playerTimer != null) {
            for (ActionListener l : playerTimer.getActionListeners()) {
                playerTimer.removeActionListener(l);
            }
        }
    }
}