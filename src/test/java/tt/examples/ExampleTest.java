package tt.examples;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.Test;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public abstract class ExampleTest {

  private int silentPeaks = 0;

  @Test
  public void assertExampleFileIsCorrect() {
    final AudioFormat format = getAudioFormat();
    assertNotNull(format);
    assertEquals(PCM_SIGNED, format.getEncoding());
  }

  boolean endOfExampleReached(final SamplesAnalysis analysis) {
    if (analysis.getRms() < 0.01) {
      if (silentPeaks > 100) {
        return true;
      }
      silentPeaks++;
    } else {
      silentPeaks = 0;
    }
    return false;
  }

  AudioFormat getAudioFormat() {
    try {
      return AudioSystem.getAudioFileFormat(getInputStream()).getFormat();
    } catch (UnsupportedAudioFileException | IOException e) {
      e.printStackTrace();
      fail("Have an exeption");
      return null;
    }
  }

  private InputStream getInputStream() {
    return ExampleTest.class.getResourceAsStream(getTestFileName());
  }

  abstract String getTestFileName();

  void play() {
    try {
      AudioStream audioStream = new AudioStream(getInputStream());
      AudioPlayer.player.start(audioStream);
    } catch (IOException e) {
      e.printStackTrace();
      fail("Have an exeption");
    }
  }
}
