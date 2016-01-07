package tt.analysis.pattern;

import java.util.List;

import tt.analysis.core.Sample;
import static tt.pojo.RallyAction.BAT_A;
import static tt.pojo.RallyAction.BAT_B;
import static tt.pojo.RallyAction.SIDE_A;
import static tt.pojo.RallyAction.SIDE_B;

public class RallyActionsPattern {

  private final RallyActionPattern batA, batB, sideA, sideB;

  public RallyActionsPattern(final List<Sample> patternBatA, final List<Sample> patternBatB, final List<Sample> patternSideA, final List<Sample> patternSideB) {
    super();
    this.batA = new CoreRallyActionPattern(patternBatA, BAT_A);
    this.batB = new CoreRallyActionPattern(patternBatB, BAT_B);
    this.sideA = new CoreRallyActionPattern(patternSideA, SIDE_A);
    this.sideB = new CoreRallyActionPattern(patternSideB, SIDE_B);
  }

  public RallyActionPattern getBatA() {
    return batA;
  }

  public RallyActionPattern getBatB() {
    return batB;
  }

  public RallyActionPattern getSideA() {
    return sideA;
  }

  public RallyActionPattern getSideB() {
    return sideB;
  }
}
