package ooga.game.behaviors.conditionalBehavior;

import ooga.Entity;
import ooga.game.GameInternal;

import java.util.List;
import java.util.Map;
import ooga.game.behaviors.NonCollisionEffect;

public class ConditionalNonCollisionBehavior extends ConditionalBehaviorInstance {

  public ConditionalNonCollisionBehavior(Map<String, Double> variableConditions, Map<String, Boolean> inputConditions,
                                         Map<String, Boolean> verticalCollisionConditions,
                                         Map<String, Boolean> horizontalCollisionConditions, Object behavior){
    super(variableConditions, inputConditions, verticalCollisionConditions, horizontalCollisionConditions, behavior);
  }

  /**
   * executes the behavior owned by this behavior
   * @param elapsedTime time in ms
   * @param subject entity that owns this conditional behavior
   * @param variables map of variables in the game/level
   * @param inputs the input keys that are currently active in this frame
   * @param horizontalCollisions the entities this entity is colliding with horizontally
   * @param verticalCollisions the entities this entity is colliding with vertically
   * @param gameInternal what game this is run from
   */
  @Override
  public void doUpdate(double elapsedTime, Entity subject, Map<String, Double> variables, List<String> inputs,
                       List<Entity> horizontalCollisions, List<Entity> verticalCollisions, GameInternal gameInternal) {
    NonCollisionEffect frameBehavior = (NonCollisionEffect) behavior;
    frameBehavior.doEffect(elapsedTime, subject, variables);
  }
}
