package tt.calibration;

import java.time.Duration;
import java.time.LocalTime;

import tt.analysis.core.SamplesAnalyser;
import tt.analysis.core.SamplesAnalysis;
import tt.analysis.pattern.RallyActionsPattern;
import static java.time.LocalTime.now;

public class CoreCalibrator implements Calibrator {

  private static final long TIMEOUT = 30 * 1000; // stop after 30 seconds
  private final SamplesAnalyser analyser;
  private byte[] patternSideA;
  private byte[] patternBatB;
  private byte[] patternBatA;
  private byte[] patternSideB;

  public CoreCalibrator(final SamplesAnalyser analyser) {
    this.analyser = analyser;
  }

  private boolean calibrationFinished() {
    // TODO check, if s1, s2, b1, b2 found
    return false;
  }

  @Override
  public RallyActionsPattern getRallyActionsPattern() {
    return new RallyActionsPattern(patternBatA, patternBatB, patternSideA, patternSideB);
  }

  @Override
  public void listen() {
    LocalTime started = now();
    for (SamplesAnalysis analysis; (analysis = analyser.nextAnalysis()) != null;) {
      // TODO identify s1, s2, b1, b2
      if (calibrationFinished() || Duration.between(started, now()).toMillis() > TIMEOUT) {
        break;
      }
    }
  }
}
