package controller;

import model.GameModel;

public class GameController implements ControllerInterface {
    private final GameModel model;

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
    public void pause() { model.togglePause(); } // Changed here from model.pause() to model.togglePause()

    public void updateGame() { model.update(); }
    public boolean isGameRunning() { return model.isRunning(); }
}
