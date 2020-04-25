package ooga.game.behaviors.collisioneffects;

import ooga.game.EntityInternal;
import ooga.game.GameInternal;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class RunIntoTerrainUpEffect extends RunIntoTerrainEffect {
  public RunIntoTerrainUpEffect(List<String> args) {
    super(args);
  }

  @Override
  public void processArgs(List<String> args) {
    //has no arguments
  }

  /**
   * Performs the effect
   * @param subject     The entity that owns this. This is the entity that should be modified.
   * @param otherEntity entity we are "interacting with" in this effect
   * @param elapsedTime time between steps in ms
   * @param variables   game variables
   * @param game        game instance
   */
  @Override
  protected void doTimeDelayedEffect(EntityInternal subject, EntityInternal otherEntity, double elapsedTime, Map<String, String> variables, GameInternal game) {
    double targetX = subject.getPosition().get(0);
    double targetY = otherEntity.getPosition().get(1) + otherEntity.getHeight();
    subject.setPosition(List.of(targetX,targetY));
    subject.blockInDirection("Up", true);
  }
}
