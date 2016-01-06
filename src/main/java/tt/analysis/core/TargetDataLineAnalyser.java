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
  private final byte[] buf = new byte[bufferByteSize];
  private int b = 0;

  public TargetDataLineAnalyser(final AudioFormat format) throws LineUnavailableException {
    line = AudioSystem.getTargetDataLine(format);
    line.open(format, bufferByteSize);
    line.start();
  }

  private float getPeak(final float[] samples) {
    float peak = 0f;
    for (float sample : samples) {
      float abs = Math.abs(sample);
      if (abs > peak) {
        peak = abs;
      }
    }
    return peak;
  }

  /**
   * @link https://en.wikipedia.org/wiki/Audio_power
   * @link https://en.wikipedia.org/wiki/Root_mean_square
   */
  private float getRms(final float[] samples) {
    float rms = 0f;
    for (float sample : samples) {
      rms += sample * sample;
    }
    return (float) Math.sqrt(rms / samples.length);
  }

  private float[] getSamples() {
    float[] samples = new float[bufferByteSize / 2];
    for (int i = 0, s = 0; i < b;) {
      int sample = 0;
      sample |= buf[i++] & 0xFF;
      sample |= buf[i++] << 8;
      samples[s++] = sample / 32768f; // XXX magic number
    }
    return samples;
  }

  public SamplesAnalysis nextAnalysis() {
    if ((b = line.read(buf, 0, buf.length)) > -1) {
      return new SamplesAnalysis(getRms(getSamples()), getPeak(getSamples()));
    } else {
      return null;
    }
  }
}
