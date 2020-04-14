package ooga.game.asyncbehavior;

import ooga.Entity;
import ooga.game.DirectionlessCollision;

public class MoveUpCollision extends DirectionlessCollision {

  private double myMoveDistance;

  public MoveUpCollision(double moveDistance) {
    myMoveDistance = moveDistance;
  }

  @Override
  public void doCollision(Entity subject, Entity collidingEntity) {
    subject.move(0, myMoveDistance);
  }
}
