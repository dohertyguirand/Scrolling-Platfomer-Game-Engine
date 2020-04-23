package ooga.game.behaviors.collisioneffects;

import ooga.Entity;
import ooga.game.GameInternal;

import java.util.List;
import java.util.Map;

public class RunIntoTerrainRightEffect extends RunIntoTerrainEffect {
  public RunIntoTerrainRightEffect(List<String> args) {
    super(args);
  }

  /**
   * Performs the effect
   *
   * @param subject     The entity that owns this. This is the entity that should be modified.
   * @param otherEntity entity we are "interacting with" in this effect
   * @param elapsedTime time between steps in ms
   * @param variables   game variables
   * @param game        game instance
   */
  @Override
  protected void doTimeDelayedEffect(Entity subject, Entity otherEntity, double elapsedTime, Map<String, Double> variables, GameInternal game) {
    double targetX = otherEntity.getPosition().get(0)- subject.getWidth();
    double targetY = subject.getPosition().get(1);
    subject.setPosition(List.of(targetX,targetY));
    //TODO: make block in directions entity variables?
    subject.blockInDirection("Right", true);
  }
}
