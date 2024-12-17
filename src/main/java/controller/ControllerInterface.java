package controller;
/**
 * Interface for a controller that can be used to control a game of Tetris.
 *
 * A controller should be able to control the game by moving the current piece
 * left, right, or down, rotating the current piece left or right, dropping the
 * current piece, and pausing the game.
 */
public interface ControllerInterface {
    /**
     * Moves the current piece left.
     */
    void moveLeft();

    /**
     * Moves the current piece right.
     */
    void moveRight();

    /**
     * Moves the current piece down.
     */
    void moveDown();

    /**
     * Rotates the current piece left.
     */
    void rotateLeft();

    /**
     * Rotates the current piece right.
     */
    void rotateRight();

    /**
     * Drops the current piece.
     */
    void drop();

    /**
     * Pauses the game.
     */
    void pause();
}