import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PieceSelectorTest {

    @Test
    void testInitializePieces() {
        PieceSelector pieceselector= new PieceSelector();
        List<Piece> predefinedpieces= pieceselector.getPredefinedPieces();
        assertEquals(5,predefinedpieces.size());
        // O
        Piece OPiece = predefinedpieces.get(0);
        assertArrayEquals(new char[][]{{'O', 'O'}, {'O', 'O'}}, OPiece.getShape());
        assertEquals(TextColor.ANSI.YELLOW, OPiece.getColor());

        // I
        Piece iPiece = predefinedpieces.get(1);
        assertArrayEquals(new char[][]{{'I', 'I', 'I', 'I'}}, iPiece.getShape());
        assertEquals(TextColor.ANSI.CYAN, iPiece.getColor());

        // T
        Piece tShapePiece = predefinedpieces.get(2);
        assertArrayEquals(new char[][]{{' ', 'T', ' '}, {'T', 'T', 'T'}}, tShapePiece.getShape());
        assertEquals(TextColor.ANSI.MAGENTA, tShapePiece.getColor());

        // J
        Piece jShapePiece = predefinedpieces.get(3);
        assertArrayEquals(new char[][]{{'J', ' '}, {'J', ' '}, {'J', 'J'}}, jShapePiece.getShape());
        assertEquals(TextColor.ANSI.BLUE, jShapePiece.getColor());

        // L
        Piece lShapePiece = predefinedpieces.get(4);
        assertArrayEquals(new char[][]{{'L', ' '}, {'L', ' '}, {'L', 'L'}}, lShapePiece.getShape());
        assertEquals(TextColor.ANSI.RED, lShapePiece.getColor());
    }

    @Test
    void testRandomPiece() {
        PieceSelector pieceSelector= new PieceSelector();
        Piece randomPiece= pieceSelector.randomPiece();
        assertNotNull(randomPiece);
    }
}

