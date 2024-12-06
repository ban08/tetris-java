package model;

public class GameModel {
    private final Board board;
    private final Score score;
    private final PieceFactory pieceFactory;
    private PositionedPiece currentPiece;
    private boolean running;

    public GameModel() {
        this.board = new Board(10, 20);
        this.score = new Score();
        this.pieceFactory = new RandomPieceFactory();
        this.running = true;
        this.currentPiece = generateNewPiece();
        board.addObserver(score);
    }

    private PositionedPiece generateNewPiece() {
        return new PositionedPiece(pieceFactory.createPiece(), 4, 0);    }

    public void moveLeft() {
        if (board.canPlaceShape(currentPiece.getPiece().getShape(), currentPiece.getX() - 1, currentPiece.getY())) {
            currentPiece.moveLeft();
        }
    }

    public void moveRight() {
        if (board.canPlaceShape(currentPiece.getPiece().getShape(), currentPiece.getX() + 1, currentPiece.getY())) {
            currentPiece.moveRight();
        }
    }

    public void moveDown() {
        if (board.canPlaceShape(currentPiece.getPiece().getShape(), currentPiece.getX(), currentPiece.getY() + 1)) {
            currentPiece.moveDown();
        } else {
            lockCurrentPiece();
        }
    }

    public void rotateLeft() {
        currentPiece.rotate(board, new RotateLeftStrategy());
    }

    public void rotateRight() {
        currentPiece.rotate(board, new RotateRightStrategy());
    }

    public void drop() {
        while (board.canPlaceShape(currentPiece.getPiece().getShape(), currentPiece.getX(), currentPiece.getY() + 1)) {
            currentPiece.moveDown();
        }
        lockCurrentPiece();
    }

    public void pause() {
        // Implement pause logic if needed
    }

    private void lockCurrentPiece() {
        board.addPositionedPiece(currentPiece);
        board.deleteFullLines();
        currentPiece = generateNewPiece();
        if (!board.canPlaceShape(currentPiece.getPiece().getShape(), currentPiece.getX(), currentPiece.getY())) {
            running = false;
        }
    }

    /**
     * Update the game state (e.g., gravity).
     */
    public void update() {
        moveDown();
    }

    public Board getBoard() {
        return board;
    }

    public PositionedPiece getCurrentPiece() {
        return currentPiece;
    }

    public int getScorePoints() {
        return score.getPoints();
    }

    public boolean isRunning() {
        return running;
    }
}
