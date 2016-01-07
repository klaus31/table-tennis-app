package tt.analysis.core;

public class Sample {

  private final float[] subSamples;

  public Sample(final float[] subSamples) {
    this.subSamples = subSamples;
  }

  /**
   * @link https://en.wikipedia.org/wiki/Audio_power
   * @link https://en.wikipedia.org/wiki/Root_mean_square
   */
  public float getAmplitude() {
    float rms = 0f;
    for (float sample : subSamples) {
      rms += sample * sample;
    }
    return (float) Math.sqrt(rms / subSamples.length);
  }

  public float getPeak() {
    float peak = 0f;
    for (float sample : subSamples) {
      float abs = Math.abs(sample);
      if (abs > peak) {
        peak = abs;
      }
    }
    return peak;
  }

  @Override
  public String toString() {
    String format = "{amplitude:%s, peak:%s}";
    return String.format(format, getAmplitude(), getPeak());
  }
}
