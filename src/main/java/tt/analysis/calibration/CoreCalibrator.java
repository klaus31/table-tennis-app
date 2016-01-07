package tt.analysis.calibration;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import tt.analysis.core.Sample;
import tt.analysis.core.SamplesAnalyser;
import tt.analysis.pattern.RallyActionsPattern;
import static java.time.LocalTime.now;

public class CoreCalibrator implements Calibrator {

  private final static long TIMEOUT = 30 * 1000; // stop after 30 seconds
  // TODO to find this constants is the main task of this calibration class
  private final static float MAX_NOISE_FLOOR_PEAK = 0.01F; // any peak smaller == noise floor
  private final static float MIN_PEAK_ACTION_STARTING_WITH = 0.23F; // including noise floor
  private final SamplesAnalyser analyser;
  private final List<Sample> samplesBatA = new ArrayList<>();
  private final List<Sample> samplesSideA = new ArrayList<>();
  private final List<Sample> samplesSideB = new ArrayList<>();
  private final List<Sample> samplesBatB = new ArrayList<>();
  private final int peaksNeeded = 5; // means ba sa sb bb sa
  private final List<Sample> samplesToProbe = new ArrayList<>();
  private final List<Sample> sampleWithTopPeaks = new ArrayList<>();

  public CoreCalibrator(final SamplesAnalyser analyser) {
    this.analyser = analyser;
  }

  private void extractPattern(final List<Sample> pattern, final Sample sampleWithTopPeak) {
    int index = samplesToProbe.indexOf(sampleWithTopPeak);
    do {
      pattern.add(samplesToProbe.get(index));
      index++;
    } while (samplesToProbe.size() > index && samplesToProbe.get(index).getPeak() > MAX_NOISE_FLOOR_PEAK);
  }

  @Override
  public RallyActionsPattern getRallyActionsPattern() {
    return new RallyActionsPattern(samplesBatA, samplesBatB, samplesSideA, samplesSideB);
  }

  private boolean isMinimumRequirementForAnalysisReached() {
    return sampleWithTopPeaks.size() == peaksNeeded;
  }

  private boolean isTimeout(final LocalTime started) {
    return Duration.between(started, now()).toMillis() > TIMEOUT;
  }

  @Override
  public void listen() throws TimeoutException {
    record();
    extractPattern(samplesBatA, sampleWithTopPeaks.get(0));
    extractPattern(samplesSideA, sampleWithTopPeaks.get(1));
    extractPattern(samplesSideB, sampleWithTopPeaks.get(2));
    extractPattern(samplesBatB, sampleWithTopPeaks.get(3));
  }

  private void record() throws TimeoutException {
    reset();
    final LocalTime started = now();
    boolean currenctActionActive = false;
    for (Sample sample; (sample = analyser.nextAnalysis()) != null;) {
      samplesToProbe.add(sample);
      System.out.println(sample);
      if (sample.getPeak() >= MIN_PEAK_ACTION_STARTING_WITH && !currenctActionActive) {
        // assume, that the first sample is the loudest peak
        sampleWithTopPeaks.add(sample);
        System.out.println(sample + "<---- top  " + sampleWithTopPeaks.size());
        currenctActionActive = true;
      } else if (sample.getPeak() < MIN_PEAK_ACTION_STARTING_WITH) {
        currenctActionActive = false;
      }
      if (isMinimumRequirementForAnalysisReached()) {
        break;
      }
      if (isTimeout(started)) {
        throw new TimeoutException();
      }
    }
  }

  private void reset() {
    samplesToProbe.clear();
    sampleWithTopPeaks.clear();
    samplesSideA.clear();
    samplesBatB.clear();
    samplesBatA.clear();
    samplesSideB.clear();
  }
}
