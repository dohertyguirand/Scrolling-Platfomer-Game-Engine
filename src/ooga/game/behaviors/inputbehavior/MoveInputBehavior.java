package ooga.game.behaviors.inputbehavior;

import java.util.List;
import ooga.game.behaviors.ControlsBehavior;
import ooga.Entity;

public class MoveInputBehavior implements ControlsBehavior {

  private double xMovePerFrame;
  private double yMovePerFrame;

  public MoveInputBehavior(List<String> args) {
    xMovePerFrame = Double.parseDouble(args.get(0));
    yMovePerFrame = Double.parseDouble(args.get(1));
  }

  public MoveInputBehavior(double xMove, double yMove) {
    xMovePerFrame = xMove;
    yMovePerFrame = yMove;
  }

  public MoveInputBehavior() {
    this(0,0);
  }

  @Override
  public void reactToControls(Entity subject) {
    subject.move(xMovePerFrame,yMovePerFrame);
  }
}
