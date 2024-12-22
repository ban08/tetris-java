package view;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.TerminalSize;
import model.Board;
import model.Piece;
import model.PositionedPiece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameRendererTest {

    private Screen mockScreen;
    private TextGraphics mockTextGraphics;
    private GameRenderer renderer;

    @BeforeEach
    void setUp() throws IOException {
        mockScreen = mock(Screen.class);
        mockTextGraphics = mock(TextGraphics.class);
        when(mockScreen.newTextGraphics()).thenReturn(mockTextGraphics);
        when(mockScreen.getTerminalSize()).thenReturn(new TerminalSize(80, 24));

        renderer = new GameRenderer(mockScreen);
    }

    @Test
    void testRender_NormalBoardAndPiece() throws IOException {
        Board board = mock(Board.class);
        char[][] boardData = {
                {' ', ' ', 'X'},
                {'X', 'X', ' '},
                {' ', ' ', ' '}
        };
        TextColor[][] colorData = new TextColor[3][3];
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                colorData[r][c] = TextColor.ANSI.BLACK;
            }
        }
        when(board.getBoard()).thenReturn(boardData);
        when(board.getColors()).thenReturn(colorData);

        Piece current = new Piece(new char[][]{
                {'I','I'},
                {'I','I'}
        }, TextColor.ANSI.BLUE);
        PositionedPiece positionedPiece = new PositionedPiece(current, 1, 1);

        Piece nextPiece = new Piece(new char[][]{
                {'O','O'},
                {'O','O'}
        }, TextColor.ANSI.YELLOW);

        renderer.render(
                board,
                positionedPiece,
                150,      // score
                250,      // bonusCharge
                false,    // bonusActive
                false,    // choosingLines
                0,        // selectedLine
                List.of(),// chosenLines
                false,    // paused
                nextPiece,
                "Time: 00:30"
        );

        verify(mockScreen).clear();
        verify(mockScreen).newTextGraphics();
        verify(mockTextGraphics, atLeastOnce()).putString(anyInt(), anyInt(), anyString());
        verify(mockScreen, times(1)).refresh();

        ArgumentCaptor<String> strCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockTextGraphics, atLeastOnce()).putString(anyInt(), anyInt(), strCaptor.capture());
        List<String> allStrings = strCaptor.getAllValues();

        assertTrue(allStrings.contains("Score: 150"), "Should contain 'Score: 150'");
        assertTrue(allStrings.contains("Time: 00:30"), "Should contain 'Time: 00:30'");
        assertTrue(allStrings.contains("Next"), "Should contain 'Next' label");
    }

    @Test
    void testRender_Paused() {
        Board board = mock(Board.class);
        when(board.getBoard()).thenReturn(new char[2][2]);
        when(board.getColors()).thenReturn(new TextColor[2][2]);

        renderer.render(
                board,
                null,
                999,
                500,
                false,
                false,
                0,
                List.of(),
                true, // paused
                null,
                "Time: 01:00"
        );

        ArgumentCaptor<String> strCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockTextGraphics, atLeastOnce()).putString(anyInt(), anyInt(), strCaptor.capture());
        assertTrue(strCaptor.getAllValues().contains("PAUSED"));
    }

    @Test
    void testRender_BonusActiveAndChoosingLines() {
        Board board = mock(Board.class);
        // Let's say a 4x3 board
        when(board.getBoard()).thenReturn(new char[3][4]);
        when(board.getColors()).thenReturn(new TextColor[3][4]);

        List<Integer> chosenLines = new ArrayList<>();
        chosenLines.add(0);
        chosenLines.add(2);

        renderer.render(
                board,
                null,
                1234,
                500,
                true, // bonusActive
                true, // choosingLines
                1,    // selectedLine
                chosenLines,
                false,
                null,
                "Time: 02:00"
        );

        ArgumentCaptor<String> strCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockTextGraphics, atLeastOnce()).putString(anyInt(), anyInt(), strCaptor.capture());
        List<String> allValues = strCaptor.getAllValues();
        assertTrue(allValues.contains("Select 3 lines with ENTER:"),
                "Should instruct user to select lines in bonus mode.");

        verify(mockTextGraphics, atLeast(1)).putString(anyInt(), anyInt(), eq(">")); // for line #1
        verify(mockTextGraphics, atLeast(2)).putString(anyInt(), anyInt(), eq("*")); // for lines [0,2]
    }

    @Test
    void testRender_EmptyBoardAndNoPieces() throws IOException {
        Board mockBoard = mock(Board.class);

        char[][] boardData = new char[1][1];
        TextColor[][] colorData = new TextColor[1][1];
        boardData[0][0] = ' ';
        colorData[0][0] = TextColor.ANSI.BLACK;

        when(mockBoard.getBoard()).thenReturn(boardData);
        when(mockBoard.getColors()).thenReturn(colorData);

        renderer.render(
                mockBoard,
                null, // current piece
                0,    // score
                0,    // bonus charge
                false,// bonus active
                false,// choosing lines
                0,    // selected line
                List.of(),
                false,// paused
                null, // next piece
                "Time: 00:00"
        );

        verify(mockScreen, times(1)).refresh();
    }

    @Test
    void testRender_ScreenRefreshIOExceptionCaught() throws IOException {
        doThrow(new IOException("Forced refresh error")).when(mockScreen).refresh();

        Board board = mock(Board.class);
        when(board.getBoard()).thenReturn(new char[1][1]);
        when(board.getColors()).thenReturn(new TextColor[1][1]);

        // Should not crash
        renderer.render(
                board,
                null,
                999,
                0,
                false,
                false,
                0,
                List.of(),
                false,
                null,
                "Time: 00:10"
        );
        verify(mockScreen, times(1)).refresh();
    }
}
