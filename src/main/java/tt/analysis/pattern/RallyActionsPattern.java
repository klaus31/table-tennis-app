package tt.analysis.pattern;

import static tt.pojo.RallyAction.BAT_A;
import static tt.pojo.RallyAction.BAT_B;
import static tt.pojo.RallyAction.SIDE_A;
import static tt.pojo.RallyAction.SIDE_B;

public class RallyActionsPattern {

  private final RallyActionPattern batA, batB, sideA, sideB;

  public RallyActionsPattern(final byte[] patternBatA, final byte[] patternBatB, final byte[] patternSideA, final byte[] patternSideB) {
    super();
    this.batA = new CoreRallyActionPattern(patternBatA, BAT_A);
    this.batB = new CoreRallyActionPattern(patternBatB, BAT_B);
    this.sideA = new CoreRallyActionPattern(patternSideA, SIDE_A);
    this.sideB = new CoreRallyActionPattern(patternSideB, SIDE_B);
  }
}
