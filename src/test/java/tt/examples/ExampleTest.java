package tt.examples;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import org.junit.Test;

import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public abstract class ExampleTest {

  private AudioInputStream stream = null;

  @Test
  public void assertExampleFileIsCorrect() throws Exception {
    final AudioInputStream audioInputStream = getAudioInputStream();
    assertNotNull(audioInputStream);
    assertEquals(PCM_SIGNED, audioInputStream.getFormat().getEncoding());
  }

  // XXX are this really the amplitudes?!
  List<Byte> getAmplitudes() throws Exception {
    final AudioInputStream audioInputStream = getAudioInputStream();
    final List<Byte> amplitutes = new ArrayList<>();
    int sampleSize = audioInputStream.getFormat().getSampleSizeInBits();
    byte[] b = new byte[sampleSize];
    long framelength = audioInputStream.getFrameLength();
    int i = 0;
    for (i = 0; i <= framelength; i++) {
      while (audioInputStream.read(b) != -1) {
        amplitutes.add(b[i]);
      }
    }
    return amplitutes;
  }

  AudioInputStream getAudioInputStream() throws Exception {
    // XXX not thread safe
    if (stream == null) {
      final InputStream istream = ExampleTest.class.getResourceAsStream(getTestFileName());
      stream = AudioSystem.getAudioInputStream(istream);
    }
    return stream;
  }

  abstract String getTestFileName();
}
