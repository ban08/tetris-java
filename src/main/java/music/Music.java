package music;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
/**
 * A class responsible for playing music.
 * <p>
 * This class is designed to load and play audio files from the classpath.
 *
 *
 */
public class Music {
    private Clip clip;
    private float currentVolume;
    private float previousVolume;
    private FloatControl fc;
    private boolean muted;

    /**
     * Creates a new instance of the Music class.
     * @param resourcePath the path to the audio file
     */
    public Music(String resourcePath) {
        try {
            muted = false;
            currentVolume = 0;
            previousVolume = 0;

            // Load from classpath resource:
            URL audioURL = Music.class.getResource(resourcePath);
            if (audioURL == null) {
                throw new IOException("Audio resource not found: " + resourcePath);
            }

            AudioInputStream inputStream = AudioSystem.getAudioInputStream(audioURL);
            clip = AudioSystem.getClip();
            clip.open(inputStream);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Refactored constructor for testing
    public Music(String resourcePath, Clip clip, AudioInputStream audioInputStream) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        this.muted = false;
        this.currentVolume = 0;
        this.previousVolume = 0;
        this.clip = clip;
        this.clip.open(audioInputStream);
        this.fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    }

    /**
     * Checks if the music is muted.
     * @return true if the music is muted, false otherwise.
     */
    public boolean isMuted() {
        return muted;
    }

    /**
     * Starts playing the music.
     */
    public void runMusic() {
        play();
        loop();
    }

    /**
     * Starts playing the music from the beginning.
     */
    public void play() {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }

    /**
     * Loops the music continuously.
     */
    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /**
     * Increases the volume of the music.
     */
    public void volumeUp() {
        if (!isMuted()) {
            currentVolume += 5.0f;
            if (currentVolume > 6.0f) currentVolume = 6.0f;
            fc.setValue(currentVolume);
        }
    }

    /**
     * Decreases the volume of the music.
     */
    public void volumeDown() {
        if (!isMuted()) {
            currentVolume -= 5.0f;
            if (currentVolume < -80.0f) currentVolume = -80.0f;
            fc.setValue(currentVolume);
        }
    }

    /**
     * Toggles the mute state of the music.
     */
    public void volumeMute() {
        if (!isMuted()) {
            previousVolume = currentVolume;
            currentVolume = -80.0f;
            fc.setValue(currentVolume);
            muted = true;
        } else {
            currentVolume = previousVolume;
            fc.setValue(currentVolume);
            muted = false;
        }
    }
}