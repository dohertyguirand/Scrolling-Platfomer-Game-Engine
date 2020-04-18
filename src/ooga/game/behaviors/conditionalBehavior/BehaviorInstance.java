package ooga.game.behaviors.conditionalBehavior;

import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.CollisionEffect;
import ooga.game.behaviors.ConditionalBehavior;
import ooga.game.behaviors.NonCollisionEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BehaviorInstance implements ConditionalBehavior {

  Map<String, Boolean> inputConditions;
  Map<String, Boolean> verticalCollisionConditions;
  Map<String, Boolean> horizontalCollisionConditions;
  Map<String, Double> variableConditions;
  Map<String, List<CollisionEffect>> collisionEffects;
  List<NonCollisionEffect> nonCollisionEffects;

  public BehaviorInstance(Map<String, Double> variableConditions, Map<String, Boolean> inputConditions,
                          Map<String, Boolean> verticalCollisionConditions,
                          Map<String, Boolean> horizontalCollisionConditions, Map<String, List<CollisionEffect>> collisionEffects,
                          List<NonCollisionEffect> nonCollisionEffects){
    this.inputConditions = inputConditions;
    this.variableConditions = variableConditions;
    this.verticalCollisionConditions = verticalCollisionConditions;
    this.horizontalCollisionConditions = horizontalCollisionConditions;
    this.collisionEffects = collisionEffects;
    this.nonCollisionEffects = nonCollisionEffects;
  }

  /**
   * So we want a new type of behavior, conditional behavior, which has a map of conditionals for each thing it is possible
   * to depend on (inputs, collisions, variables). It also has a list of behaviors it will execute if the conditionals are true.
   * Example: Fireboy can only go through door, ending the level, if he is colliding with door and touching ground,
   * variable red diamonds collected is 5 (or perhaps a dynamic value?),
   * and user is pressing up key
   * Method call for this example would look like: new ConditionalBehavior(inputs=(up:true), collisions=(door:true, ground:true),
   * variables=(red diamonds: 5), behaviors=(EndLevel))
   * @param elapsedTime time passed in ms
   * @param subject     entity that owns this behavior
   * @param variables   map of game/level variables
   * @param inputs      all registered key inputs at this frame
   * @param verticalCollisions  names of all entities this entity is currently colliding with vertically
   * @param horizontalCollisions names of all entities this entity is currently colliding with horizontally
   * @param gameInternal what game this is run from
   */
  @Override
  public void doConditionalUpdate(double elapsedTime, Entity subject, Map<String, Double> variables, List<String> inputs,
                                  List<Entity> verticalCollisions, List<Entity> horizontalCollisions, GameInternal gameInternal) {
    for(Map.Entry<String, Double> variableCondition : variableConditions.entrySet()){
      if(!variables.get(variableCondition.getKey()).equals(variableCondition.getValue())){
        return;
      }
    }
    for(Map.Entry<String, Boolean> inputCondition : inputConditions.entrySet()){
      if(inputs.contains(inputCondition.getKey()) != inputCondition.getValue()){
        return;
      }
    }
    List<String> verticalCollisionNames = getEntityNames(verticalCollisions);
    for(Map.Entry<String, Boolean> collisionCondition : verticalCollisionConditions.entrySet()){
      if(verticalCollisionNames.contains(collisionCondition.getKey()) != collisionCondition.getValue()){
        return;
      }
    }
    List<String> horizontalCollisionNames = getEntityNames(horizontalCollisions);
    for(Map.Entry<String, Boolean> collisionCondition : horizontalCollisionConditions.entrySet()){
      if(horizontalCollisionNames.contains(collisionCondition.getKey()) != collisionCondition.getValue()){
        return;
      }
    }
    doCollisionEffects(elapsedTime, subject, variables, inputs, horizontalCollisions, verticalCollisions, gameInternal);
    doNonCollisionEffects(elapsedTime, subject, variables, gameInternal);
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
  public void doCollisionEffects(double elapsedTime, Entity subject, Map<String, Double> variables, List<String> inputs,
                       List<Entity> horizontalCollisions, List<Entity> verticalCollisions, GameInternal gameInternal) {
    for(Map.Entry<String, List<CollisionEffect>>  collisionEffectEntry: collisionEffects.entrySet()) {
      String collidingEntityName = collisionEffectEntry.getKey();
      List<CollisionEffect> specificCollisionEffects = collisionEffectEntry.getValue();
      for (Entity collidingWith : verticalCollisions) {
        if (collidingWith.getName().equals(collidingEntityName)) {
          for(CollisionEffect collisionEffect : specificCollisionEffects) {
            collisionEffect.doVerticalCollision(subject, collidingWith, elapsedTime, variables, gameInternal);
          }
        }
      }
      for (Entity collidingWith : horizontalCollisions) {
        if (collidingWith.getName().equals(collidingEntityName)) {
          for(CollisionEffect collisionEffect : specificCollisionEffects) {
            collisionEffect.doHorizontalCollision(subject, collidingWith, elapsedTime, variables, gameInternal);
          }
        }
      }
    }
  }

  /**
   * executes the behavior owned by this behavior
   * @param elapsedTime time in ms
   * @param subject entity that owns this conditional behavior
   * @param variables map of variables in the game/level
   * @param gameInternal instance of game internal
   */
  @Override
  public void doNonCollisionEffects(double elapsedTime, Entity subject, Map<String, Double> variables, GameInternal gameInternal) {
    for(NonCollisionEffect nonCollisionEffect : nonCollisionEffects) {
      nonCollisionEffect.doEffect(elapsedTime, subject, variables, gameInternal);
    }
  }

  private List<String> getEntityNames(List<Entity> entityList){
    List<String> entityNames = new ArrayList<>();
    for(Entity entity : entityList){
      entityNames.add(entity.getName());
    }
    return entityNames;
  }
}
