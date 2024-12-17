package model;

import com.googlecode.lanterna.TextColor;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A factory class for generating random Tetris pieces.
 * This class implements the PieceFactory interface and provides
 * a method to create random Tetris pieces from a predefined list of shapes.
 */
public class RandomPieceFactory implements PieceFactory {
    private final List<Piece> predefinedPieces;
    private final Random random;

    /**
     * Constructs a RandomPieceFactory and initializes predefined Tetris pieces.
     */
    public RandomPieceFactory() {
        this.predefinedPieces = initializePieces();
        this.random = new Random();
    }

    /**
     * Initializes the list of predefined Tetris pieces.
     *
     * @return a list of predefined Tetris pieces.
     */
    private List<Piece> initializePieces() {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(new char[][]{{'O', 'O'}, {'O', 'O'}}, TextColor.ANSI.WHITE)); // Square
        pieces.add(new Piece(new char[][]{{'I', 'I', 'I', 'I'}}, TextColor.ANSI.CYAN)); // Line
        pieces.add(new Piece(new char[][]{{' ', 'T', ' '}, {'T', 'T', 'T'}}, TextColor.ANSI.YELLOW)); // T-Shape
        pieces.add(new Piece(new char[][]{{'J', ' '}, {'J', ' '}, {'J', 'J'}}, TextColor.ANSI.BLUE)); // J-Shape
        pieces.add(new Piece(new char[][]{{' ', 'L'}, {' ', 'L'}, {'L', 'L'}}, TextColor.ANSI.RED)); // L-Shape
        pieces.add(new Piece(new char[][]{{' ', 'S', 'S'}, {'S', 'S', ' '}}, TextColor.ANSI.GREEN)); // S-Shape
        pieces.add(new Piece(new char[][]{{'Z', 'Z', ' '}, {' ', 'Z', 'Z'}}, TextColor.ANSI.GREEN)); // Z-Shape
        return pieces;
    }

    /**
     * Creates a random Tetris piece by selecting a piece from the predefined list.
     *
     * @return a new random Tetris piece.
     */
    @Override
    public Piece createPiece() {
        int index = random.nextInt(predefinedPieces.size());
        Piece template = predefinedPieces.get(index);
        char[][] shapeCopy = copyShape(template.getShape());
        return new Piece(shapeCopy, template.getColor());
    }

    /**
     * Creates a deep copy of the given shape array.
     *
     * @param original the original shape array.
     * @return a deep copy of the shape array.
     */
    private char[][] copyShape(char[][] original) {
        char[][] copy = new char[original.length][original[0].length];
        for (int i = 0; i < original.length; i++)
            System.arraycopy(original[i], 0, copy[i], 0, original[i].length);
        return copy;
    }
}