package model;

public interface IPositionedPieceFactory {
    PositionedPiece createPiece(Piece piece, int x, int y);
}
