package tt.examples;

import org.junit.Ignore;
import org.junit.Test;

import tt.analysis.CoreMatchAnalyser;
import tt.analysis.MatchAnalyser;

/**
 * Recorded with an iphone 5.
 *
 * An ace (http://tabletennis.about.com/od/glossary/g/ace.htm):<br>
 * <pre>
 * b1 s1 s2
 * </pre>
 *
 * same shit as example 1, but only the serve (without a touch from the receiver)
 */
public class Example2Test extends ExampleTest {

  @Test
  @Ignore
  // TODO not implemented yet, because it is the wrong abstraction layer not concerning directly the feasibility analysis
  public void assertCorrectMatchAnalysis() {
    // given
    // TODO mock callibration
    MatchAnalyser analyser = new CoreMatchAnalyser();
    // TODO register listener
    startPlayingTableTennis();
    // then
    // TODO check, if analyser fired rally finished
    // TODO check, if actions of rally finished are b1 s1 s2
  }

  @Override
  String getTestFileName() {
    return "example-2.wav";
  }
}
