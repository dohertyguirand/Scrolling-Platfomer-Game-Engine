package ooga.game.behaviors.conditionalBehavior;

import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.CollisionEffect;

import java.util.List;
import java.util.Map;

public class ConditionalCollisionBehavior extends ConditionalBehaviorInstance{

  private String collidingEntityName;

  public ConditionalCollisionBehavior(Map<String, Double> variableConditions, Map<String, Boolean> inputConditions,
                                      Map<String, Boolean> verticalCollisionConditions,
                                      Map<String, Boolean> horizontalCollisionConditions, CollisionEffect behavior,
                                      String collidingEntityName) {
    super(variableConditions, inputConditions, verticalCollisionConditions, horizontalCollisionConditions, behavior);
    this.collidingEntityName = collidingEntityName;
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
    CollisionEffect collisionEffect = (CollisionEffect)behavior;
    for (Entity collidingWith : verticalCollisions) {
      if(collidingWith.getName().equals(collidingEntityName)) {
        collisionEffect
            .doVerticalCollision(subject, collidingWith, elapsedTime, variables, gameInternal);
      }
    }
    for (Entity collidingWith : horizontalCollisions) {
      if(collidingWith.getName().equals(collidingEntityName)) {
        collisionEffect
            .doHorizontalCollision(subject, collidingWith, elapsedTime, variables, gameInternal);
      }
    }
  }
}
