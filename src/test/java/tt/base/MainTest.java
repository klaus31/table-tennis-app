package tt.base;

import org.junit.Test;

import static java.util.Arrays.stream;

import static org.junit.Assert.assertTrue;

public class MainTest {

  @Test
  public void assertHasMainMethod() {
    assertTrue(stream(Main.class.getMethods()).anyMatch(m -> m.toString().equals("public static void tt.base.Main.main(java.lang.String[])")));
  }
}
