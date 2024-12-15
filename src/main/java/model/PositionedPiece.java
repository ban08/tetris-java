package model;

public class PositionedPiece {
    private final Piece piece;
    private int x, y;

    public PositionedPiece(Piece piece, int x, int y) {
        this.piece = piece;
        this.x = x;
        this.y = y;
    }

    public Piece getPiece() { return piece; }
    public int getX() { return x; }
    public int getY() { return y; }

    public void moveLeft() { x--; }
    public void moveRight() { x++; }
    public void moveDown() { y++; }

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
