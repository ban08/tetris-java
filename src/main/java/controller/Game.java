package controller;
import model.Board;
import model.PositionedPiece;
import model.PieceSelector;
import model.Score;
import model.RotateLeftStrategy;
import model.RotateRightStrategy;
import view.GameView;

public class Game implements ControllerInterface {
    private final Board board;
    private final Score score;
    private final PieceSelector pieceSelector;
    private PositionedPiece currentPiece;
    private boolean running;

    public Game() {
        this.board = new Board(10, 20);
        this.score = new Score();
        this.pieceSelector = new PieceSelector();
        this.running = true;
        this.currentPiece = generateNewPiece();
        board.addObserver(score);
    }

    private PositionedPiece generateNewPiece() {
        return new PositionedPiece(pieceSelector.randomPiece(), 4, 0);
    }

    // controller.ControllerInterface methods
    @Override
    public void moveLeft() {
        if (board.canPlaceShape(currentPiece.getPiece().getShape(), currentPiece.getX() - 1, currentPiece.getY())) {
            currentPiece.moveLeft();
        }
    }

    @Override
    public void moveRight() {
        if (board.canPlaceShape(currentPiece.getPiece().getShape(), currentPiece.getX() + 1, currentPiece.getY())) {
            currentPiece.moveRight();
        }
    }

    @Override
    public void moveDown() {
        if (board.canPlaceShape(currentPiece.getPiece().getShape(), currentPiece.getX(), currentPiece.getY() + 1)) {
            currentPiece.moveDown();
        } else {
            lockCurrentPiece();
        }
    }

    @Override
    public void rotateLeft() {
        currentPiece.rotate(board, new RotateLeftStrategy());
    }

    @Override
    public void rotateRight() {
        currentPiece.rotate(board, new RotateRightStrategy());
    }

    @Override
    public void drop() {
        while (board.canPlaceShape(currentPiece.getPiece().getShape(), currentPiece.getX(), currentPiece.getY() + 1)) {
            currentPiece.moveDown();
        }
        lockCurrentPiece();
    }

    @Override
    public void pause() {
        // Implement pause logic if necessary
    }

    private void lockCurrentPiece() {
        board.addPositionedPiece(currentPiece);
        board.deleteFullLines();
        currentPiece = generateNewPiece();
        if (!board.canPlaceShape(currentPiece.getPiece().getShape(), currentPiece.getX(), currentPiece.getY())) {
            running = false;
        }
    }

    public void update() {
        moveDown(); // Automatically move the piece down
    }

    // Getters for the View
    public Board getBoard() {
        return board;
    }

    public PositionedPiece getCurrentPiece() {
        return currentPiece;
    }

    public int getScore() {
        return score.getPoints();
    }

    public boolean isRunning() {
        return running;
    }
}
