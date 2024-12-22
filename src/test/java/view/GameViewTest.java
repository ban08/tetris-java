package view;

import com.googlecode.lanterna.TextColor;
import model.GameModel;
import model.Piece;
import model.PositionedPiece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class GameViewTest {

    private GameRenderer mockRenderer;
    private GameModel mockModel;
    private GameView view;

    @BeforeEach
    void setUp() {
        mockRenderer = mock(GameRenderer.class);
        mockModel = mock(GameModel.class);

        Piece dummyPiece = new Piece(new char[][]{{'X'}}, TextColor.ANSI.YELLOW);
        PositionedPiece dummyPositioned = new PositionedPiece(dummyPiece, 0, 0);

        when(mockModel.getNextPiece()).thenReturn(dummyPositioned);
        when(mockModel.getCurrentPiece()).thenReturn(dummyPositioned);

        view = new GameView(mockRenderer, mockModel);
    }

    @Test
    void testRenderWithoutLineSelection() {
        // ARRANGE: Stub typical model state
        when(mockModel.getScorePoints()).thenReturn(123);
        when(mockModel.getBonusCharge()).thenReturn(200);
        when(mockModel.isBonusActive()).thenReturn(false);
        when(mockModel.isPaused()).thenReturn(false);

        var boardStub       = mockModel.getBoard();            // stubbed Board
        var currentPiece    = mockModel.getCurrentPiece();     // stubbed PositionedPiece
        var nextPieceObj    = mockModel.getNextPiece().getPiece(); // actual Piece
        String timerString  = "Time: 00:30";

        // ACT
        view.render(timerString);

        // ASSERT
        verify(mockRenderer, times(1)).render(
                eq(boardStub),           // from local variable
                eq(currentPiece),
                eq(123),
                eq(200),
                eq(false),               // isBonusActive
                eq(false),               // choosingLines
                eq(0),                   // selectedLine
                eq(List.of()),           // empty chosenLines
                eq(false),               // isPaused
                eq(nextPieceObj),        // next piece
                eq(timerString)          // time string
        );
    }

    @Test
    void testRenderWithLineSelection() {
        // ARRANGE
        when(mockModel.getScorePoints()).thenReturn(999);
        when(mockModel.getBonusCharge()).thenReturn(500);
        when(mockModel.isBonusActive()).thenReturn(true);
        when(mockModel.isPaused()).thenReturn(false);

        var boardStub       = mockModel.getBoard();
        var currentPiece    = mockModel.getCurrentPiece();
        var nextPieceObj    = mockModel.getNextPiece().getPiece();

        List<Integer> chosenLines = List.of(1, 3);
        String timerString  = "Time: 01:00";

        // ACT
        view.render(true, 2, chosenLines, timerString);

        // ASSERT
        verify(mockRenderer, times(1)).render(
                eq(boardStub),
                eq(currentPiece),
                eq(999),
                eq(500),
                eq(true),          // isBonusActive
                eq(true),          // choosingLines
                eq(2),            // selectedLine
                eq(chosenLines),
                eq(false),         // isPaused
                eq(nextPieceObj),
                eq(timerString)
        );
    }
}
