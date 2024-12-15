package model;

import com.googlecode.lanterna.TextColor;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPieceFactory implements PieceFactory {
    private final List<Piece> predefinedPieces;
    private final Random random;

    public RandomPieceFactory() {
        this.predefinedPieces = initializePieces();
        this.random = new Random();
    }

    private List<Piece> initializePieces() {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(new char[][]{{'O','O'},{'O','O'}}, TextColor.ANSI.WHITE));
        pieces.add(new Piece(new char[][]{{'I','I','I','I'}}, TextColor.ANSI.CYAN));
        pieces.add(new Piece(new char[][]{{' ','T',' '},{'T','T','T'}}, TextColor.ANSI.YELLOW));
        pieces.add(new Piece(new char[][]{{'J',' '},{'J',' '},{'J','J'}}, TextColor.ANSI.BLUE));
        pieces.add(new Piece(new char[][]{{' ','L'},{' ','L'},{'L','L'}}, TextColor.ANSI.RED));
        pieces.add(new Piece(new char[][]{{' ','S','S'},{'S','S',' '}}, TextColor.ANSI.GREEN));
        pieces.add(new Piece(new char[][]{{'Z','Z',' '},{' ','Z','Z'}}, TextColor.ANSI.GREEN));
        return pieces;
    }

    @Override
    public Piece createPiece() {
        int index = random.nextInt(predefinedPieces.size());
        Piece template = predefinedPieces.get(index);
        char[][] shapeCopy = copyShape(template.getShape());
        return new Piece(shapeCopy, template.getColor());
    }

    private char[][] copyShape(char[][] original) {
        char[][] copy = new char[original.length][original[0].length];
        for (int i = 0; i < original.length; i++)
            System.arraycopy(original[i], 0, copy[i], 0, original[i].length);
        return copy;
    }
}
