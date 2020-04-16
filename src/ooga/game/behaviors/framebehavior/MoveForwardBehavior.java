package ooga.game.behaviors.framebehavior;

import java.util.List;
import ooga.Entity;
import ooga.game.behaviors.MovementBehavior;

public class MoveForwardBehavior implements MovementBehavior {


  private Entity myEntity;
  private double xMovePerFrame;
  private double yMovePerFrame;

  public MoveForwardBehavior(List<String> args) {
    xMovePerFrame = Double.parseDouble(args.get(0));
    yMovePerFrame = Double.parseDouble(args.get(1));
  }

  public MoveForwardBehavior(double xmove, double ymove) {
    xMovePerFrame = xmove;
    yMovePerFrame = ymove;
  }

  public MoveForwardBehavior() {
    this(0,0);
  }

  @Override
  public void doMovementUpdate(double elapsedTime, Entity subject) {
    subject.move(xMovePerFrame * elapsedTime,yMovePerFrame * elapsedTime);
  }
}
