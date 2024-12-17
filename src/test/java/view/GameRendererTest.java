// src/test/java/view/GameRendererTest.java
package view;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import model.Board;
import model.PositionedPiece;
import model.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class GameRendererTest {

    private Screen mockScreen;
    private GameRenderer renderer;
    private TextGraphics mockTextGraphics;

    @BeforeEach
    void setUp() throws IOException {
        mockScreen = mock(Screen.class);
        renderer = new GameRenderer(mockScreen);
        mockTextGraphics = mock(TextGraphics.class);
        when(mockScreen.newTextGraphics()).thenReturn(mockTextGraphics);
        when(mockScreen.getTerminalSize()).thenReturn(new com.googlecode.lanterna.TerminalSize(80, 24));
    }

    @Test
    void testRenderCalledWithCorrectParameters() {
        // Arrange
        Board mockBoard = mock(Board.class);
        char[][] boardState = new char[][]{
                {' ', ' ', ' '},
                {'X', 'X', 'X'},
                {' ', ' ', ' '}
        };
        TextColor[][] colors = new TextColor[][]{
                {TextColor.ANSI.BLACK, TextColor.ANSI.BLACK, TextColor.ANSI.BLACK},
                {TextColor.ANSI.RED, TextColor.ANSI.RED, TextColor.ANSI.RED},
                {TextColor.ANSI.BLACK, TextColor.ANSI.BLACK, TextColor.ANSI.BLACK}
        };
        when(mockBoard.getBoard()).thenReturn(boardState);
        when(mockBoard.getColors()).thenReturn(colors);

        Piece currentPiece = new Piece(new char[][]{{'I'}, {'I'}, {'I'}, {'I'}}, TextColor.ANSI.CYAN);
        PositionedPiece positionedPiece = new PositionedPiece(currentPiece, 1, 0);

        Piece nextPiece = new Piece(new char[][]{{'O', 'O'}, {'O', 'O'}}, TextColor.ANSI.YELLOW);

        int score = 1500;
        int bonusCharge = 300;
        boolean bonusActive = true;
        boolean choosingLines = true;
        int selectedLine = 2;
        java.util.List<Integer> chosenLines = java.util.List.of(3, 5, 7);
        boolean paused = false;
        String timer = "Time: 00:05";

        // Act
        renderer.render(mockBoard, positionedPiece, score, bonusCharge, bonusActive, choosingLines, selectedLine, chosenLines, paused, nextPiece, timer);

        // Assert
        verify(mockScreen, times(1)).clear();
        verify(mockScreen, times(1)).newTextGraphics();
        verify(mockTextGraphics, atLeastOnce()).setBackgroundColor(any());
        verify(mockTextGraphics, atLeastOnce()).setForegroundColor(any());
        verify(mockTextGraphics, atLeastOnce()).putString(anyInt(), anyInt(), anyString());
        verify(mockScreen, times(1)).clear();
    }

    @Test
    void testRender_NextPieceDisplayedCorrectly() {
        // Arrange
        Board mockBoard = mock(Board.class);
        char[][] boardState = new char[][]{
                {' ', ' ', ' '},
                {'X', 'X', 'X'},
                {' ', ' ', ' '}
        };
        TextColor[][] colors = new TextColor[][]{
                {TextColor.ANSI.BLACK, TextColor.ANSI.BLACK, TextColor.ANSI.BLACK},
                {TextColor.ANSI.RED, TextColor.ANSI.RED, TextColor.ANSI.RED},
                {TextColor.ANSI.BLACK, TextColor.ANSI.BLACK, TextColor.ANSI.BLACK}
        };
        when(mockBoard.getBoard()).thenReturn(boardState);
        when(mockBoard.getColors()).thenReturn(colors);

        Piece currentPiece = new Piece(new char[][]{{'S', 'S'}, {'S', 'S'}}, TextColor.ANSI.GREEN);
        PositionedPiece positionedPiece = new PositionedPiece(currentPiece, 3, 3);

        Piece nextPiece = new Piece(new char[][]{{'Z', 'Z', ' '}, {' ', 'Z', 'Z'}}, TextColor.ANSI.GREEN);

        int score = 800;
        int bonusCharge = 100;
        boolean bonusActive = false;
        boolean choosingLines = false;
        int selectedLine = 0;
        java.util.List<Integer> chosenLines = java.util.List.of();
        boolean paused = false;
        String timer = "Time: 00:45";

        // Act
        renderer.render(mockBoard, positionedPiece, score, bonusCharge, bonusActive, choosingLines, selectedLine, chosenLines, paused, nextPiece, timer);

        // Assert
        // Verify that the "Next" label is rendered
        verify(mockTextGraphics, atLeastOnce()).putString(anyInt(), anyInt(), eq("Next"));
        // Verify that the next piece shape is rendered correctly
        verify(mockTextGraphics, atLeastOnce()).putString(anyInt(), anyInt(), eq("█"));
    }

    @Test
    void testRender_WithBonusActiveAndChoosingLines() {
        // Arrange
        Board mockBoard = mock(Board.class);
        char[][] boardState = new char[][]{
                {' ', ' ', ' '},
                {'X', 'X', 'X'},
                {' ', ' ', ' '}
        };
        TextColor[][] colors = new TextColor[][]{
                {TextColor.ANSI.BLACK, TextColor.ANSI.BLACK, TextColor.ANSI.BLACK},
                {TextColor.ANSI.RED, TextColor.ANSI.RED, TextColor.ANSI.RED},
                {TextColor.ANSI.BLACK, TextColor.ANSI.BLACK, TextColor.ANSI.BLACK}
        };
        when(mockBoard.getBoard()).thenReturn(boardState);
        when(mockBoard.getColors()).thenReturn(colors);

        Piece currentPiece = new Piece(new char[][]{{'I'}, {'I'}, {'I'}, {'I'}}, TextColor.ANSI.CYAN);
        PositionedPiece positionedPiece = new PositionedPiece(currentPiece, 1, 0);

        Piece nextPiece = new Piece(new char[][]{{'O', 'O'}, {'O', 'O'}}, TextColor.ANSI.YELLOW);

        int score = 2000;
        int bonusCharge = 500;
        boolean bonusActive = true;
        boolean choosingLines = true;
        int selectedLine = 1; // Adjust for testing
        java.util.List<Integer> chosenLines = java.util.List.of(0, 2); // Adjust for testing
        boolean paused = false;
        String timer = "Time: 01:00";

        // Act
        renderer.render(mockBoard, positionedPiece, score, bonusCharge, bonusActive, choosingLines, selectedLine, chosenLines, paused, nextPiece, timer);

        // Assert
        // Verify that the selection marker (">") is drawn at the correct position
        int boardX = (80 - boardState[0].length) / 2 - 3; // Assuming terminal width of 80
        int boardY = (24 - boardState.length) / 2 + selectedLine;

        verify(mockTextGraphics).putString(eq(boardX), eq(boardY), eq(">")); // Verify selected line marker
        verify(mockTextGraphics).putString(eq(boardX), eq(boardY - 1), eq("*")); // Verify chosen line marker
        verify(mockTextGraphics).putString(eq(boardX), eq(boardY + 1), eq("*")); // Verify chosen line marker
        verify(mockTextGraphics, atLeastOnce()).putString(anyInt(), anyInt(), anyString());
    }

    @Test
    void testRender_WhenGameIsPaused() {
        // Arrange
        Board mockBoard = mock(Board.class);
        char[][] boardState = new char[][]{
                {' ', ' ', ' '},
                {'X', 'X', 'X'},
                {' ', ' ', ' '}
        };
        TextColor[][] colors = new TextColor[][]{
                {TextColor.ANSI.BLACK, TextColor.ANSI.BLACK, TextColor.ANSI.BLACK},
                {TextColor.ANSI.RED, TextColor.ANSI.RED, TextColor.ANSI.RED},
                {TextColor.ANSI.BLACK, TextColor.ANSI.BLACK, TextColor.ANSI.BLACK}
        };
        when(mockBoard.getBoard()).thenReturn(boardState);
        when(mockBoard.getColors()).thenReturn(colors);

        Piece currentPiece = new Piece(new char[][]{{'L', ' '}, {'L', ' '}, {'L', 'L'}}, TextColor.ANSI.RED);
        PositionedPiece positionedPiece = new PositionedPiece(currentPiece, 4, 2);

        Piece nextPiece = new Piece(new char[][]{{'I', 'I', 'I', 'I'}}, TextColor.ANSI.CYAN);

        int score = 1000;
        int bonusCharge = 250;
        boolean bonusActive = false;
        boolean choosingLines = false;
        int selectedLine = 0;
        java.util.List<Integer> chosenLines = java.util.List.of();
        boolean paused = true;
        String timer = "Time: 00:30";

        // Act
        renderer.render(mockBoard, positionedPiece, score, bonusCharge, bonusActive, choosingLines, selectedLine, chosenLines, paused, nextPiece, timer);

        // Assert
        verify(mockTextGraphics, atLeastOnce()).putString(anyInt(), anyInt(), eq("PAUSED"));
    }

}
