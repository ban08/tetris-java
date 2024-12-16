package model;

import com.googlecode.lanterna.TextColor;
import java.util.ArrayList;
import java.util.List;

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

    private void initializeBoard() {
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                board[i][j] = ' ';
                colors[i][j] = TextColor.ANSI.BLACK;
            }
    }

    public char[][] getBoard() { return board; }
    public TextColor[][] getColors() { return colors; }
    public void addObserver(BoardObserver observer) {
        if (observer == null) {
            throw new NullPointerException("Adding null observer should throw NullPointerException.");
        }
        observers.add(observer);
    }
    public List<BoardObserver> getObservers() { return observers; }

    private void notifyLineCleared(int linesCleared) {
        for (BoardObserver obs : observers) obs.onLineCleared(linesCleared);
    }

    private void notifyGameOver() {
        for (BoardObserver obs : observers) obs.onGameOver();
    }

    public boolean canPlaceShape(char[][] shape, int x, int y) {
        if (x < 0 || y < 0 || x+shape[0].length > width || y+shape.length > height) return false;
        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col] != ' ' && board[y+row][x+col] != ' ') return false;
            }
        }
        return true;
    }

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

    public void deleteFullLines() {
        int linesCleared = 0;
        for (int row = 0; row < height; row++) {
            if (isFullLine(row)) {
                clearLine(row);
                shiftLinesDown(row);
                linesCleared++;
            }
        }
        if (linesCleared > 0) notifyLineCleared(linesCleared);
    }

    private boolean isFullLine(int row) {
        for (int col = 0; col < width; col++)
            if (board[row][col] == ' ') return false;
        return true;
    }

    private void clearLine(int row) {
        for (int col = 0; col < width; col++) {
            board[row][col] = ' ';
            colors[row][col] = TextColor.ANSI.BLACK;
        }
    }

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


    public void checkGameOver(PositionedPiece piece) {
        if (!canPlaceShape(piece.getPiece().getShape(), piece.getX(), piece.getY())) notifyGameOver();
    }

    public void resetBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = ' ';
                colors[i][j] = TextColor.ANSI.BLACK;
            }
        }
    }
}
