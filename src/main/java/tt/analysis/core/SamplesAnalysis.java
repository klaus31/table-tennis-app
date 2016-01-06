package tt.analysis.core;

public class SamplesAnalysis {

  private final float rms;
  private final float peak;

  public SamplesAnalysis(final float rms, final float peak) {
    this.rms = rms;
    this.peak = peak;
  }

  public float getPeak() {
    return peak;
  }

  public float getRms() {
    return rms;
  }

  @Override
  public String toString() {
    String format = "{rms:%s, peak:%s}";
    return String.format(format, rms, peak);
  }
}
