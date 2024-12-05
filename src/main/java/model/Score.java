package model;

/**
 * Keeps track of the score.
 */
public class Score implements BoardObserver {
    private int points;

    /**
     * Constructs a new model.Score.
     */
    public Score() {
        this.points = 0;
    }

    /**
     * @return the current score.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Notifies this score that a line has been cleared.
     * @param linesCleared the number of lines cleared.
     */
    @Override
    public void onLineCleared(int linesCleared) {
        points += linesCleared * 100;
    }

    /**
     * Notifies this score that the game is over.
     */
    @Override
    public void onGameOver() {
        System.out.println("controller.Game Over! Final model.Score: " + points);
    }
}