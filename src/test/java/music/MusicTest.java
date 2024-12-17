package music;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.*;
import java.io.IOException;

import static org.mockito.Mockito.*;

class MusicTest {

    private Music music;
    private Clip mockClip;
    private FloatControl mockFloatControl;
    private AudioInputStream mockAudioInputStream;

    @BeforeEach
    void setUp() throws Exception {
        // Mock Clip and FloatControl
        mockClip = mock(Clip.class);
        mockFloatControl = mock(FloatControl.class);

        // Mock AudioInputStream
        mockAudioInputStream = mock(AudioInputStream.class);

        // Set up the Music instance
        music = new Music("/music/theme.wav", mockClip, mockAudioInputStream);

        // Mock behavior for clip control
        when(mockClip.getControl(FloatControl.Type.MASTER_GAIN)).thenReturn(mockFloatControl);
    }

    @Test
    void runMusic_ShouldPlayAndLoop() throws Exception {
        // Act
        music.runMusic();

        // Assert
        verify(mockClip, times(1)).open(mockAudioInputStream);
        verify(mockClip, times(1)).setFramePosition(0);
        verify(mockClip, times(1)).start();
        verify(mockClip, times(1)).loop(Clip.LOOP_CONTINUOUSLY);
    }

    @Test
    void play_ShouldStartClip() throws Exception {
        // Act
        music.play();

        // Assert
        verify(mockClip, times(1)).open(mockAudioInputStream);
        verify(mockClip, times(1)).setFramePosition(0);
        verify(mockClip, times(1)).start();
    }
}
