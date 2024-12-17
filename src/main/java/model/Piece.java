package model;

import com.googlecode.lanterna.TextColor;

/**
 * Represents a Tetris piece with a specific shape and color.
 * Each piece is defined by a 2D array of characters and a color.
 */
public class Piece {
    private char[][] shape;
    private final TextColor color;

    /**
     * Constructs a Piece object with the specified shape and color.
     *
     * @param shape the 2D array of characters representing the piece's shape
     * @param color the color of the piece
     */
    public Piece(char[][] shape, TextColor color) {
        this.shape = shape;
        this.color = color;
    }

    /**
     * Returns the shape of the piece.
     *
     * @return the 2D array of characters representing the piece's shape
     */
    public char[][] getShape() {
        return shape;
    }

    /**
     * Sets the shape of the piece.
     *
     * @param shape the 2D array of characters representing the new shape
     */
    public void setShape(char[][] shape) {
        this.shape = shape;
    }

    /**
     * Returns the color of the piece.
     *
     * @return the color of the piece
     */
    public TextColor getColor() {
        return color;
    }
}