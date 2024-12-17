package model;

import com.googlecode.lanterna.TextColor;
import java.util.ArrayList;
import java.util.List;

/**
 * The GameModel class represents the core game logic and state management
 * for our game. It handles the game board, piece movements,
 * scoring, bonus activation, and game state transitions.
 */
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
    private int lastScore;

    /**
     * Constructs a GameModel with the specified board, score, and piece factory.
     * 
     * @param board the game board
     * @param score the score object
     * @param pieceFactory the piece factory for generating game pieces
     */
    public GameModel(Board board, Score score, PieceFactory pieceFactory) {
        this.board = board;
        this.score = score;
        this.pieceFactory = pieceFactory;
        initGameState();
        board.addObserver(score);
    }

    /**
     * Default constructor initializing with default dependencies.
     */
    public GameModel() {
        this(new Board(10, 20), new Score(), new RandomPieceFactory());
    }

    /**
     * Initializes the game state to its starting conditions.
     */
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

    /**
     * Resets the game to its initial state, clearing the board and score.
     */
    public void resetGame() {
        board.resetBoard();
        this.score.reset();
        this.board.getObservers().clear();
        board.addObserver(score);
        initGameState();
    }

    /**
     * Spawns a new piece on the board, setting it as the current piece.
     */
    private void spawnNewPiece() {
        this.currentPiece = new PositionedPiece(nextPiece.getPiece(), INITIAL_PIECE_X, INITIAL_PIECE_Y);
        this.nextPiece = new PositionedPiece(pieceFactory.createPiece(), 0, 0);
    }

    /**
     * Moves the current piece left if possible.
     */
    public void moveLeft() {
        if (!paused && board.canPlaceShape(currentPiece.getPiece().getShape(), currentPiece.getX() - 1, currentPiece.getY())) {
            currentPiece.moveLeft();
        }
    }

    /**
     * Moves the current piece right if possible.
     */
    public void moveRight() {
        if (!paused && board.canPlaceShape(currentPiece.getPiece().getShape(), currentPiece.getX() + 1, currentPiece.getY())) {
            currentPiece.moveRight();
        }
    }

    /**
     * Moves the current piece down if possible, or locks it in place if not.
     */
    public void moveDown() {
        if (paused) return;
        if (board.canPlaceShape(currentPiece.getPiece().getShape(), currentPiece.getX(), currentPiece.getY() + 1)) {
            currentPiece.moveDown();
        } else {
            lockCurrentPiece();
        }
    }

    /**
     * Rotates the current piece to the left.
     */
    public void rotateLeft() {
        if (!paused) currentPiece.rotate(board, new RotateLeftStrategy());
    }

    /**
     * Rotates the current piece to the right.
     */
    public void rotateRight() {
        if (!paused) currentPiece.rotate(board, new RotateRightStrategy());
    }

    /**
     * Drops the current piece to the bottom of the board.
     */
    public void drop() {
        if (paused) return;
        while (board.canPlaceShape(currentPiece.getPiece().getShape(), currentPiece.getX(), currentPiece.getY() + 1))
            currentPiece.moveDown();
        lockCurrentPiece();
    }

    /**
     * Activates the bonus mode if sufficient charge is available.
     */
    public void activateBonus() {
        if (!paused && bonusCharge >= 500) {
            bonusActive = true;
        }
    }

    /**
     * Executes the bonus action, clearing specified rows and updating the score.
     * 
     * @param rowsToClear the list of row indices to clear
     */
    public void executeBonus(List<Integer> rowsToClear) {
        if (bonusActive) {
            List<Integer> sortedRows = new ArrayList<>(rowsToClear);
            sortedRows.sort((a, b) -> b - a);

            int linesCleared = 0;
            for (int clearedRow : sortedRows) {
                if (clearedRow >= 0 && clearedRow < board.getBoard().length) {
                    clearLine(clearedRow);
                    shiftLinesDown(clearedRow);
                    linesCleared++;
                } else {
                    System.err.println("Attempted to clear invalid row: " + clearedRow);
                }
            }

            if (linesCleared > 0) {
                score.onLineCleared(linesCleared);
            }

            bonusActive = false;
            bonusCharge = 0;
            lastScore = score.getPoints();
        }
    }

    /**
     * Clears a specified line on the board.
     * 
     * @param row the index of the row to clear
     */
    private void clearLine(int row) {
        for (int col = 0; col < board.getBoard()[0].length; col++) {
            board.getBoard()[row][col] = ' ';
            board.getColors()[row][col] = TextColor.ANSI.BLACK;
        }
    }

    /**
     * Shifts all lines above a specified row down by one.
     * 
     * @param startRow the index of the row to start shifting from
     */
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

    /**
     * Locks the current piece in place and spawns a new piece. Ends the game if
     * no space is available for the new piece.
     */
    public void lockCurrentPiece() {
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

    /**
     * Toggles the pause state of the game.
     */
    public void togglePause() {
        paused = !paused;
    }

    /**
     * Checks if the game is currently paused.
     * 
     * @return true if the game is paused; false otherwise
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * Gets the current fall speed of the pieces.
     * 
     * @return the fall speed
     */
    public int getFallSpeed() {
        return fallSpeed;
    }

    /**
     * Sets the fall speed of the pieces.
     * 
     * @param fallSpeed the new fall speed
     */
    public void setFallSpeed(int fallSpeed) {
        this.fallSpeed = fallSpeed;
    }

    /**
     * Gets the current bonus charge.
     * 
     * @return the bonus charge
     */
    public int getBonusCharge() {
        return bonusCharge;
    }

    /**
     * Sets the bonus charge.
     * 
     * @param bonusCharge the new bonus charge
     */
    public void setBonusCharge(int bonusCharge) {
        this.bonusCharge = bonusCharge;
    }

    /**
     * Checks if the bonus mode is active.
     * 
     * @return true if the bonus mode is active; false otherwise
     */
    public boolean isBonusActive() {
        return bonusActive;
    }

    /**
     * Checks if the game is currently running.
     * 
     * @return true if the game is running; false if it is over
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Gets the game board.
     * 
     * @return the board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Gets the current active piece.
     * 
     * @return the current piece
     */
    public PositionedPiece getCurrentPiece() {
        return currentPiece;
    }

    /**
     * Gets the next piece to be spawned.
     * 
     * @return the next piece
     */
    public PositionedPiece getNextPiece() {
        return nextPiece;
    }

    /**
     * Sets the current active piece.
     * 
     * @param currentPiece the piece to set as the current piece
     */
    public void setCurrentPiece(PositionedPiece currentPiece) {
        this.currentPiece = currentPiece;
    }

    /**
     * Sets the next piece to be spawned.
     * 
     * @param nextPiece the piece to set as the next piece
     */
    public void setNextPiece(PositionedPiece nextPiece) {
        this.nextPiece = nextPiece;
    }

    /**
     * Gets the current score points.
     * 
     * @return the score points
     */
    public int getScorePoints() {
        return score.getPoints();
    }

    /**
     * Gets the score from the last game state update.
     * 
     * @return the last recorded score
     */
    public int getLastScore() {
        return lastScore;
    }

    /**
     * Sets the last recorded score.
     * 
     * @param lastScore the score to set
     */
    public void setLastScore(int lastScore) {
        this.lastScore = lastScore;
    }

    /**
     * Updates the game state, moving the current piece down if the game is not paused.
     */
    public void update() {
        if (!paused) moveDown();
    }
}