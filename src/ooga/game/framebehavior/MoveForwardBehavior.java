package ooga.game.framebehavior;

import ooga.Entity;
import ooga.MovementBehavior;

public class MoveForwardBehavior implements MovementBehavior {


  private Entity myEntity;
  private double xMovePerFrame;
  private double yMovePerFrame;

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
