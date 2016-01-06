package tt.calibration;

import tt.analysis.pattern.RallyActionsPattern;

public interface Calibrator {

  RallyActionsPattern getRallyActionsPattern();

  void listen();
}
