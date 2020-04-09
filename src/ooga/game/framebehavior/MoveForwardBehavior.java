package ooga.game.framebehavior;

import ooga.EntityAPI;
import ooga.MovementBehavior;

public class MoveForwardBehavior implements MovementBehavior {


  private EntityAPI myEntity;
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
  public void doMovementUpdate(double elapsedTime, EntityAPI subject) {
    subject.move(xMovePerFrame * elapsedTime,yMovePerFrame * elapsedTime);
  }
}
