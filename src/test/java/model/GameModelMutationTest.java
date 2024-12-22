package model;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameModelMutationTest {

    private GameModel model;
    private Board mockBoard;
    private Score mockScore;
    private PieceFactory mockFactory;
    @BeforeEach
    void setUp() {
        mockBoard = mock(Board.class);
        mockScore = mock(Score.class);
        mockFactory = mock(PieceFactory.class);

        char[][] boardArray = new char[20][10];
        TextColor[][] colorArray = new TextColor[20][10];
        for(int r=0; r<20; r++){
            for(int c=0; c<10; c++){
                boardArray[r][c] = ' ';
                colorArray[r][c] = TextColor.ANSI.BLACK;
            }
        }
        when(mockBoard.getBoard()).thenReturn(boardArray);
        when(mockBoard.getColors()).thenReturn(colorArray);

        Piece somePiece = new Piece(new char[][]{{'O'}}, TextColor.ANSI.RED);
        when(mockFactory.createPiece()).thenReturn(somePiece);

        model = new GameModel(mockBoard, mockScore, mockFactory);
    }
    @Test
    void testLockCurrentPiece_GainedScoreNegative() {
        model.setLastScore(200);
        when(mockScore.getPoints()).thenReturn(100);

        model.lockCurrentPiece();
        assertEquals(0, model.getBonusCharge(), "BonusCharge should remain zero if gainedScore is negative.");
    }

    @Test
    void testExecuteBonus_InvalidRow() {
        model.setBonusCharge(500);
        model.activateBonus();
        assertTrue(model.isBonusActive());

        model.executeBonus(List.of(-1, 25));
        verify(mockScore, never()).onLineCleared(anyInt());
        assertFalse(model.isBonusActive());
    }
}
