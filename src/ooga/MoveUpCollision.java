package ooga;

import ooga.CollisionBehavior;
import ooga.Entity;

public class MoveUpCollision implements CollisionBehavior {

  private Entity mySubject;
  private double myMoveDisance;

  public MoveUpCollision(Entity subject, double moveDistance) {
    mySubject = subject;
    myMoveDisance = moveDistance;
  }

  @Override
  public void doCollision(String collidingEntity) {
    mySubject.move(0,myMoveDisance);
  }
}
