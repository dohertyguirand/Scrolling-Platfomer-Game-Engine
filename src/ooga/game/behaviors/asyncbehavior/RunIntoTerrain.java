package ooga.game.behaviors.asyncbehavior;

import java.util.List;
import java.util.Map;
import ooga.game.GameInternal;
import ooga.game.behaviors.CollisionBehavior;
import ooga.Entity;

public class RunIntoTerrain implements CollisionBehavior {

  public static final double MARGIN = 0.1;

  public RunIntoTerrain(List<String> args) {
    //arguments have no effect on this behavior
  }

  //TODO: Consider making collisions even richer, by having four methods (one for each direction)
  @Override
  public void doVerticalCollision(Entity subject, Entity collidingEntity, double elapsedTime,
      Map<String, Double> variables, GameInternal game) {
    subject.setVelocity(subject.getVelocity().get(0),0);
    double direction = Math.signum(subject.getVelocity().get(1));
    if (direction < 0) {
      doUpwardCollision(subject,collidingEntity,elapsedTime,variables);
    }
    else {
      doDownwardCollision(subject,collidingEntity,elapsedTime,variables);
    }
  }

  private void doDownwardCollision(Entity subject, Entity collidingEntity, double elapsedTime, Map<String,Double> variables) {
    double targetX = subject.getPosition().get(0);
    double targetY = collidingEntity.getPosition().get(1)-subject.getHeight()-MARGIN;
    subject.setPosition(List.of(targetX,targetY));
  }

  private void doUpwardCollision(Entity subject, Entity collidingEntity, double elapsedTime, Map<String,Double> variables) {
    double targetX = subject.getPosition().get(0);
    double targetY = collidingEntity.getPosition().get(1) + collidingEntity.getHeight() + MARGIN;
    subject.setPosition(List.of(targetX,targetY));
  }

  @Override
  public void doHorizontalCollision(Entity subject, Entity collidingEntity, double elapsedTime,
      Map<String, Double> variables, GameInternal game) {
    double direction = Math.signum(subject.getVelocity().get(0));
    subject.setVelocity(0,subject.getVelocity().get(1));
    System.out.println("direction = " + direction);
    if (direction < 0) {
      doCollisionTowardLeft(subject,collidingEntity,elapsedTime,variables);
    }
    else {
      doCollisionTowardRight(subject,collidingEntity,elapsedTime,variables);
    }
  }

  private void doCollisionTowardRight(Entity subject, Entity collidingEntity, double elapsedTime, Map<String,Double> variables) {
    double targetX = collidingEntity.getPosition().get(0)- subject.getWidth() - MARGIN;
    double targetY = subject.getPosition().get(1);
    subject.setPosition(List.of(targetX,targetY));
  }

  private void doCollisionTowardLeft(Entity subject, Entity collidingEntity, double elapsedTime, Map<String,Double> variables) {
    System.out.println("COLLIDING TOWARD LEFT");
    double targetX = collidingEntity.getPosition().get(0) + collidingEntity.getWidth() + MARGIN;
    double targetY = subject.getPosition().get(1);
    subject.setPosition(List.of(targetX,targetY));
  }

}
