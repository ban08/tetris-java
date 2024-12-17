package main;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.screen.Screen;
import music.Music;
import states.State;
import states.StartMenuState;
import view.GUI;
import view.LanternaGUI;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
/**
 * The main class of the program.
 * It is responsible for starting the game loop, setting up the GUI and loading the font.
 * It also creates the initial game state, which is the start menu state.
 *
 *
 *
 *
 */

public class Main {
    /**
     * The main entry point of the program.
     * Starts a new game loop.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            new Main().start(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts a new game loop.
     * This method sets up the GUI, loads the font, creates the initial game state,
     * which is the start menu state, and starts the game loop.
     * It also starts the background music and sets up the screen.
     *
     * @param args the command line arguments
     *
     * @throws Exception if an error occurs while setting up the game loop
     */
    public void start(String[] args) throws Exception {
        // Load custom font
        Font loadedFont = loadCustomFont("/fonts/crs.otf", 25f);
        AWTTerminalFontConfiguration fontConfig = AWTTerminalFontConfiguration.newInstance(loadedFont);

        TerminalSize initialSize = new TerminalSize(80, 24);
        DefaultTerminalFactory terminalFactory = createTerminalFactory(loadedFont, initialSize)
                .setInitialTerminalSize(initialSize)
                .setForceAWTOverSwing(true)
                .setTerminalEmulatorFontConfiguration(fontConfig);

        Screen screen = terminalFactory.createScreen();
        screen.startScreen();

        GUI gui = createGUI(screen);

        // Start background music
        Music music = createMusic("/music/theme.wav");
        music.runMusic();

        State state = createStartMenuState(screen, music);

        while (state != null) {
            long startTime = System.currentTimeMillis();
            state = state.step(gui, startTime);
            long elapsed = System.currentTimeMillis() - startTime;
            long sleep = 16 - elapsed;
            if (sleep > 0) Thread.sleep(sleep);
        }

        screen.close();
    }

    // Protected methods to allow mocking in tests
    /**
     * Creates a terminal factory with the given font and initial size.
     *
     * @param loadedFont the font to use for the terminal
     * @param initialSize the initial size of the terminal
     * @return the created terminal factory
     */
    protected DefaultTerminalFactory createTerminalFactory(Font loadedFont, TerminalSize initialSize) {
        return new DefaultTerminalFactory();
    }

    /**
     * Creates a GUI instance with the given screen.
     *
     * @param screen the screen to use for the GUI
     * @return the created GUI instance
     */
    protected GUI createGUI(Screen screen) {
        return new LanternaGUI(screen);
    }

    /**
     * Creates a music instance with the given resource path.
     *
     * @param resourcePath the resource path of the music file
     * @return the created music instance
     */
    protected Music createMusic(String resourcePath) throws Exception {
        return new Music(resourcePath);
    }

    /**
     * Creates a start menu state with the given screen and music.
     *
     * @param screen the screen to use for the start menu state
     * @param music the music instance to use for the start menu state
     * @return the created start menu state
     */
    protected State createStartMenuState(Screen screen, Music music) {
        return new StartMenuState(screen, music);
    }

    /**
     * Loads a custom font from the given resource path with the given size.
     *
     * @param resourcePath the resource path of the font file
     * @param size the size of the font
     * @return the loaded font
     */
    protected Font loadCustomFont(String resourcePath, float size) throws IOException, FontFormatException {
        URL fontResource = Main.class.getResource(resourcePath);
        if (fontResource == null) {
            throw new IOException("Font resource not found: " + resourcePath);
        }
        try (InputStream fontStream = fontResource.openStream()) {
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            return font.deriveFont(Font.PLAIN, size);
        }
    }
}

