package main;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import music.Music;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import states.StartMenuState;
import view.GUI;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class MainTest {

    private Main main;
    private DefaultTerminalFactory mockTerminalFactory;
    private TerminalScreen mockScreen; // Correctly declare as TerminalScreen
    private GUI mockGui;
    private Music mockMusic;
    private StartMenuState mockStartMenuState;

    @BeforeEach
    void setUp() throws Exception {
        // Spy the Main class
        main = spy(new Main());

        // Load a real font
        Font realFont = loadSystemFont();

        // Mock dependencies
        mockTerminalFactory = mock(DefaultTerminalFactory.class);
        mockScreen = mock(TerminalScreen.class); // Correct return type
        mockGui = mock(GUI.class);
        mockMusic = mock(Music.class);
        mockStartMenuState = mock(StartMenuState.class);

        // Stubbing methods
        doReturn(realFont).when(main).loadCustomFont(anyString(), anyFloat());
        doReturn(mockTerminalFactory).when(main).createTerminalFactory(any(Font.class), any(TerminalSize.class));

        // Stub DefaultTerminalFactory's fluent configuration
        when(mockTerminalFactory.setInitialTerminalSize(any(TerminalSize.class))).thenReturn(mockTerminalFactory);
        when(mockTerminalFactory.setForceAWTOverSwing(anyBoolean())).thenReturn(mockTerminalFactory);
        when(mockTerminalFactory.setTerminalEmulatorFontConfiguration(any())).thenReturn(mockTerminalFactory);

        // Return the TerminalScreen
        when(mockTerminalFactory.createScreen()).thenReturn(mockScreen); // Correct return type alignment

        // Stubbing the rest of the behavior
        doReturn(mockGui).when(main).createGUI(mockScreen);
        doReturn(mockMusic).when(main).createMusic(anyString());
        doReturn(mockStartMenuState).when(main).createStartMenuState(mockScreen, mockMusic);
        when(mockStartMenuState.step(any(GUI.class), anyLong())).thenReturn(null);
    }

    private Font loadSystemFont() throws Exception {
        try (InputStream fontStream = MainTest.class.getResourceAsStream("/fonts/crs.otf")) {
            if (fontStream != null) {
                return Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(25f);
            } else {
                return new Font("Arial", Font.PLAIN, 25); // Fallback to system font
            }
        }
    }

    @Test
    void start_ShouldInitializeComponentsCorrectly() throws Exception {
        // Act
        main.start(new String[]{});

        // Verify that methods were called
        verify(main).loadCustomFont("/fonts/crs.otf", 25f);
        verify(mockTerminalFactory).createScreen();
        verify(mockScreen).startScreen();
        verify(main).createMusic("/music/theme.wav");
        verify(mockMusic).runMusic();
        verify(main).createStartMenuState(mockScreen, mockMusic);
        verify(mockStartMenuState).step(eq(mockGui), anyLong()); // Fixed line
        verify(mockScreen).close();
    }


    @Test
    void start_ShouldHandleExceptionsGracefully() throws Exception {
        // Arrange: Make loadCustomFont throw an IOException
        doThrow(new IOException("Failed to load font")).when(main).loadCustomFont(anyString(), anyFloat());

        // Act & Assert
        IOException exception = assertThrows(IOException.class, () -> main.start(new String[]{}));
        verify(mockScreen, never()).startScreen();
        verify(mockScreen, never()).close();
    }
}
