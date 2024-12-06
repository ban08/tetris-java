package model;

import com.googlecode.lanterna.TextColor;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A factory that creates random pieces from a predefined set.
 */
public class RandomPieceFactory implements PieceFactory {
    private final List<Piece> predefinedPieces;
    private final Random random;

    public RandomPieceFactory() {
        this.predefinedPieces = initializePieces();
        this.random = new Random();
    }

    private List<Piece> initializePieces() {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(new char[][]{{'O', 'O'}, {'O', 'O'}}, TextColor.ANSI.YELLOW)); // O-Shape
        pieces.add(new Piece(new char[][]{{'I', 'I', 'I', 'I'}}, TextColor.ANSI.CYAN));     // I-Shape
        pieces.add(new Piece(new char[][]{{' ', 'T', ' '}, {'T', 'T', 'T'}}, TextColor.ANSI.MAGENTA)); // T-Shape
        pieces.add(new Piece(new char[][]{{'J', ' '}, {'J', ' '}, {'J', 'J'}}, TextColor.ANSI.BLUE)); // J-Shape
        pieces.add(new Piece(new char[][]{{'L', ' '}, {'L', ' '}, {'L', 'L'}}, TextColor.ANSI.RED));   // L-Shape
        return pieces;
    }

    @Override
    public Piece createPiece() {
        int index = random.nextInt(predefinedPieces.size());
        // Create a new Piece instance so pieces are not shared references
        Piece template = predefinedPieces.get(index);
        char[][] shapeCopy = copyShape(template.getShape());
        return new Piece(shapeCopy, template.getColor());
    }

    private char[][] copyShape(char[][] original) {
        char[][] copy = new char[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            System.arraycopy(original[i], 0, copy[i], 0, original[i].length);
        }
        return copy;
    }
}
