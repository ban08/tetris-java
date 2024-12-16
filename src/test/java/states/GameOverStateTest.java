package states;

import music.Music;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.GUI;
import view.LanternaGUI;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.TerminalSize;

import java.io.IOException;

/**
 * Unit tests for the GameOverState class.
 */
public class GameOverStateTest {

    private GameOverState gameOverState;
    private Screen mockScreen;
    private Music mockMusic;
    private LanternaGUI mockLanternaGui;

    private TextGraphics mockTextGraphics;
    private TerminalSize mockTerminalSize;

    @BeforeEach
    public void setUp() throws IOException {
        // Mock the Screen
        mockScreen = mock(Screen.class);
        mockMusic = mock(Music.class);
        gameOverState = new GameOverState(mockScreen, mockMusic);

        // Mock TerminalSize and TextGraphics
        mockTerminalSize = new TerminalSize(80, 24);
        mockTextGraphics = mock(TextGraphics.class);
        when(mockScreen.getTerminalSize()).thenReturn(mockTerminalSize);
        when(mockScreen.newTextGraphics()).thenReturn(mockTextGraphics);

        // Mock LanternaGUI
        mockLanternaGui = mock(LanternaGUI.class);
        when(mockLanternaGui.getScreen()).thenReturn(mockScreen);
    }

    @Test
    public void testPressingRTransitionsToStartMenuState() throws IOException {
        // Arrange
        when(mockLanternaGui.getNextAction()).thenReturn(GUI.ACTION.R_KEY);

        // Act
        State nextState = gameOverState.step(mockLanternaGui, System.currentTimeMillis());

        // Assert
        verify(mockScreen, times(1)).clear();
        verify(mockTextGraphics, atLeastOnce()).putString(anyInt(), anyInt(), anyString());
        verify(mockScreen, times(1)).refresh();

        assertTrue(nextState instanceof StartMenuState, "State should transition to StartMenuState when 'R' is pressed");
    }

    @Test
    public void testPressingQTransitionsToExit() throws IOException {
        // Arrange
        when(mockLanternaGui.getNextAction()).thenReturn(GUI.ACTION.Q_KEY);

        // Act
        State nextState = gameOverState.step(mockLanternaGui, System.currentTimeMillis());

        // Assert
        verify(mockScreen, times(1)).clear();
        verify(mockTextGraphics, atLeastOnce()).putString(anyInt(), anyInt(), anyString());
        verify(mockScreen, times(1)).refresh();

        assertNull(nextState, "State should return null to exit when 'Q' is pressed");
    }

    @Test
    public void testPressingOtherKeysRemainsInGameOverState() throws IOException {
        // Arrange
        when(mockLanternaGui.getNextAction()).thenReturn(GUI.ACTION.NONE);

        // Act
        State nextState = gameOverState.step(mockLanternaGui, System.currentTimeMillis());

        // Assert
        verify(mockScreen, times(1)).clear();
        verify(mockTextGraphics, atLeastOnce()).putString(anyInt(), anyInt(), anyString());
        verify(mockScreen, times(1)).refresh();

        assertSame(gameOverState, nextState, "State should remain GameOverState for unrelated key presses");
    }
}
