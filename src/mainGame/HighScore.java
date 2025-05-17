package mainGame;

import java.io.*;

public class HighScore {

    private static final String FILE_NAME = "highscore.dat";

    public static int loadHighScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            return Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            return 0;
        }
    }

    public static void saveHighScore(int score) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write(String.valueOf(score));
        } catch (IOException e) {
            System.err.println("Error saving high score: " + e.getMessage());
        }
    }
}