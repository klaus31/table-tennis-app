package tt.pojo;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class RallyTest {

  @Parameterized.Parameters
  public static Collection<CoreRally> instancesToTest() {
    return Arrays.asList(new CoreRally());
  }

  private final Rally rally;

  public RallyTest(final Rally rally) {
    this.rally = rally;
  }

  @Test
  public void assertGetActionsReturnsAnArrayInAllImplementations() {
    RallyAction[] actions = this.rally.getActions();
    assertNotNull(actions);
  }
}
