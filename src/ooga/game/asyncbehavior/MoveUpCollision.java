package ooga.game.asyncbehavior;

import ooga.CollisionBehavior;
import ooga.EntityAPI;

public class MoveUpCollision implements CollisionBehavior {

  private EntityAPI mySubject;
  private double myMoveDistance;

  public MoveUpCollision(EntityAPI subject, double moveDistance) {
    //TODO: Change this constructor, since behaviors no longer need to be
    // initialized with subjects.
    mySubject = subject;
    myMoveDistance = moveDistance;
  }

  @Override
  public void doCollision(EntityAPI subject, String collidingEntity) {
    subject.move(0, myMoveDistance);
  }
}
