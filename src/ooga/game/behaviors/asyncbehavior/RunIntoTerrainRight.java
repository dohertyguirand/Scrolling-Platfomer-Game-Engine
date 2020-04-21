package ooga.game.behaviors.asyncbehavior;

import ooga.Entity;
import ooga.game.GameInternal;

import java.util.List;
import java.util.Map;

public class RunIntoTerrainRight extends RunIntoTerrain {
  public RunIntoTerrainRight(List<String> args) {
    super(args);
  }

  @Override
  public void doEffect(Entity subject, Entity otherEntity, double elapsedTime, Map<String, Double> variables, GameInternal game) {
    //subject.setVelocity(0,subject.getVelocity().get(1));
    System.out.println("here!");
    System.out.println("Subject: " + subject.getName());
    System.out.println("pos " +subject.getPosition().get(0));
    System.out.println("collidingPos " + otherEntity.getPosition().toString());
    double targetX = otherEntity.getPosition().get(0)- subject.getWidth();
    double targetY = subject.getPosition().get(1);
    System.out.println(targetX);
    subject.setPosition(List.of(targetX,targetY));
    //TODO: make block in directions entity variables?
    subject.blockInDirection("Right", true);
  }
}
