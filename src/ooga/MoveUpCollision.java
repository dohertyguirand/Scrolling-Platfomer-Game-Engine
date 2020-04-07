package ooga;

public class MoveUpCollision implements CollisionBehavior {

  private Entity mySubject;
  private double myMoveDistance;

  public MoveUpCollision(Entity subject, double moveDistance) {
    mySubject = subject;
    myMoveDistance = moveDistance;
  }

  @Override
  public void doCollision(String collidingEntity) {
    mySubject.move(0, myMoveDistance);
  }
}
