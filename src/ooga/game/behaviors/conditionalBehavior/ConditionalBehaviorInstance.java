package ooga.game.behaviors.conditionalBehavior;

import ooga.Entity;
import ooga.game.behaviors.ConditionalBehavior;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ConditionalBehaviorInstance implements ConditionalBehavior {

  Map<String, Boolean> inputConditions;
  Map<String, Boolean> collisionConditions;
  Map<String, Double> variableConditions;
  Object behavior;

  public ConditionalBehaviorInstance(Map<String, Double> variableConditions, Map<String, Boolean> inputConditions,
                                     Map<String, Boolean> collisionConditions, Object behavior){
    this.inputConditions = inputConditions;
    this.variableConditions = variableConditions;
    this.collisionConditions = collisionConditions;
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
   *
   * @param elapsedTime time passed in ms
   * @param subject     entity that owns this behavior
   * @param variables   map of game/level variables
   * @param inputs      all registered key inputs at this frame
   * @param collisions  names of all entities this entity is currently colliding with
   */
  @Override
  public void doConditionalUpdate(double elapsedTime, Entity subject, Map<String, Double> variables, List<String> inputs, List<String> collisions) {
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
    for(Map.Entry<String, Boolean> collisionCondition : collisionConditions.entrySet()){
      if(collisions.contains(collisionCondition.getKey()) != collisionCondition.getValue()){
        return;
      }
    }
    doUpdate(elapsedTime, subject);
  }
}
