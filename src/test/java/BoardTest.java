import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    public void testInitializeBoard() {
        Board board = new Board(3, 3);
        assertEquals(3, board.getBoard().length);
        assertEquals(3, board.getBoard()[0].length);
        assertEquals(' ', board.getBoard()[0][0]);
        assertEquals(TextColor.ANSI.BLACK, board.getColors()[0][0]);
    }

    @Test
    public void testAddObserver() {
        Board board = new Board(3, 3);
        BoardObserver observer = new BoardObserver() {
            @Override
            public void onLineCleared(int linesCleared) {}

            @Override
            public void onGameOver() {}
        };
        board.addObserver(observer);
        assertEquals(1, board.getObservers().size());
    }

    @Test
    public void testCanPlaceShape() {
        Board board = new Board(3, 3);
        char[][] shape = {{'A', 'A'}, {'A', 'A'}};
        assertTrue(board.canPlaceShape(shape, 0, 0));
        assertFalse(board.canPlaceShape(shape, 2, 3));
    }
    @Test
    public void testAddPositionedPiece() {
        Board board = new Board(3, 3);
        char[][] shape = {{'A', 'A'}, {'A', 'A'}}; // Declare shape variable locally
        PositionedPiece piece = new PositionedPiece(new Piece(shape, TextColor.ANSI.BLACK), 0, 0);
        board.addPositionedPiece(piece);
        assertEquals('A', board.getBoard()[0][0]);
        assertEquals(TextColor.ANSI.BLACK, board.getColors()[0][0]);
    }

    @Test
    public void testDeleteFullLines() {
        Board board = new Board(3, 3);
        board.addPositionedPiece(new PositionedPiece(new Piece(new char[][]{{'A', 'A', 'A'}}, TextColor.ANSI.BLACK), 0, 0));
        board.deleteFullLines();
        assertEquals(' ', board.getBoard()[0][0]);
        assertEquals(TextColor.ANSI.BLACK, board.getColors()[0][0]);
    }

    @Test
    public void testCheckGameOver() {
        Board board = new Board(3, 3);
        PositionedPiece piece = new PositionedPiece(new Piece(new char[][]{{'A', 'A'}, {'A', 'A'}}, TextColor.ANSI.BLACK), 1, 1);
        board.checkGameOver(piece);
        // Verificar se o jogo acabou
    }
}