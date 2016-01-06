package tt.pojo;

public class CoreRally implements Rally {

  @Override
  public RallyAction[] getActions() {
    return new RallyAction[] { RallyAction.BAT_A }; // TODO not implemented yet
  }
}
