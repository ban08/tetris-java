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

public class Main {
    public static void main(String[] args) {
        try {
            new Main().start(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
    protected DefaultTerminalFactory createTerminalFactory(Font loadedFont, TerminalSize initialSize) {
        return new DefaultTerminalFactory();
    }

    protected GUI createGUI(Screen screen) {
        return new LanternaGUI(screen);
    }

    protected Music createMusic(String resourcePath) throws Exception {
        return new Music(resourcePath);
    }

    protected State createStartMenuState(Screen screen, Music music) {
        return new StartMenuState(screen, music);
    }

    // Changed from private to protected to allow mocking
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
