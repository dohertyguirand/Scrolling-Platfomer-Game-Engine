package ooga.game.behaviors.conditionalBehavior;

import ooga.Entity;
import ooga.game.behaviors.CollisionBehavior;

import java.util.Map;

public class ConditionalCollisionBehavior extends ConditionalBehaviorInstance{

  public ConditionalCollisionBehavior(Map<String, Double> variableConditions, Map<String, Boolean> inputConditions, Map<String, Boolean> collisionConditions, CollisionBehavior behavior) {
    super(variableConditions, inputConditions, collisionConditions, behavior);
  }

  /**
   * executes the behavior owned by this behavior
   *
   * @param elapsedTime time in ms
   * @param subject     entity that owns this conditional behavior
   */
  @Override
  public void doUpdate(double elapsedTime, Entity subject) {

  }
}
