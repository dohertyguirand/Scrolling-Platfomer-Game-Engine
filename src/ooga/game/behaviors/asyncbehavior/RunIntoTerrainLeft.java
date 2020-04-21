package ooga.game.behaviors.asyncbehavior;

import ooga.Entity;
import ooga.game.GameInternal;

import java.util.List;
import java.util.Map;

public class RunIntoTerrainLeft extends RunIntoTerrain {
  public RunIntoTerrainLeft(List<String> args) {
    super(args);
  }

  @Override
  public void doCollision(Entity subject, Entity collidingEntity, double elapsedTime, Map<String, Double> variables, GameInternal game) {
    //subject.setVelocity(0,subject.getVelocity().get(1));
    System.out.println("here2!");
    System.out.println("Subject: " + subject.getName());
    System.out.println("Width: " + collidingEntity.getWidth());
    System.out.println("collidingPos " + collidingEntity.getPosition().toString());
    double targetX = collidingEntity.getPosition().get(0) + collidingEntity.getWidth();
    double targetY = subject.getPosition().get(1);
    System.out.println(targetX);
    subject.setPosition(List.of(targetX,targetY));
    subject.blockInDirection("Left", true);
  }
}
