package tt.analysis.core;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

/**
 * thx for part of this file to Radiodef @ http://stackoverflow.com/questions/26574326/how-to-calculate-the-level-amplitude-db-of-audio-signal-in-java
 */
public class TargetDataLineAnalyser implements SamplesAnalyser {

  private final int bufferByteSize = 1024;
  private final TargetDataLine line;
  private final byte[] buffer = new byte[bufferByteSize];
  private int b = 0;

  public TargetDataLineAnalyser(final AudioFormat format) throws LineUnavailableException {
    line = AudioSystem.getTargetDataLine(format);
    line.open(format, bufferByteSize);
    line.start();
  }

  // XXX the very basic all important method of the entire shit and i do not understand it. :D
  private float[] getSamples() {
    float[] samples = new float[bufferByteSize / 2];
    for (int i = 0, s = 0; i < b;) {
      int sample = 0;
      sample |= buffer[i++] & 0xFF;
      sample |= buffer[i++] << 8;
      // TODO lets work with integer values - we do not need a relation to 0 and 1!
      samples[s++] = sample / 32768f; // XXX magic number
    }
    return samples;
  }

  @Override
  public Sample nextAnalysis() {
    if ((b = line.read(buffer, 0, buffer.length)) > -1) {
      return new Sample(getSamples());
    } else {
      return null;
    }
  }
}
