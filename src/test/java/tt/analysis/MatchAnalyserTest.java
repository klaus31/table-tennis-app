package tt.analysis;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class MatchAnalyserTest {

  @Parameterized.Parameters
  public static Collection<MatchAnalyser> instancesToTest() {
    return Arrays.asList(new CoreMatchAnalyser());
  }

  private final MatchAnalyser analyser;

  public MatchAnalyserTest(final MatchAnalyser analyser) {
    this.analyser = analyser;
  }

  @Test
  public void assertGetActionsReturnsAnArrayInAllImplementations() {
    Assert.assertNotNull(analyser); // TODO give it sense: testing test code yet (instancesToTest)
  }
}
