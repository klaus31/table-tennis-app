package tt.analysis.calibration;

import java.util.concurrent.TimeoutException;

import tt.analysis.pattern.RallyActionsPattern;

public interface Calibrator {

  RallyActionsPattern getRallyActionsPattern();

  void listen() throws TimeoutException;
}
