package ooga.game.behaviors.asynceffects;

import ooga.Entity;
import ooga.game.GameInternal;

import java.util.List;
import java.util.Map;

public class RunIntoTerrainUp extends RunIntoTerrain {
  public RunIntoTerrainUp(List<String> args) {
    super(args);
  }

  @Override
  public void doEffect(Entity subject, Entity otherEntity, double elapsedTime, Map<String, Double> variables, GameInternal game) {
    //subject.setVelocity(subject.getVelocity().get(0),0);
    double targetX = subject.getPosition().get(0);
    double targetY = otherEntity.getPosition().get(1) + otherEntity.getHeight();
    subject.setPosition(List.of(targetX,targetY));
    subject.blockInDirection("Up", true);
  }
}
