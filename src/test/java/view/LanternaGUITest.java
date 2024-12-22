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
        if (character != null) {
            when(keyStroke.getCharacter()).thenReturn(character);
        }
        when(mockScreen.pollInput()).thenReturn(keyStroke);
    }

    @Test
    void testNoInputReturnsNone() throws IOException {
        when(mockScreen.pollInput()).thenReturn(null);
        assertEquals(GUI.ACTION.NONE, gui.getNextAction());
    }

    @Test
    void testEOFReturnsNone() throws IOException {
        mockKeyInput(KeyType.EOF, null);
        assertEquals(GUI.ACTION.NONE, gui.getNextAction());
    }

    @Test
    void testArrowUp() throws IOException {
        mockKeyInput(KeyType.ArrowUp, null);
        assertEquals(GUI.ACTION.ARROW_UP, gui.getNextAction());
    }

    @Test
    void testArrowDown() throws IOException {
        mockKeyInput(KeyType.ArrowDown, null);
        assertEquals(GUI.ACTION.ARROW_DOWN, gui.getNextAction());
    }

    @Test
    void testArrowLeft() throws IOException {
        mockKeyInput(KeyType.ArrowLeft, null);
        assertEquals(GUI.ACTION.ARROW_LEFT, gui.getNextAction());
    }

    @Test
    void testArrowRight() throws IOException {
        mockKeyInput(KeyType.ArrowRight, null);
        assertEquals(GUI.ACTION.ARROW_RIGHT, gui.getNextAction());
    }

    @Test
    void testEscape() throws IOException {
        mockKeyInput(KeyType.Escape, null);
        assertEquals(GUI.ACTION.ESCAPE, gui.getNextAction());
    }

    @Test
    void testEnter() throws IOException {
        mockKeyInput(KeyType.Enter, null);
        assertEquals(GUI.ACTION.SELECT, gui.getNextAction());
    }

    @Test
    void testCharacterSpace() throws IOException {
        mockKeyInput(KeyType.Character, ' ');
        assertEquals(GUI.ACTION.SPACE, gui.getNextAction());
    }

    @Test
    void testCharacterA() throws IOException {
        mockKeyInput(KeyType.Character, 'A');
        assertEquals(GUI.ACTION.A, gui.getNextAction());
    }

    @Test
    void testCharacterD() throws IOException {
        mockKeyInput(KeyType.Character, 'D');
        assertEquals(GUI.ACTION.D, gui.getNextAction());
    }

    @Test
    void testCharacterP() throws IOException {
        mockKeyInput(KeyType.Character, 'P');
        assertEquals(GUI.ACTION.P, gui.getNextAction());
    }

    @Test
    void testCharacterS() throws IOException {
        mockKeyInput(KeyType.Character, 'S');
        assertEquals(GUI.ACTION.S_KEY, gui.getNextAction());
    }

    @Test
    void testCharacterE() throws IOException {
        mockKeyInput(KeyType.Character, 'E');
        assertEquals(GUI.ACTION.E_KEY, gui.getNextAction());
    }

    @Test
    void testCharacterR() throws IOException {
        mockKeyInput(KeyType.Character, 'R');
        assertEquals(GUI.ACTION.R_KEY, gui.getNextAction());
    }

    @Test
    void testCharacterQ() throws IOException {
        mockKeyInput(KeyType.Character, 'Q');
        assertEquals(GUI.ACTION.Q_KEY, gui.getNextAction());
    }

    @Test
    void testCharacterB() throws IOException {
        mockKeyInput(KeyType.Character, 'b');
        assertEquals(GUI.ACTION.B_KEY, gui.getNextAction());
    }

    @Test
    void testUnknownCharacter() throws IOException {
        mockKeyInput(KeyType.Character, 'Z');
        assertEquals(GUI.ACTION.NONE, gui.getNextAction());
    }

    @Test
    void testClear() {
        gui.clear();
        verify(mockScreen, times(1)).clear();
    }

    @Test
    void testRefresh() throws IOException {
        gui.refresh();
        verify(mockScreen, times(1)).refresh();
    }

    @Test
    void testClose() throws IOException {
        gui.close();
        verify(mockScreen, times(1)).close();
    }
}
