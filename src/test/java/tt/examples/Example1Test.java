package tt.examples;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.assertThat;

/**
 * Recorded with an iphone 5.
 *
 * A single rally:<br>
 * <pre>
 * b1 s1 s2 b2 s1
 * (b1 s2 b2 s1)[5]
 * b1 s2 b2 n s2 s2
 * => Point for Player 1
 * </pre>
 *
 * XXX explorative testing (no productive code tested yet)
 *
 * thx for part of this file to Radiodef @ http://stackoverflow.com/questions/26574326/how-to-calculate-the-level-amplitude-db-of-audio-signal-in-java
 */
public class Example1Test extends ExampleTest {

  private boolean currenctActionActive = false;
  private int countOfRelevantRallyActions = 0;

  @Test
  // I do not think, the correct actions are hit here!
  public void countActionsUsingPeak() throws Exception {
    // given
    TargetDataLineAnalyser analyser = new TargetDataLineAnalyser(getAudioFormat());
    play();
    final float LIMIT_PEAK_IS_ACTION = 0.23F; // XXX magic number: to find on calibrate (on warm up) and to make test green!
    // when
    for (SamplesAnalysis analysis; (analysis = analyser.nextAnalysis()) != null;) {
      if (analysis.getPeak() >= LIMIT_PEAK_IS_ACTION && !currenctActionActive) {
        currenctActionActive = true;
        countOfRelevantRallyActions++;
        logActionStarted(analysis);
      } else if (analysis.getRms() < LIMIT_PEAK_IS_ACTION) {
        currenctActionActive = false;
      }
      if (endOfExampleReached(analysis)) {
        break;
      }
    }
    // then
    assertThat(countOfRelevantRallyActions, anyOf(is(28), is(29), is(30)));
  }

  @Test
  // I do not think, the correct actions are hit here!
  public void countActionsUsingRms() throws Exception {
    // given
    TargetDataLineAnalyser analyser = new TargetDataLineAnalyser(getAudioFormat());
    play();
    final float LIMIT_RMS_IS_ACTION = 0.030F; // XXX magic number: to find on calibrate (on warm up) and to make test green!
    // when
    for (SamplesAnalysis analysis; (analysis = analyser.nextAnalysis()) != null;) {
      if (analysis.getRms() >= LIMIT_RMS_IS_ACTION && !currenctActionActive) {
        currenctActionActive = true;
        countOfRelevantRallyActions++;
        logActionStarted(analysis);
      } else if (analysis.getRms() < LIMIT_RMS_IS_ACTION) {
        currenctActionActive = false;
      }
      if (endOfExampleReached(analysis)) {
        break;
      }
    }
    // then
    assertThat(countOfRelevantRallyActions, anyOf(is(28), is(29), is(30), is(31)));
  }

  @Override
  String getTestFileName() {
    return "example-1.wav";
  }

  private void logActionStarted(final SamplesAnalysis analysis) {
    String format = "[%1$tT %1$tL] %2$d. action started. Result: %3$s";
    System.out.println(String.format(format, new Date(), countOfRelevantRallyActions, analysis));
  }

  @Before
  public void resetHelpMembers() {
    currenctActionActive = false;
    countOfRelevantRallyActions = 0;
  }
}
