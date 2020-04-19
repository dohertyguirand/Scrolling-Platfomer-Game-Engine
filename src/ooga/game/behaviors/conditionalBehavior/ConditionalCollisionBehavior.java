package ooga.game.behaviors.conditionalBehavior;

import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.CollisionBehavior;

import java.util.List;
import java.util.Map;

public class ConditionalCollisionBehavior extends ConditionalBehaviorInstance{

  private String collidingEntityName;

  public ConditionalCollisionBehavior(Map<String, Double> variableConditions, Map<String, Boolean> inputConditions,
                                      Map<String, Boolean> verticalCollisionConditions,
                                      Map<String, Boolean> horizontalCollisionConditions, CollisionBehavior behavior,
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
    CollisionBehavior collisionBehavior = (CollisionBehavior)behavior;
    for (Entity collidingWith : verticalCollisions) {
      System.out.println("got here");
      System.out.println(collidingWith.getName());
      System.out.println(collidingEntityName);
      if(collidingWith.getName().equals(collidingEntityName)) {
        collisionBehavior.doVerticalCollision(subject, collidingWith, elapsedTime, variables, gameInternal);
      }
    }
    for (Entity collidingWith : horizontalCollisions) {
      System.out.println("got here");
      System.out.println(collidingWith.getName());
      System.out.println(collidingEntityName);
      if(collidingWith.getName().equals(collidingEntityName)) {
        collisionBehavior.doHorizontalCollision(subject, collidingWith, elapsedTime, variables, gameInternal);
      }
    }
  }
}
