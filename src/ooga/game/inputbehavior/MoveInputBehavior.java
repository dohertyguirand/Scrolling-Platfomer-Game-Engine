package ooga.game.inputbehavior;

import ooga.ControlsBehavior;
import ooga.Entity;
import ooga.MovementBehavior;

public class MoveInputBehavior implements ControlsBehavior {


  private Entity myEntity;
  private double xMovePerFrame;
  private double yMovePerFrame;

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
