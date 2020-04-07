package ooga;

public class MoveForwardBehavior implements MovementBehavior {

  public static final double X_MOVE_PER_SECOND = 10;
  public static final double Y_MOVE_PER_SECOND = 0;
  private Entity myEntity;

  public MoveForwardBehavior(Entity e) {
    setTarget(e);
  }

  public MoveForwardBehavior() {
    this(null);
  }

  @Override
  public void setTarget(Entity e) {
    myEntity = e;
  }

  @Override
  public void doMovementUpdate(double elapsedTime) {
    myEntity.move(X_MOVE_PER_SECOND,Y_MOVE_PER_SECOND);
  }
}
