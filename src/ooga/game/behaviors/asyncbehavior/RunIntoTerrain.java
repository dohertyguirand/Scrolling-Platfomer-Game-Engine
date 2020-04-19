package ooga.game.behaviors.asyncbehavior;

import java.util.List;
import java.util.Map;
import ooga.game.GameInternal;
import ooga.Entity;

public class RunIntoTerrain extends QuadDirectionCollision {

  public static final double HORIZONTAL_MARGIN = 0.1;
  public static final double VERTICAL_MARGIN = 0.1;

  public RunIntoTerrain(List<String> args) {
    //arguments have no effect on this behavior
  }

  @Override
  protected void doDownwardCollision(Entity subject, Entity collidingEntity, double elapsedTime,
      Map<String, Double> variables, GameInternal game) {
    subject.setVelocity(subject.getVelocity().get(0),0);
    double targetX = subject.getPosition().get(0);
    double targetY = collidingEntity.getPosition().get(1)-subject.getHeight()- VERTICAL_MARGIN;
    subject.setPosition(List.of(targetX,targetY));
  }

  @Override
  protected void doUpwardCollision(Entity subject, Entity collidingEntity, double elapsedTime,
      Map<String, Double> variables, GameInternal game) {
    subject.setVelocity(subject.getVelocity().get(0),0);
    double targetX = subject.getPosition().get(0);
    double targetY = collidingEntity.getPosition().get(1) + collidingEntity.getHeight() + VERTICAL_MARGIN;
    subject.setPosition(List.of(targetX,targetY));
  }

  @Override
  protected void doCollisionTowardRight(Entity subject, Entity collidingEntity, double elapsedTime,
      Map<String, Double> variables, GameInternal game) {
    subject.setVelocity(0,subject.getVelocity().get(1));
    System.out.println("here!");
    System.out.println("Subject: " + subject.getName());
    System.out.println("pos " +subject.getPosition().get(0));
    System.out.println("collidingPos " + collidingEntity.getPosition().toString());
    double targetX = collidingEntity.getPosition().get(0)- subject.getWidth() - HORIZONTAL_MARGIN;
    double targetY = subject.getPosition().get(1);
    System.out.println(targetX);
    subject.setPosition(List.of(targetX,targetY));
  }

  @Override
  protected void doCollisionTowardLeft(Entity subject, Entity collidingEntity, double elapsedTime,
      Map<String, Double> variables, GameInternal game) {
    subject.setVelocity(0,subject.getVelocity().get(1));
    System.out.println("here2!");
    System.out.println("Subject: " + subject.getName());
    System.out.println("Width: " + collidingEntity.getWidth());
    System.out.println("collidingPos " + collidingEntity.getPosition().toString());
    double targetX = collidingEntity.getPosition().get(0) + collidingEntity.getWidth() + HORIZONTAL_MARGIN;
    double targetY = subject.getPosition().get(1);
    System.out.println(targetX);
    subject.setPosition(List.of(targetX,targetY));
  }

}
