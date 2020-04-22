package ooga.game.behaviors.asynceffects;

import ooga.Entity;
import ooga.game.GameInternal;

import java.util.List;
import java.util.Map;

public class RunIntoTerrainLeft extends RunIntoTerrain {
  public RunIntoTerrainLeft(List<String> args) {
    super(args);
  }

  @Override
  public void doEffect(Entity subject, Entity otherEntity, double elapsedTime, Map<String, Double> variables, GameInternal game) {
    //subject.setVelocity(0,subject.getVelocity().get(1));
    System.out.println("here2!");
    System.out.println("Subject: " + subject.getName());
    System.out.println("Width: " + otherEntity.getWidth());
    System.out.println("collidingPos " + otherEntity.getPosition().toString());
    double targetX = otherEntity.getPosition().get(0) + otherEntity.getWidth();
    double targetY = subject.getPosition().get(1);
    System.out.println(targetX);
    subject.setPosition(List.of(targetX,targetY));
    subject.blockInDirection("Left", true);
  }
}
