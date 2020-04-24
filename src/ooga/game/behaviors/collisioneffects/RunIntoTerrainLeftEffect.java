package ooga.game.behaviors.collisioneffects;

import ooga.Entity;
import ooga.game.GameInternal;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class RunIntoTerrainLeftEffect extends RunIntoTerrainEffect {
  public RunIntoTerrainLeftEffect(List<String> args) {
    super(args);
  }

  @Override
  public void processArgs(List<String> args) {
    //has no arguments
  }

  /**
   * Performs the effect
   *  @param subject     The entity that owns this. This is the entity that should be modified.
   * @param otherEntity entity we are "interacting with" in this effect
   * @param elapsedTime time between steps in ms
   * @param variables   game variables
   * @param game        game instance
   */
  @Override
  protected void doTimeDelayedEffect(Entity subject, Entity otherEntity, double elapsedTime, Map<String, String> variables, GameInternal game) {
    double targetX = otherEntity.getPosition().get(0) + otherEntity.getWidth();
    double targetY = subject.getPosition().get(1);
    subject.setPosition(List.of(targetX,targetY));
    subject.blockInDirection("Left", true);
  }
}
