/**
 * The GameController class is the main controller for the Tetris game. It
 * delegates user input to the underlying GameModel and provides methods for
 * updating the game state and querying the game state.
 *
 *
 */
package controller;

import model.GameModel;

public class GameController implements ControllerInterface {
    private final GameModel model;

    /**
     * Constructs a new GameController with the given GameModel.
     *
     * @param model The GameModel to control.
     */
    public GameController(GameModel model) {
        this.model = model;
    }

    @Override
    public void moveLeft() { model.moveLeft(); }

    @Override
    public void moveRight() { model.moveRight(); }

    @Override
    public void moveDown() { model.moveDown(); }

    @Override
    public void rotateLeft() { model.rotateLeft(); }

    @Override
    public void rotateRight() { model.rotateRight(); }

    @Override
    public void drop() { model.drop(); }

    @Override
    public void pause() { model.togglePause(); }

    /**
     * Updates the game state by calling the update() method on the underlying
     * GameModel.
     */
    public void updateGame() { model.update(); }

    /**
     * Queries the game state and returns true if the game is running, false
     * otherwise.
     *
     * @return True if the game is running, false otherwise.
     */
    public boolean isGameRunning() { return model.isRunning(); }
}