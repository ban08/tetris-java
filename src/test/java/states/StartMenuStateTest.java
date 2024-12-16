package states;

import controller.GameController;
import music.Music;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.GUI;
import view.LanternaGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.TerminalSize;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

/**
 * Unit tests for the StartMenuState class.
 */
public class StartMenuStateTest {

    private StartMenuState startMenuState;
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
        startMenuState = new StartMenuState(mockScreen, mockMusic);

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
    public void testPressingSTransitionsToGameState() throws IOException {
        // Arrange
        when(mockLanternaGui.getNextAction()).thenReturn(GUI.ACTION.S_KEY);

        // Act
        State nextState = startMenuState.step(mockLanternaGui, System.currentTimeMillis());

        // Assert
        verify(mockScreen, times(1)).clear();
        verify(mockTextGraphics, atLeastOnce()).putString(anyInt(), anyInt(), anyString());
        verify(mockScreen, times(1)).refresh();

        assertTrue(nextState instanceof GameState, "State should transition to GameState when 'S' is pressed");
    }

    @Test
    public void testPressingETransitionsToExit() throws IOException {
        // Arrange
        when(mockLanternaGui.getNextAction()).thenReturn(GUI.ACTION.E_KEY);

        // Act
        State nextState = startMenuState.step(mockLanternaGui, System.currentTimeMillis());

        // Assert
        verify(mockScreen, times(1)).clear();
        verify(mockTextGraphics, atLeastOnce()).putString(anyInt(), anyInt(), anyString());
        verify(mockScreen, times(1)).refresh();

        assertNull(nextState, "State should return null to exit when 'E' is pressed");
    }

    @Test
    public void testPressingOtherKeysRemainsInStartMenuState() throws IOException {
        // Arrange
        when(mockLanternaGui.getNextAction()).thenReturn(GUI.ACTION.NONE);

        // Act
        State nextState = startMenuState.step(mockLanternaGui, System.currentTimeMillis());

        // Assert
        verify(mockScreen, times(1)).clear();
        verify(mockTextGraphics, atLeastOnce()).putString(anyInt(), anyInt(), anyString());
        verify(mockScreen, times(1)).refresh();

        assertSame(startMenuState, nextState, "State should remain StartMenuState for unrelated key presses");
    }
}
