package model;
import com.googlecode.lanterna.TextColor;

    import java.util.ArrayList;
    import java.util.List;

    /**
     * A tetris board.
     */
    public class Board {
        private final char[][] board;
        private final TextColor[][] colors;
        private final int width;
        private final int height;
        private final List<BoardObserver> observers;

        /**
         * Create a new board with the given width and height.
         *
         * @param width the width of the board
         * @param height the height of the board
         */
        public Board(int width, int height) {
            this.width = width;
            this.height = height;
            this.board = new char[height][width];
            this.colors = new TextColor[height][width];
            this.observers = new ArrayList<>();
            initializeBoard();
        }

        /**
         * Initialize the board by setting all blocks to ' ' and colors to {@link TextColor.ANSI.BLACK}.
         */
        private void initializeBoard() {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    board[i][j] = ' ';
                    colors[i][j] = TextColor.ANSI.BLACK;
                }
            }
        }

        /**
         * Return the current state of the board.
         *
         * @return the current state of the board
         */
        public char[][] getBoard() {
            return board;
        }

        /**
         * Return the current state of the board's colors.
         *
         * @return the current state of the board's colors
         */
        public TextColor[][] getColors() {
            return colors;
        }

        /**
         * Add a new observer to the board.
         *
         * @param observer the observer to add
         */
        public void addObserver(BoardObserver observer) {
            observers.add(observer);
        }

        /**
         * Get all observers of the board.
         *
         * @return a list of all observers
         */
        public List<BoardObserver> getObservers() {
            return observers;
        }

        /**
         * Notify all observers that a line has been cleared.
         *
         * @param linesCleared the number of lines cleared
         */
        private void notifyLineCleared(int linesCleared) {
            for (BoardObserver observer : observers) {
                observer.onLineCleared(linesCleared);
            }
        }

        /**
         * Notify all observers that the game is over.
         */
        private void notifyGameOver() {
            for (BoardObserver observer : observers) {
                observer.onGameOver();
            }
        }

        /**
         * Check if a shape can be placed at the given position.
         *
         * @param shape the shape to place
         * @param x the x-coordinate of the position
         * @param y the y-coordinate of the position
         * @return true if the shape can be placed, false otherwise
         */
        public boolean canPlaceShape(char[][] shape, int x, int y) {
            // Check if the shape is within the bounds of the board
            if (x < 0 || y < 0 || x + shape[0].length > width || y + shape.length > height) {
                return false;
            }

            // Check for overlaps with existing blocks
            for (int row = 0; row < shape.length; row++) {
                for (int col = 0; col < shape[row].length; col++) {
                    if (shape[row][col] != ' ' && board[y + row][x + col] != ' ') {
                        return false;
                    }
                }
            }

            return true; // Valid placement
        }

        /**
         * Add a positioned piece to the board.
         *
         * @param positionedPiece the positioned piece to add
         */
        public void addPositionedPiece(PositionedPiece positionedPiece) {
            char[][] shape = positionedPiece.getPiece().getShape();
            TextColor color = positionedPiece.getPiece().getColor();
            int x = positionedPiece.getX();
            int y = positionedPiece.getY();

            for (int row = 0; row < shape.length; row++) {
                for (int col = 0; col < shape[row].length; col++) {
                    if (shape[row][col] != ' ') {
                        board[y + row][x + col] = shape[row][col];
                        colors[y + row][x + col] = color;
                    }
                }
            }
        }

        /**
         * Delete all full lines in the board.
         */
        public void deleteFullLines() {
            int linesCleared = 0;

            for (int row = 0; row < height; row++) {
                if (isFullLine(row)) {
                    clearLine(row);
                    shiftLinesDown(row);
                    linesCleared++;
                }
            }

            if (linesCleared > 0) {
                notifyLineCleared(linesCleared);
            }
        }

        /**
         * Check if a line is full.
         *
         * @param row the row to check
         * @return true if the line is full, false otherwise
         */
        private boolean isFullLine(int row) {
            for (int col = 0; col < width; col++) {
                if (board[row][col] == ' ') return false;
            }
            return true;
        }

        /**
         * Clear a line by setting all blocks to ' ' and colors to {@link TextColor.ANSI.BLACK}.
         *
         * @param row the row to clear
         */
        private void clearLine(int row) {
            for (int col = 0; col < width; col++) {
                board[row][col] = ' ';
                colors[row][col] = TextColor.ANSI.BLACK;
            }
        }

        /**
         * Shift all lines down by one row, starting from the given row.
         *
         * @param startRow the row to start from
         */
        private void shiftLinesDown(int startRow) {
            for (int row = startRow; row > 0; row--) {
                System.arraycopy(board[row - 1], 0, board[row], 0, width);
                System.arraycopy(colors[row - 1], 0, colors[row], 0, width);
            }
            for (int col = 0; col < width; col++) {
                board[0][col] = ' ';
                colors[0][col] = TextColor.ANSI.BLACK;
            }
        }

        /**
         * Check if the game is over by checking if a piece can be placed at the given position.
         *
         * @param piece the piece to place
         */
        public void checkGameOver(PositionedPiece piece) {
            if (!canPlaceShape(piece.getPiece().getShape(), piece.getX(), piece.getY())) {
                notifyGameOver();
            }
        }
    }