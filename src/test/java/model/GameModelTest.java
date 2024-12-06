package model;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameModelTest {

    @Mock
    PieceFactory mockFactory;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInitialization() {
        when(mockFactory.createPiece()).thenReturn(new Piece(new char[][]{{'X'}}, TextColor.ANSI.CYAN));
        GameModel model = createModelWithMockFactory();
        assertTrue(model.isRunning());
        assertNotNull(model.getCurrentPiece());
    }

    @Test
    void testMoveLeft() {
        when(mockFactory.createPiece()).thenReturn(new Piece(new char[][]{{'X'}}, TextColor.ANSI.YELLOW));
        GameModel model = createModelWithMockFactory();
        int initialX = model.getCurrentPiece().getX();
        model.moveLeft();
        assertEquals(initialX - 1, model.getCurrentPiece().getX());
    }

    @Test
    void testLockPieceGameOver() {
        when(mockFactory.createPiece())
                .thenReturn(new Piece(new char[][]{
                        {'X'},
                        {'X'},
                        {'X'},
                        {'X'},
                        {'X'},
                        {'X'},
                        {'X'},
                        {'X'},
                        {'X'},
                        {'X'},
                        {'X'},
                        {'X'},
                        {'X'},
                        {'X'},
                        {'X'},
                        {'X'},
                        {'X'},
                        {'X'},
                        {'X'},
                        {'X'}
                }, TextColor.ANSI.RED))
                .thenReturn(new Piece(new char[][]{{'Z'}}, TextColor.ANSI.GREEN));

        GameModel model = createModelWithMockFactory();
        model.drop();


        assertFalse(model.isRunning(), "Game should be over because second piece cannot spawn at (4,0).");
    }


    @Test
    void testDropFully() {
        when(mockFactory.createPiece()).thenReturn(new Piece(new char[][]{{'X'}}, TextColor.ANSI.CYAN));
        GameModel model = createModelWithMockFactory();
        model.drop();
        // Piece should be locked at bottom or until collision. Just ensure no crash and still running if space allowed.
        assertTrue(model.isRunning());
    }

    private GameModel createModelWithMockFactory() {
        return new GameModel() {
            {
                try {
                    var field = GameModel.class.getDeclaredField("pieceFactory");
                    field.setAccessible(true);
                    field.set(this, mockFactory);
                    // re-generate current piece
                    var cpField = GameModel.class.getDeclaredField("currentPiece");
                    cpField.setAccessible(true);
                    cpField.set(this, new PositionedPiece(mockFactory.createPiece(), 4, 0));
                } catch (Exception e) {
                    fail("Reflection failed: " + e.getMessage());
                }
            }
        };
    }
}
