package model;

import com.googlecode.lanterna.TextColor;

import java.util.List;

public interface IBoard {
    char[][] getBoard();
    TextColor[][] getColors();
    boolean canPlaceShape(char[][] shape, int x, int y);
    void addPositionedPiece(PositionedPiece positionedPiece);
    void deleteFullLines();
    void checkGameOver(PositionedPiece piece);
    void addObserver(BoardObserver observer);
    List<BoardObserver> getObservers();
}
