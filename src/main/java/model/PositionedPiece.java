package model;
/**
 * A class representing a piece that is positioned on a board.
 *
 * <p>A PositionedPiece is created with a reference to a Piece and an x and y
 * coordinate. The x and y coordinates represent the top-left corner of the
 * piece on the board.
 *
 * <p>The class provides methods for moving the piece left, right, and down on
 * the board. It also provides a method for rotating the piece on the board.
 *
 * <p>The rotation method takes a RotationStrategy and a Board as parameters.
 * The RotationStrategy is used to rotate the piece, and the Board is used to
 * check if the rotated piece can be placed on the board. If the rotated piece
 * can be placed on the board, the piece is updated and the x and y coordinates
 * are adjusted. If the rotated piece cannot be placed on the board, the piece
 * is not updated and the x and y coordinates are not changed.
 *
 *
 *
 *
 */
public class PositionedPiece {
    private final Piece piece;
    private int x, y;

    /**
     * Creates a new PositionedPiece with the given piece and x and y
     * coordinates.
     *
     * @param piece the piece to position
     * @param x the x coordinate of the top-left corner of the piece
     * @param y the y coordinate of the top-left corner of the piece
     */
    public PositionedPiece(Piece piece, int x, int y) {
        this.piece = piece;
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the piece of this PositionedPiece.
     *
     * @return the piece of this PositionedPiece
     */
    public Piece getPiece() { return piece; }

    /**
     * Returns the x coordinate of this PositionedPiece.
     *
     * @return the x coordinate of this PositionedPiece
     */
    public int getX() { return x; }

    /**
     * Returns the y coordinate of this PositionedPiece.
     *
     * @return the y coordinate of this PositionedPiece
     */
    public int getY() { return y; }

    /**
     * Moves this PositionedPiece one unit to the left.
     */
    public void moveLeft() { x--; }

    /**
     * Moves this PositionedPiece one unit to the right.
     */
    public void moveRight() { x++; }

    /**
     * Moves this PositionedPiece one unit down.
     */
    public void moveDown() { y++; }

    /**
     * Rotates this PositionedPiece on the given board using the given
     * RotationStrategy.
     *
     * @param board the board to rotate the piece on
     * @param strategy the RotationStrategy to use
     */
    public void rotate(Board board, RotationStrategy strategy) {
        char[][] originalShape = piece.getShape();
        int originalX = x;
        int originalY = y;

        char[][] rotatedShape = strategy.rotate(originalShape);
        int adjustedX = Math.max(0, Math.min(board.getBoard()[0].length - rotatedShape[0].length, x));
        int adjustedY = Math.max(0, Math.min(board.getBoard().length - rotatedShape.length, y));

        if (board.canPlaceShape(rotatedShape, adjustedX, adjustedY)) {
            piece.setShape(rotatedShape);
            x = adjustedX;
            y = adjustedY;
        } else {
            piece.setShape(originalShape);
            x = originalX;
            y = originalY;
        }
    }
}