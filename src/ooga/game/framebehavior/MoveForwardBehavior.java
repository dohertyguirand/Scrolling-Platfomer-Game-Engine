package ooga.game.framebehavior;

import ooga.EntityAPI;
import ooga.MovementBehavior;

public class MoveForwardBehavior implements MovementBehavior {

  public static final double X_MOVE_PER_SECOND = 10;
  public static final double Y_MOVE_PER_SECOND = 0;
  private EntityAPI myEntity;

  public MoveForwardBehavior(EntityAPI e) {
    setTarget(e);
  }

  public MoveForwardBehavior() {
    this(null);
  }

  @Override
  public void setTarget(EntityAPI e) {
    myEntity = e;
  }

  @Override
  public void doMovementUpdate(double elapsedTime) {
    myEntity.move(X_MOVE_PER_SECOND,Y_MOVE_PER_SECOND);
  }
}
