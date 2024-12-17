package view;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LanternaGUITest {

    private Screen mockScreen;
    private LanternaGUI gui;

    @BeforeEach
    void setUp() {
        mockScreen = mock(Screen.class);
        gui = new LanternaGUI(mockScreen);
    }

    private void mockKeyInput(KeyType keyType, Character character) throws IOException {
        KeyStroke keyStroke = mock(KeyStroke.class);
        when(keyStroke.getKeyType()).thenReturn(keyType);
        when(keyStroke.getCharacter()).thenReturn(character);
        when(mockScreen.pollInput()).thenReturn(keyStroke);
    }

    @Test
    void testLanternaGUI_InputHandling_ArrowLeft() throws IOException {
        mockKeyInput(KeyType.ArrowLeft, null);
        GUI.ACTION action = gui.getNextAction();
        assertEquals(GUI.ACTION.ARROW_LEFT, action, "Input 'ArrowLeft' should map to ARROW_LEFT action.");
    }

    @Test
    void testLanternaGUI_InputHandling_ArrowRight() throws IOException {
        mockKeyInput(KeyType.ArrowRight, null);
        GUI.ACTION action = gui.getNextAction();
        assertEquals(GUI.ACTION.ARROW_RIGHT, action, "Input 'ArrowRight' should map to ARROW_RIGHT action.");
    }

    @Test
    void testLanternaGUI_InputHandling_ArrowUp() throws IOException {
        mockKeyInput(KeyType.ArrowUp, null);
        GUI.ACTION action = gui.getNextAction();
        assertEquals(GUI.ACTION.ARROW_UP, action, "Input 'ArrowUp' should map to ARROW_UP action.");
    }

    @Test
    void testLanternaGUI_InputHandling_ArrowDown() throws IOException {
        mockKeyInput(KeyType.ArrowDown, null);
        GUI.ACTION action = gui.getNextAction();
        assertEquals(GUI.ACTION.ARROW_DOWN, action, "Input 'ArrowDown' should map to ARROW_DOWN action.");
    }

    @Test
    void testLanternaGUI_InputHandling_A_Key() throws IOException {
        mockKeyInput(KeyType.Character, 'A');
        GUI.ACTION action = gui.getNextAction();
        assertEquals(GUI.ACTION.A, action, "Input 'A' should map to A action.");
    }

    @Test
    void testLanternaGUI_InputHandling_D_Key() throws IOException {
        mockKeyInput(KeyType.Character, 'D');
        GUI.ACTION action = gui.getNextAction();
        assertEquals(GUI.ACTION.D, action, "Input 'D' should map to D action.");
    }

    @Test
    void testLanternaGUI_InputHandling_P_Key() throws IOException {
        mockKeyInput(KeyType.Character, 'P');
        GUI.ACTION action = gui.getNextAction();
        assertEquals(GUI.ACTION.P, action, "Input 'P' should map to P action.");
    }

    @Test
    void testLanternaGUI_InputHandling_Space() throws IOException {
        mockKeyInput(KeyType.Character, ' ');
        GUI.ACTION action = gui.getNextAction();
        assertEquals(GUI.ACTION.SPACE, action, "Input 'Space' should map to SPACE action.");
    }

    @Test
    void testLanternaGUI_InputHandling_NoInput() throws IOException {
        when(mockScreen.pollInput()).thenReturn(null);
        GUI.ACTION action = gui.getNextAction();
        assertEquals(GUI.ACTION.NONE, action, "No input should map to NONE action.");
    }
}