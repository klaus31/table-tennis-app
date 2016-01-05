package tt.examples;

import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class Example1Test extends ExampleTest {

  @Test
  public void assertHasAmplitudes() throws Exception {
    List<Byte> amplitudes = getAmplitudes();
    assertTrue(amplitudes.size() > 0);
  }

  @Override
  String getTestFileName() {
    return "example-1.wav";
  }
}
