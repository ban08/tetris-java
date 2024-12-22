package view;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import model.menu.StartMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

import java.io.IOException;

public class StartMenuViewerTest {

    private StartMenuViewer viewer;
    private StartMenu mockMenu;
    private LanternaGUI mockGui;
    private Screen mockScreen;
    private TextGraphics mockTextGraphics;

    @BeforeEach
    void setUp() {
        mockMenu = mock(StartMenu.class);
        viewer = new StartMenuViewer(mockMenu);

        mockGui = mock(LanternaGUI.class);
        mockScreen = mock(Screen.class);
        mockTextGraphics = mock(TextGraphics.class);

        when(mockGui.getScreen()).thenReturn(mockScreen);
        when(mockScreen.newTextGraphics()).thenReturn(mockTextGraphics);

        when(mockScreen.getTerminalSize()).thenReturn(new com.googlecode.lanterna.TerminalSize(40, 10));
    }

    @Test
    void testDraw_ValidScenario() throws IOException {
        viewer.draw(mockGui);

        verify(mockScreen, times(1)).clear();
        verify(mockScreen, times(1)).refresh();
        verify(mockTextGraphics, atLeastOnce()).setBackgroundColor(TextColor.ANSI.BLACK);
        verify(mockTextGraphics, atLeastOnce()).setForegroundColor(TextColor.ANSI.WHITE);
        verify(mockTextGraphics, atLeastOnce()).putString(
                anyInt(), anyInt(), contains("Press S to Start Game")
        );
        verify(mockTextGraphics, atLeastOnce()).putString(
                anyInt(), anyInt(), contains("Press E to Exit")
        );
    }

    @Test
    void testDraw_InvalidGUIInstance() throws IOException {
        GUI basicGui = mock(GUI.class); // Not a LanternaGUI

        try {
            viewer.draw(basicGui);
            fail("Expected an IllegalStateException for non-LanternaGUI");
        } catch (IllegalStateException e) {
        }
    }
}
