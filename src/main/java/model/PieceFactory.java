package model;
/**
 * An interface for generating random pieces.
 *
 * <p>A piece factory should be able to generate a random piece when the createPiece method is called.
 */
public interface PieceFactory {
    /**
     * Generates a random piece.
     * 
     * @return a random piece
     */
    Piece createPiece();
}