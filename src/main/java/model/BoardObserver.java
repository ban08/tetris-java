package model;

public interface BoardObserver {
    /**
     * This method is called by the board when a line (or lines) are cleared.
     * @param linesCleared the number of lines cleared
     */
    void onLineCleared(int linesCleared);
    /**
     * This method is called by the board when the game is over.
     */
    void onGameOver();
}
