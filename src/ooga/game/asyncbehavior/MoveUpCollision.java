package ooga.game.asyncbehavior;

import ooga.CollisionBehavior;
import ooga.Entity;

public class MoveUpCollision implements CollisionBehavior {

  private Entity mySubject;
  private double myMoveDistance;

  public MoveUpCollision(Entity subject, double moveDistance) {
    //TODO: Change this constructor, since behaviors no longer need to be
    // initialized with subjects.
    mySubject = subject;
    myMoveDistance = moveDistance;
  }

  @Override
  public void doCollision(Entity subject, String collidingEntity) {
    subject.move(0, myMoveDistance);
  }
}
