package tt.analysis.pattern;

import java.util.List;

import tt.analysis.core.Sample;
import tt.pojo.RallyAction;

/**
 * Pattern representing a {@link RallyAction}
 */
public interface RallyActionPattern {

  RallyAction getAction();

  List<Sample> getSamples();
}
