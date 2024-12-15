package model;

import com.googlecode.lanterna.TextColor;
import java.util.List;

public class GameModel {
    private static final int INITIAL_PIECE_X = 4;
    private static final int INITIAL_PIECE_Y = 0;

    private final Board board;
    private Score score;
    private final PieceFactory pieceFactory;
    private PositionedPiece currentPiece;
    private PositionedPiece nextPiece;
    private boolean running;
    private int fallSpeed;
    private int bonusCharge;
    private boolean bonusActive;
    private boolean paused;
    private int lastScore; // Track increments since last piece lock

    public GameModel() {
        this.board = new Board(10, 20);
        this.score = new Score();
        this.pieceFactory = new RandomPieceFactory();
        initGameState();
        board.addObserver(score);
    }

    private void initGameState() {
        this.running = true;
        this.fallSpeed = 500;
        this.bonusCharge = 0;
        this.bonusActive = false;
        this.paused = false;
        this.currentPiece = new PositionedPiece(pieceFactory.createPiece(), INITIAL_PIECE_X, INITIAL_PIECE_Y);
        this.nextPiece = new PositionedPiece(pieceFactory.createPiece(), 0, 0);
        this.lastScore = 0;
    }

    public void resetGame() {
        // Clear board
        clearBoard();
        // Reset score and re-add observer
        this.score = new Score();
        this.board.getObservers().clear();
        board.addObserver(score);
        // Reset all game state fields
        initGameState();
    }

    private void clearBoard() {
        char[][] b = board.getBoard();
        TextColor[][] c = board.getColors();
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[i].length; j++) {
                b[i][j] = ' ';
                c[i][j] = TextColor.ANSI.BLACK;
            }
        }
    }

    private void spawnNewPiece() {
        this.currentPiece = new PositionedPiece(nextPiece.getPiece(), INITIAL_PIECE_X, INITIAL_PIECE_Y);
        this.nextPiece = new PositionedPiece(pieceFactory.createPiece(), 0, 0);
    }

    public void moveLeft() {
        if (!paused && board.canPlaceShape(currentPiece.getPiece().getShape(), currentPiece.getX() - 1, currentPiece.getY()))
            currentPiece.moveLeft();
    }

    public void moveRight() {
        if (!paused && board.canPlaceShape(currentPiece.getPiece().getShape(), currentPiece.getX() + 1, currentPiece.getY()))
            currentPiece.moveRight();
    }

    public void moveDown() {
        if (paused) return;
        if (board.canPlaceShape(currentPiece.getPiece().getShape(), currentPiece.getX(), currentPiece.getY() + 1)) {
            currentPiece.moveDown();
        } else {
            lockCurrentPiece();
        }
    }

    public void rotateLeft() {
        if (!paused) currentPiece.rotate(board, new RotateLeftStrategy());
    }

    public void rotateRight() {
        if (!paused) currentPiece.rotate(board, new RotateRightStrategy());
    }

    public void drop() {
        if (paused) return;
        while (board.canPlaceShape(currentPiece.getPiece().getShape(), currentPiece.getX(), currentPiece.getY() + 1))
            currentPiece.moveDown();
        lockCurrentPiece();
    }

    public void activateBonus() {
        if (!paused && bonusCharge >= 500) {
            bonusActive = true;
        }
    }

    public void executeBonus(List<Integer> rowsToClear) {
        if (bonusActive) {
            rowsToClear.sort(Integer::compareTo);
            for (int clearedRow : rowsToClear) {
                clearLine(clearedRow);
                shiftLinesDown(clearedRow);
            }
            bonusActive = false;
            bonusCharge = 0;
            lastScore = score.getPoints();
        }
    }

    private void clearLine(int row) {
        for (int col = 0; col < board.getBoard()[0].length; col++) {
            board.getBoard()[row][col] = ' ';
            board.getColors()[row][col] = TextColor.ANSI.BLACK;
        }
    }

    private void shiftLinesDown(int startRow) {
        for (int r = startRow; r > 0; r--) {
            System.arraycopy(board.getBoard()[r-1], 0, board.getBoard()[r], 0, board.getBoard()[0].length);
            System.arraycopy(board.getColors()[r-1], 0, board.getColors()[r], 0, board.getBoard()[0].length);
        }
        for (int col = 0; col < board.getBoard()[0].length; col++) {
            board.getBoard()[0][col] = ' ';
            board.getColors()[0][col] = TextColor.ANSI.BLACK;
        }
    }

    private void lockCurrentPiece() {
        if (paused) return;
        board.addPositionedPiece(currentPiece);
        board.deleteFullLines();

        int currentScore = score.getPoints();
        int gainedScore = currentScore - lastScore;
        if (gainedScore < 0) gainedScore = 0;

        bonusCharge = Math.min(500, bonusCharge + (gainedScore / 2));

        lastScore = currentScore;

        if (currentScore > 0 && currentScore % 1000 == 0 && fallSpeed > 100) {
            fallSpeed -= 50;
        }

        spawnNewPiece();
        if (!board.canPlaceShape(currentPiece.getPiece().getShape(), currentPiece.getX(), currentPiece.getY())) {
            running = false;
        }
    }

    public void togglePause() {
        paused = !paused;
    }

    public boolean isPaused() {
        return paused;
    }

    public int getFallSpeed() {
        return fallSpeed;
    }

    public int getBonusCharge() {
        return bonusCharge;
    }

    public boolean isBonusActive() {
        return bonusActive;
    }

    public boolean isRunning() {
        return running;
    }

    public Board getBoard() {
        return board;
    }

    public PositionedPiece getCurrentPiece() {
        return currentPiece;
    }

    public PositionedPiece getNextPiece() {
        return nextPiece;
    }

    public int getScorePoints() {
        return score.getPoints();
    }

    public void update() {
        if (!paused) moveDown();
    }
}
