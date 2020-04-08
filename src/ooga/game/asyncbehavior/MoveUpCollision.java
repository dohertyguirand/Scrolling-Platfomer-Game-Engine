package ooga.game.asyncbehavior;

import ooga.CollisionBehavior;
import ooga.EntityAPI;

public class MoveUpCollision implements CollisionBehavior {

  private EntityAPI mySubject;
  private double myMoveDistance;

  public MoveUpCollision(EntityAPI subject, double moveDistance) {
    mySubject = subject;
    myMoveDistance = moveDistance;
  }

  @Override
  public void doCollision(String collidingEntity) {
    mySubject.move(0, myMoveDistance);
  }
}
