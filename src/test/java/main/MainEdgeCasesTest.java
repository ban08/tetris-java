package main;

import com.googlecode.lanterna.screen.Screen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.GUI;
import music.Music;
import states.State;

import java.awt.*;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MainEdgeCasesTest {

    private Main main;
    private Screen mockScreen;
    private GUI mockGui;
    private Music mockMusic;
    private State mockState;

    @BeforeEach
    void setUp() {
        main = spy(new Main());
        mockScreen = mock(Screen.class);
        mockGui = mock(GUI.class);
        mockMusic = mock(Music.class);
        mockState = mock(State.class);
    }

    @Test
    void testLoadCustomFont_IOExceptionCase() throws Exception {
        // Suppose we pass a resource path that doesn't exist
        assertThrows(IOException.class,
                () -> main.loadCustomFont("/invalid/path.ttf", 20f),
                "Should throw IOException if font resource does not exist."
        );
    }

    @Test
    void testStartMusicFailure() throws Exception {
        // Force an Exception from createMusic
        doThrow(new IOException("Music load error")).when(main).createMusic(anyString());

        // We expect start to bubble an Exception or handle it
        assertThrows(IOException.class, () -> main.start(new String[]{}));
    }
}
