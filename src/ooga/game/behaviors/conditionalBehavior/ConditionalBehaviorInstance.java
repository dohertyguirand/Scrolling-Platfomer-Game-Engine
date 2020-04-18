package ooga.game.behaviors.conditionalBehavior;

import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.ConditionalBehavior;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class ConditionalBehaviorInstance implements ConditionalBehavior {

  Map<String, Boolean> inputConditions;
  Map<String, Boolean> verticalCollisionConditions;
  Map<String, Boolean> horizontalCollisionConditions;
  Map<String, Double> variableConditions;
  Object behavior;

  public ConditionalBehaviorInstance(Map<String, Double> variableConditions, Map<String, Boolean> inputConditions,
                                     Map<String, Boolean> verticalCollisionConditions,
                                     Map<String, Boolean> horizontalCollisionConditions, Object behavior){
    this.inputConditions = inputConditions;
    this.variableConditions = variableConditions;
    this.verticalCollisionConditions = verticalCollisionConditions;
    this.horizontalCollisionConditions = horizontalCollisionConditions;
    this.behavior = behavior;
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
    if (variablesSatisfied(variables) && inputsSatisfied(inputs) &&
        collisionsSatisfied(verticalCollisions, verticalCollisionConditions) &&
        collisionsSatisfied(horizontalCollisions, horizontalCollisionConditions)) {
      System.out.println("DOING UPDATE");
      doUpdate(elapsedTime, subject, variables, inputs, horizontalCollisions, verticalCollisions, gameInternal);
    }
    else {
      System.out.println("NOT SATISFIED");
      System.out.println(variablesSatisfied(variables));
      System.out.println(inputsSatisfied(inputs));
      System.out.println(collisionsSatisfied(verticalCollisions,verticalCollisionConditions));
      System.out.println(collisionsSatisfied(horizontalCollisions,horizontalCollisionConditions));
    }
  }

  private boolean collisionsSatisfied(List<Entity> collisions,
      Map<String, Boolean> requiredCollisions) {
    List<String> horizontalCollisionNames = getEntityNames(collisions);
    for (Map.Entry<String, Boolean> collisionCondition : requiredCollisions.entrySet()) {
      if (horizontalCollisionNames.contains(collisionCondition.getKey()) != collisionCondition
          .getValue()) {
        System.out.println("COLLISIONS NOT SATISFIED, NEED " + collisionCondition);
        return false;
      }
    }
    return true;
  }

  private boolean inputsSatisfied(List<String> inputs) {
    for(Map.Entry<String, Boolean> inputCondition : inputConditions.entrySet()){
      if(inputs.contains(inputCondition.getKey()) != inputCondition.getValue()){
        System.out.println("INPUTS NOT SATISFIED, NEED " + inputCondition);
        return false;
      }
    }
    return true;
  }

  private boolean variablesSatisfied(Map<String, Double> variables) {
    for(Map.Entry<String, Double> variableCondition : variableConditions.entrySet()){
      if(!variables.get(variableCondition.getKey()).equals(variableCondition.getValue())){
        System.out.println("VARIABLES NOT SATISFIED, NEED " + variableCondition);
        return false;
      }
    }
    return true;
  }

  private List<String> getEntityNames(List<Entity> entityList){
    List<String> entityNames = new ArrayList<>();
    for(Entity entity : entityList){
      entityNames.add(entity.getName());
    }
    return entityNames;
  }
}
