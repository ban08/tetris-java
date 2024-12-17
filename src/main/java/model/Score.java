package model;
/**
 * A class representing a score in the game.
 *
 * <p>The score is initially zero and is incremented by 100 points for each
 * line cleared. The score is reset to zero when the game is reset.
 *
 * <p>This class also implements the BoardObserver interface, which means it
 * receives notifications when lines are cleared or when the game is over.
 *
 *
 */
public class Score implements BoardObserver {
    private int points;
    /**
     * Creates a new Score object with an initial score of zero.
     */
    public Score() { this.points = 0; }

    /**
     * Returns the current score.
     *
     * @return the current score
     */
    public int getPoints() { return points; }
    /**
     * Notification that one or more lines have been cleared.
     *
     * @param linesCleared the number of lines cleared
     */
    @Override
    public void onLineCleared(int linesCleared) {
        points += linesCleared * 100;
    }

    /**
     * Resets the score to zero.
     */
    public void reset() {
        this.points = 0;
    }
    /**
     * Notification that the game is over.
     */
    @Override
    public void onGameOver() {
        System.out.println("Game Over! Final Score: " + points);
    }
}