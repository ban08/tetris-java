package model;

public class Score implements BoardObserver {
    private int points;
    public Score() { this.points = 0; }

    public int getPoints() { return points; }

    @Override
    public void onLineCleared(int linesCleared) {
        points += linesCleared * 100;
    }

    @Override
    public void onGameOver() {
        System.out.println("Game Over! Final Score: " + points);
    }
}
