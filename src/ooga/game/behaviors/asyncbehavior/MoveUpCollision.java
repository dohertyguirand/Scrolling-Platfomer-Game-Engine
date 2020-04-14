package ooga.game.behaviors.asyncbehavior;

import java.util.List;
import ooga.Entity;
import ooga.game.DirectionlessCollision;

public class MoveUpCollision extends DirectionlessCollision {

  private double myMoveDistance;

  public MoveUpCollision(List<String> args) {
    myMoveDistance = Double.parseDouble(args.get(0));
  }

  public MoveUpCollision(double moveDistance) {
    myMoveDistance = moveDistance;
  }

  @Override
  public void doCollision(Entity subject, Entity collidingEntity) {
    subject.move(0, myMoveDistance);
  }
}
