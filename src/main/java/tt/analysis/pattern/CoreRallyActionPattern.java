package tt.analysis.pattern;

import java.util.List;

import tt.analysis.core.Sample;
import tt.pojo.RallyAction;

public class CoreRallyActionPattern implements RallyActionPattern {

  private final List<Sample> samples;
  private final RallyAction action;

  public CoreRallyActionPattern(final List<Sample> patternBatA, final RallyAction action) {
    super();
    this.samples = patternBatA;
    this.action = action;
  }

  @Override
  public RallyAction getAction() {
    return action;
  }

  @Override
  public List<Sample> getSamples() {
    return samples;
  }
}
