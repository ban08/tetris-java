// src/test/java/states/GameStateTest.java
package states;

import com.googlecode.lanterna.TextColor;
import controller.GameController;
import model.Board;
import model.GameModel;
import model.Piece;
import model.PositionedPiece;
import music.Music;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import view.GUI;
import view.LanternaGUI;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.TerminalSize;

import java.io.IOException;

/**
 * Unit tests for the GameState class.
 */
public class GameStateTest {

    @Mock
    private Screen mockScreen;

    @Mock
    private Music mockMusic;

    @Mock
    private GameModel mockModel;

    @Mock
    private GameController mockController;

    @Mock
    private LanternaGUI mockLanternaGui;

    @Mock
    private TextGraphics mockTextGraphics;

    @Mock
    private TerminalSize mockTerminalSize;

    @Mock
    private PositionedPiece mockPositionedPiece;

    @Mock
    private Piece mockPiece;

    @Mock
    private Board mockBoard;

    @InjectMocks
    private GameState gameState;

    @BeforeEach
    public void setUp() throws IOException {
        // Initialize Mockito annotations
        MockitoAnnotations.openMocks(this);

        // Stub TerminalSize and TextGraphics
        when(mockScreen.getTerminalSize()).thenReturn(mockTerminalSize);
        when(mockScreen.newTextGraphics()).thenReturn(mockTextGraphics);

        // Stub LanternaGUI
        when(mockLanternaGui.getScreen()).thenReturn(mockScreen);

        // Stub GameModel methods
        when(mockModel.isRunning()).thenReturn(false); // Default: game over
        when(mockModel.getNextPiece()).thenReturn(mockPositionedPiece);
        when(mockModel.getCurrentPiece()).thenReturn(mockPositionedPiece);
        when(mockModel.getBoard()).thenReturn(mockBoard);
        when(mockModel.getScorePoints()).thenReturn(0);
        when(mockModel.getBonusCharge()).thenReturn(0);
        when(mockModel.isBonusActive()).thenReturn(false);
        when(mockModel.isPaused()).thenReturn(false);
        when(mockModel.getNextPiece().getPiece()).thenReturn(mockPiece);
        when(mockModel.getCurrentPiece().getPiece()).thenReturn(mockPiece);

        // Stub Board methods
        when(mockBoard.canPlaceShape(any(char[][].class), anyInt(), anyInt())).thenReturn(true);

        // Stub Board.getBoard() and getColors()
        char[][] mockBoardArray = new char[20][10]; // Example dimensions
        TextColor[][] mockColorArray = new TextColor[20][10]; // Example dimensions
        when(mockBoard.getBoard()).thenReturn(mockBoardArray);
        when(mockBoard.getColors()).thenReturn(mockColorArray);
        // Initialize the arrays with default values if needed
        for (int i = 0; i < mockBoardArray.length; i++) {
            for (int j = 0; j < mockBoardArray[i].length; j++) {
                mockBoardArray[i][j] = ' '; // Empty space
                mockColorArray[i][j] = TextColor.ANSI.BLACK; // Default color
            }
        }

        // **NEW STUB TO PREVENT NPE in renderActivePiece**
        when(mockPiece.getShape()).thenReturn(new char[][]{
                {'X', 'X'},
                {'X', 'X'}
        });

        // If renderActivePiece uses other properties, stub them as well
        // Example:
        // when(mockPiece.getRotation()).thenReturn(0); // If rotation is used

        // Instantiate GameState with injected mocks
        gameState = new GameState(mockScreen, mockMusic, mockModel, mockController);
    }

    @Test
    public void testGameOverTransition() throws IOException {
        // Arrange
        when(mockModel.isRunning()).thenReturn(false);

        // Act
        State nextState = gameState.step(mockLanternaGui, System.currentTimeMillis());

        // Assert
        verify(mockScreen, times(1)).clear();
        verify(mockTextGraphics, atLeastOnce()).putString(anyInt(), anyInt(), anyString());
        verify(mockScreen, times(1)).refresh();

        assertTrue(nextState instanceof GameOverState, "State should transition to GameOverState when game is over");
    }

    @Test
    public void testGameContinuesWhenRunning() throws IOException {
        // Arrange
        when(mockModel.isRunning()).thenReturn(true);
        when(mockLanternaGui.getNextAction()).thenReturn(GUI.ACTION.NONE);

        // Act
        State nextState = gameState.step(mockLanternaGui, System.currentTimeMillis());

        // Assert
        verify(mockScreen, times(1)).clear();
        verify(mockTextGraphics, atLeastOnce()).putString(anyInt(), anyInt(), anyString());
        verify(mockScreen, times(1)).refresh();

        assertSame(gameState, nextState, "State should remain GameState when game is running");
    }
}
