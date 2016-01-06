package tt.analysis.pattern;

import tt.pojo.RallyAction;

public class CoreRallyActionPattern implements RallyActionPattern {

  private final byte[] patternBatA;
  private final RallyAction batA;

  public CoreRallyActionPattern(final byte[] patternBatA, final RallyAction batA) {
    super();
    this.patternBatA = patternBatA;
    this.batA = batA;
  }
}
