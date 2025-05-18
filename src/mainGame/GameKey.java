package mainGame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;

public class GameKey extends KeyAdapter {

    private final GamePanel panel;

    public GameKey(GamePanel panel) {
        this.panel = panel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Timer jumpTimer = panel.getJumpTimer();
        Timer obstacleTimer = panel.getObstacleTimer();
        Timer runnerTimer = panel.getRunnerTimer();

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                if (!jumpTimer.isRunning()) {
                    jumpTimer.setDelay(3);
                    jumpTimer.start();
                }
                break;

            case KeyEvent.VK_DOWN:
                jumpTimer.setDelay(2);
                break;

            case KeyEvent.VK_RIGHT:
                if (!obstacleTimer.isRunning()) {
                    obstacleTimer.start();
                }
                if (!runnerTimer.isRunning()) {
                    runnerTimer.start();
                }

                if (panel.getObstacleSpeed() > 1) {
                    panel.setObstacleSpeed(panel.getObstacleSpeed() - 1);
                    obstacleTimer.setDelay(panel.getObstacleSpeed());
                }
                break;

            case KeyEvent.VK_LEFT:
                if (panel.getObstacleSpeed() < 5) {
                    panel.setObstacleSpeed(panel.getObstacleSpeed() + 1);
                    obstacleTimer.setDelay(panel.getObstacleSpeed());
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (panel.getRunnerTimer().isRunning()) {
                panel.getRunnerTimer().stop();
            }
            if (panel.getObstacleTimer().isRunning()) {
                panel.getObstacleTimer().stop();
            }
        }
    }
}
