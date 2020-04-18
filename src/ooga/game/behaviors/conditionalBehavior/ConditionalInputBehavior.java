package ooga.game.behaviors.conditionalBehavior;

import java.util.HashMap;
import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.NonCollisionEffect;

import java.util.List;
import java.util.Map;

public class ConditionalInputBehavior extends ConditionalBehaviorInstance {

  private String inputName;

  public ConditionalInputBehavior(Map<String, Double> variableConditions, Map<String, Boolean> inputConditions,
                                     Map<String, Boolean> verticalCollisionConditions,
                                     Map<String, Boolean> horizontalCollisionConditions, Object behavior,
                                  String inputName){
    super(variableConditions, inputConditions, verticalCollisionConditions, horizontalCollisionConditions, behavior);
    this.inputName = inputName;
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
    NonCollisionEffect nonCollisionEffect = (NonCollisionEffect)behavior;
    for(String input : inputs) {
      if(input.equals(inputName)) {
        nonCollisionEffect.doEffect(1.0, subject, new HashMap<>());
        break;
      }
    }
  }
}
