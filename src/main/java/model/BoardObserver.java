package model;

/**
 * Interface for observers of the Board model.
 * 
 * Observers of the Board model should implement this interface to
 * receive notifications of changes to the board state.
 * 
 *
 */
public interface BoardObserver {

    /**
     * Notification that one or more lines have been cleared.
     * 
     * @param linesCleared the number of lines cleared
     */
    void onLineCleared(int linesCleared);

    /**
     * Notification that the game is over.
     */
    void onGameOver();
}