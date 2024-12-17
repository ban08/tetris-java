package model;

import com.googlecode.lanterna.TextColor;
import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a Tetris board.
 *
 * <p>The board is a 2D array of characters, where each character represents a cell in the board.
 * The board also has a corresponding 2D array of colors, where each color corresponds to the color
 * of the cell in the same position in the board.
 *
 * <p>The board is initialized with all cells set to the space character, and all colors set to black.
 * The board can be modified by adding positioned pieces to it, which will update the board and color
 * arrays accordingly.
 *
 * <p>The board also has methods for checking if a line is full, clearing a line, and shifting lines
 * down. These methods are used in the deleteFullLines method, which deletes all full lines and shifts
 * the remaining lines down.
 *
 * <p>The board also has a method for checking if the game is over, which is done by checking if the
 * next piece can be placed on the board.
 *
 * <p>The board also has a method for resetting the board, which sets all cells to the space character
 * and all colors to black.
 *
 *
 */

public class Board {
    private final char[][] board;
    private final TextColor[][] colors;
    private final int width;
    private final int height;
    private final List<BoardObserver> observers;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new char[height][width];
        this.colors = new TextColor[height][width];
        this.observers = new ArrayList<>();
        initializeBoard();
    }

    /**
     * Initializes the board and color arrays by setting all cells to the space
     * character and all colors to black.
     */
    private void initializeBoard() {
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                board[i][j] = ' ';
                colors[i][j] = TextColor.ANSI.BLACK;
            }
    }

    /**
     * Returns the 2D array of characters representing the game board.
     * Each character represents a cell on the board. The character can be one of:
     * <ul>
     * <li> ' ' (space) meaning the cell is empty
     * <li> 'X' meaning the cell is occupied by a piece
     * </ul>
     *
     * @return a 2D array of characters representing the game board
     */
    public char[][] getBoard() { return board; }
    /**
     * Returns the 2D array of TextColors representing the colors of the cells
     * on the board. The color of a cell is the color of the piece that occupies
     * the cell, or black if the cell is empty.
     *
     * @return a 2D array of TextColors representing the colors of the cells on the board
     */
    public TextColor[][] getColors() { return colors; }
    /**
     * Adds a BoardObserver to the list of observers that are notified when
     * lines are cleared or when the game is over.
     *
     * @param observer the observer to add
     * @throws NullPointerException if observer is null
     */
    public void addObserver(BoardObserver observer) {
        if (observer == null) {
            throw new NullPointerException("Adding null observer should throw NullPointerException.");
        }
        observers.add(observer);
    }
    /**
     * Returns a list of BoardObserver objects that are notified when
     * lines are cleared or when the game is over.
     *
     * @return a list of BoardObserver objects
     */
    public List<BoardObserver> getObservers() { return observers; }

    /**
     * Notifies all observers that a certain number of lines have been cleared.
     * This will call the onLineCleared method of each observer with the number
     * of lines cleared as argument.
     *
     * @param linesCleared the number of lines that have been cleared
     */
    private void notifyLineCleared(int linesCleared) {
        for (BoardObserver obs : observers) obs.onLineCleared(linesCleared);
    }

    /**
     * Notifies all observers that the game is over.
     * This will call the onGameOver method of each observer.
     */
    private void notifyGameOver() {
        for (BoardObserver obs : observers) obs.onGameOver();
    }

    /**
     * Returns true if the given shape can be placed at the given position.
     * The shape is placed at the given position (x, y) if and only if
     * the shape does not stick out of the board and does not overlap with
     * any existing pieces.
     *
     * @param shape the shape to place
     * @param x the x-coordinate of the position
     * @param y the y-coordinate of the position
     * @return true if the shape can be placed at the given position
     */
    public boolean canPlaceShape(char[][] shape, int x, int y) {
        if (x < 0 || y < 0 || x+shape[0].length > width || y+shape.length > height) return false;
        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col] != ' ' && board[y+row][x+col] != ' ') return false;
            }
        }
        return true;
    }

    /**
     * Adds a positioned piece to the board. The piece is placed at the
     * given position (x, y) on the board, and the board and color arrays
     * are updated accordingly.
     *
     * @param positionedPiece the piece to add
     */
    public void addPositionedPiece(PositionedPiece positionedPiece) {
        char[][] shape = positionedPiece.getPiece().getShape();
        TextColor color = positionedPiece.getPiece().getColor();
        int x = positionedPiece.getX();
        int y = positionedPiece.getY();

        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col] != ' ') {
                    board[y+row][x+col] = shape[row][col];
                    colors[y+row][x+col] = color;
                }
            }
        }
    }

    /**
     * Deletes all full lines on the board. A line is considered full
     * if all its cells are occupied by a piece.
     * After deleting all full lines, the remaining lines are shifted
     * down to fill the empty space created by the deleted lines.
     * If any lines were deleted, all observers are notified with the
     * number of lines that were deleted.
     */
    public void deleteFullLines() {
        int linesCleared = 0;

        for (int row = height - 1; row >= 0; ) {
            if (isFullLine(row)) {
                clearLine(row);
                shiftLinesDown(row);
                linesCleared++;
            } else {
                row--;
            }
        }

        if (linesCleared > 0) notifyLineCleared(linesCleared);
    }

    /**
     * Checks if the specified row on the board is fully occupied by pieces.
     *
     * @param row the index of the row to check
     * @return true if the row is fully occupied, false otherwise
     */
    private boolean isFullLine(int row) {
        for (int col = 0; col < width; col++)
            if (board[row][col] == ' ') return false;
        return true;
    }

    /**
     * Clears a line on the board by setting all its cells to empty space
     * and resetting their color to black.
     *
     * @param row the index of the row to clear
     */
    private void clearLine(int row) {
        for (int col = 0; col < width; col++) {
            board[row][col] = ' ';
            colors[row][col] = TextColor.ANSI.BLACK;
        }
    }

    /**
     * Shifts all lines of the board down from the given start row (inclusive),
     * by copying the content of each row to the row below it, and then clears
     * the top row.
     *
     * @param startRow the index of the row from which to start the shift
     */
    private void shiftLinesDown(int startRow) {
        for (int r = startRow; r > 0; r--) {
            System.arraycopy(board[r-1], 0, board[r], 0, width);
            System.arraycopy(colors[r-1], 0, colors[r], 0, width);
        }
        for (int col = 0; col < width; col++) {
            board[0][col] = ' ';
            colors[0][col] = TextColor.ANSI.BLACK;
        }
    }

    /**
     * Checks if the game is over by trying to place the given piece on the
     * board. If the piece cannot be placed, all observers are notified that
     * the game is over.
     *
     * @param piece the piece to try to place
     */
    public void checkGameOver(PositionedPiece piece) {
        if (!canPlaceShape(piece.getPiece().getShape(), piece.getX(), piece.getY())) notifyGameOver();
    }

    /**
     * Resets the board to its initial state by setting all cells to empty spaces
     * and all colors to black. This effectively clears any pieces or colors
     * from the board, preparing it for a new game or round.
     */
    public void resetBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = ' ';
                colors[i][j] = TextColor.ANSI.BLACK;
            }
        }
    }
}
