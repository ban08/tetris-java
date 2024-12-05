package model;

import com.googlecode.lanterna.TextColor;

import java.util.ArrayList;
import java.util.List;

/**
 * A class responsible for generating random pieces.
 */
public class PieceSelector {
    private final List<Piece> predefinedPieces;

    /**
     * Constructor that initializes the list of predefined pieces.
     */
    public PieceSelector() {
        this.predefinedPieces = initializePieces();
    }

    /**
     * Getter for predefined pieces for tests use.
     */
    public List<Piece> getPredefinedPieces(){
        return predefinedPieces;
    }
    /**
     * Initializes the list of predefined pieces.
     * 
     * @return the list of predefined pieces.
     */
    private List<Piece> initializePieces() {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(new char[][]{{'O', 'O'}, {'O', 'O'}}, TextColor.ANSI.YELLOW)); // Square (O)
        pieces.add(new Piece(new char[][]{{'I', 'I', 'I', 'I'}}, TextColor.ANSI.CYAN)); // Line (I)
        pieces.add(new Piece(new char[][]{{' ', 'T', ' '}, {'T', 'T', 'T'}}, TextColor.ANSI.MAGENTA)); // T-Shape (T)
        pieces.add(new Piece(new char[][]{{'J', ' '}, {'J', ' '}, {'J', 'J'}}, TextColor.ANSI.BLUE)); // J-Shape (J)
        pieces.add(new Piece(new char[][]{{'L', ' '}, {'L', ' '}, {'L', 'L'}}, TextColor.ANSI.RED)); // L-Shape (L)
        return pieces;
    }

    /**
     * Generates a random piece from the list of predefined pieces.
     * 
     * @return a random piece.
     */
    public Piece randomPiece() {
        int randomIndex = (int) (Math.random() * predefinedPieces.size());
        return predefinedPieces.get(randomIndex);
    }
}