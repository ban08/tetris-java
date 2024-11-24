/**
 * A Tetris piece with a position on the board.
 */
public class PositionedPiece {
    private final Piece piece;
    private int x, y;

    /**
     * Constructor.
     *
     * @param piece the piece
     * @param x the x position
     * @param y the y position
     */
    public PositionedPiece(Piece piece, int x, int y) {
        this.piece = piece;
        this.x = x;
        this.y = y;
    }

    /**
     * Get the piece.
     *
     * @return the piece
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Get the x position.
     *
     * @return the x position
     */
    public int getX() {
        return x;
    }

    /**
     * Get the y position.
     *
     * @return the y position
     */
    public int getY() {
        return y;
    }

    /**
     * Move the piece one position to the left.
     */
    public void moveLeft() {
        x--;
    }

    /**
     * Move the piece one position to the right.
     */
    public void moveRight() {
        x++;
    }

    /**
     * Move the piece one position down.
     */
    public void moveDown() {
        y++;
    }

    /**
     * Rotate the piece according to the given strategy.
     *
     * @param board the board
     * @param strategy the rotation strategy
     */
    public void rotate(Board board, RotationStrategy strategy) {
        char[][] originalShape = piece.getShape(); // Backup original state
        int originalX = x;
        int originalY = y;

        char[][] rotatedShape = strategy.rotate(originalShape);

        // Adjust X and Y if out of bounds
        int adjustedX = Math.max(0, Math.min(board.getBoard()[0].length - rotatedShape[0].length, x));
        int adjustedY = Math.max(0, Math.min(board.getBoard().length - rotatedShape.length, y));

        // Validate placement
        if (board.canPlaceShape(rotatedShape, adjustedX, adjustedY)) {
            piece.setShape(rotatedShape);
            x = adjustedX;
            y = adjustedY;
        } else {
            piece.setShape(originalShape); // Revert shape
            x = originalX; // Revert X position
            y = originalY; // Revert Y position
        }
    }
}