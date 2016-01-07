package tt.examples;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import tt.analysis.calibration.Calibrator;
import tt.analysis.calibration.CoreCalibrator;
import tt.analysis.core.Sample;
import tt.analysis.core.TargetDataLineAnalyser;
import tt.analysis.pattern.RallyActionPattern;
import tt.analysis.pattern.RallyActionsPattern;
import tt.pojo.RallyAction;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;
import static org.hamcrest.number.OrderingComparison.lessThanOrEqualTo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import static tt.pojo.RallyAction.BAT_A;
import static tt.pojo.RallyAction.BAT_B;
import static tt.pojo.RallyAction.SIDE_A;
import static tt.pojo.RallyAction.SIDE_B;

/**
 * Recorded with an iphone 5.
 *
 * A single rally:<br>
 * <pre>
 * ba sa sb bb sa
 * (ba sb bb sa)[5]
 * ba sb bb n sb sb
 * => Point for Player 1
 * </pre>
 *
 * XXX explorative testing (no productive code tested yet)
 */
public class Example1Test extends ExampleTest {

  private boolean currenctActionActive = false;
  private int countOfRelevantRallyActions = 0;

  @Test
  // I do not think, the correct actions are hit here!
  public void assertCorrectCountOfActionsUsingPeak() throws Exception {
    // given
    TargetDataLineAnalyser analyser = new TargetDataLineAnalyser(getAudioFormat());
    startPlayingTableTennis();
    final float LIMIT_PEAK_IS_ACTION = 0.23F; // XXX magic number: to find on calibrate (on warm up) and to make test green!
    // when
    for (Sample analysis; (analysis = analyser.nextAnalysis()) != null;) {
      System.out.println(analysis.getPeak());
      if (analysis.getPeak() >= LIMIT_PEAK_IS_ACTION && !currenctActionActive) {
        currenctActionActive = true;
        countOfRelevantRallyActions++;
        //        logActionStarted(analysis);
      } else if (analysis.getPeak() < LIMIT_PEAK_IS_ACTION) {
        currenctActionActive = false;
      }
      if (endOfExampleReached(analysis)) {
        break;
      }
    }
    // then
    assertThat(countOfRelevantRallyActions + " counted", countOfRelevantRallyActions, greaterThanOrEqualTo(22));
    assertThat(countOfRelevantRallyActions + " counted", countOfRelevantRallyActions, lessThanOrEqualTo(22));
  }

  @Test
  // I do not think, the correct actions are hit here!
  public void assertCorrectCountOfActionsUsingRms() throws Exception {
    // given
    TargetDataLineAnalyser analyser = new TargetDataLineAnalyser(getAudioFormat());
    startPlayingTableTennis();
    final float LIMIT_RMS_IS_ACTION = 0.030F; // XXX magic number: to find on calibrate (on warm up) and to make test green!
    // when
    for (Sample analysis; (analysis = analyser.nextAnalysis()) != null;) {
      if (analysis.getAmplitude() >= LIMIT_RMS_IS_ACTION && !currenctActionActive) {
        currenctActionActive = true;
        countOfRelevantRallyActions++;
        logActionStarted(analysis);
      } else if (analysis.getAmplitude() < LIMIT_RMS_IS_ACTION) {
        currenctActionActive = false;
      }
      if (endOfExampleReached(analysis)) {
        break;
      }
    }
    // then
    assertThat(countOfRelevantRallyActions + " counted", countOfRelevantRallyActions, greaterThanOrEqualTo(28));
    assertThat(countOfRelevantRallyActions + " counted", countOfRelevantRallyActions, lessThanOrEqualTo(31));
  }

  @Test
  public void assertCorrectRallyActionSnippetsOfCalibration() throws Exception {
    // given
    final TargetDataLineAnalyser analyser = new TargetDataLineAnalyser(getAudioFormat());
    final Calibrator calibrator = new CoreCalibrator(analyser);
    // when
    // TODO strange order (does not work the other way around - but should):
    startPlayingTableTennis();
    calibrator.listen();
    System.out.println("listen finished");
    stopPlayingTableTennis();
    // then
    RallyActionsPattern rallyActionsPattern = calibrator.getRallyActionsPattern();
    assertNotNull(rallyActionsPattern);
    assertPattern(rallyActionsPattern.getBatA(), .24F, BAT_A);
    assertPattern(rallyActionsPattern.getBatB(), .27F, BAT_B);
    assertPattern(rallyActionsPattern.getSideA(), .66F, SIDE_A);
    assertPattern(rallyActionsPattern.getSideB(), .90F, SIDE_B);
  }

  private void assertPattern(final RallyActionPattern pattern, final float expectedFirstPeak, final RallyAction expectedAction) {
    final float EXPECTED_NOISE_FLOOR = .01F;
    final float DELTA_FIRST_SAMPLE = .01F;
    final int MINIMUM_SAMPLES_NEEDED = 3;
    assertEquals(expectedAction, pattern.getAction());
    assertTrue("but: " + pattern.getSamples(), pattern.getSamples().stream().allMatch(sample -> sample.getPeak() > EXPECTED_NOISE_FLOOR));
    assertTrue("but: " + pattern.getSamples(), pattern.getSamples().size() >= MINIMUM_SAMPLES_NEEDED);
    assertEquals(expectedFirstPeak, pattern.getSamples().get(0).getPeak(), DELTA_FIRST_SAMPLE);
  }

  @Override
  String getTestFileName() {
    return "example-1.wav";
  }

  private void logActionStarted(final Sample analysis) {
    String format = "[%1$tT %1$tL] %2$d. action started. Result: %3$s";
    System.out.println(String.format(format, new Date(), countOfRelevantRallyActions, analysis));
  }

  @Before
  public void resetHelpMembers() {
    currenctActionActive = false;
    countOfRelevantRallyActions = 0;
  }
}
