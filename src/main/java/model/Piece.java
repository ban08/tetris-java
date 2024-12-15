package model;

import com.googlecode.lanterna.TextColor;

public class Piece {
    private char[][] shape;
    private final TextColor color;

    public Piece(char[][] shape, TextColor color) {
        this.shape = shape;
        this.color = color;
    }

    public char[][] getShape() { return shape; }
    public void setShape(char[][] shape) { this.shape = shape; }
    public TextColor getColor() { return color; }
}
