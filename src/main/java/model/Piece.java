package model;

import com.googlecode.lanterna.TextColor;

/**
 * Represents a piece in the game with a specific shape and color.
 */
public class Piece {
    private char[][] shape;
    private final TextColor color;

    /**
     * Constructs a model.Piece with the specified shape and color.
     *
     * @param shape the shape of the piece
     * @param color the color of the piece
     */
    public Piece(char[][] shape, TextColor color) {
        this.shape = shape;
        this.color = color;
    }

    /**
     * Returns the shape of the piece.
     *
     * @return the shape of the piece
     */
    public char[][] getShape() {
        return shape;
    }

    /**
     * Sets the shape of the piece.
     *
     * @param shape the new shape to be set
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