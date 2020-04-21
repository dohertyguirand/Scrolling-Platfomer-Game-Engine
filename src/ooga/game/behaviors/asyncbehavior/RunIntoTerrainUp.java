package ooga.game.behaviors.asyncbehavior;

import ooga.Entity;
import ooga.game.GameInternal;

import java.util.List;
import java.util.Map;

public class RunIntoTerrainUp extends RunIntoTerrain {
  public RunIntoTerrainUp(List<String> args) {
    super(args);
  }

  @Override
  public void doCollision(Entity subject, Entity collidingEntity, double elapsedTime, Map<String, Double> variables, GameInternal game) {
    //subject.setVelocity(subject.getVelocity().get(0),0);
    double targetX = subject.getPosition().get(0);
    double targetY = collidingEntity.getPosition().get(1) + collidingEntity.getHeight();
    subject.setPosition(List.of(targetX,targetY));
    subject.blockInDirection("Up", true);
  }
}
