package ooga.game.behaviors;

import java.util.Map.Entry;
import ooga.Entity;
import ooga.game.GameInternal;

import java.util.*;

public class BehaviorInstance implements ConditionalBehavior {

  public static final String ANY_DIRECTION = "ANY";
  public static final String SELF_IDENTIFIER = "SELF";
  public static final String ANY_KEY_REQUIREMENT = "KeyAny";
  public static final String KEY_INACTIVE_REQUIREMENT = "KeyInactive";
  final Map<String, List<String>> inputConditions;
  final Map<List<String>, String> requiredCollisionConditions;
  final Map<List<String>, String> bannedCollisionConditions;
  List<VariableCondition> gameVarConditions;
  Map<String,List<VariableCondition>> entityVarConditions;
  final List<Action> actions;

  /**
   * @param gameVariableConditions
   * @param entityVariableConditions
   * @param inputConditions
   * @param requiredCollisionConditions conditions that must be true Map<List<String>, String> [entity 1 info, entity 2 info] : direction (or "ANY")
   *   entity info can be id or name, method will check for either
   * @param bannedCollisionConditions conditions that must be false (see above)
   * @param actions
   */
  public BehaviorInstance(List<VariableCondition> gameVariableConditions,
      Map<String, List<VariableCondition>> entityVariableConditions,
      Map<String, List<String>> inputConditions,
      Map<List<String>, String> requiredCollisionConditions,
      Map<List<String>, String> bannedCollisionConditions, List<Action> actions){
    this.inputConditions = inputConditions;
    this.gameVarConditions = gameVariableConditions;
    this.entityVarConditions = entityVariableConditions;
    this.requiredCollisionConditions = requiredCollisionConditions;
    this.bannedCollisionConditions = bannedCollisionConditions;
    this.actions = actions;
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
   * @param collisionInfo Map of maps, direction name : map of collisions for that direction. map of collisions is entity : list of entities
   * @param gameInternal what game this is run from
   */
  @Override
  public void doConditionalUpdate(double elapsedTime, Entity subject, Map<String, String> variables, Map<String, String> inputs,
                                  Map<Entity, Map<String, List<Entity>>> collisionInfo, GameInternal gameInternal) {
    // TODO: add ability for entity instances to have additional behaviors?
    if (!checkGameVariableConditions(subject, variables)) {
      return;
    }
    if (!checkEntityVariableConditions(subject, gameInternal, variables)) {
      return;
    }
    for(Map.Entry<String, List<String>> inputCondition : inputConditions.entrySet()){
      if (!inputConditionSatisfied(inputs, inputCondition)) {
        return;
      }
    }
    if(anyCollisionConditionsUnsatisfied(collisionInfo, requiredCollisionConditions, true)) return;
    if(anyCollisionConditionsUnsatisfied(collisionInfo, bannedCollisionConditions, false)) return;
    doActions(elapsedTime, subject, variables, collisionInfo, gameInternal);
  }

  private boolean inputConditionSatisfied(Map<String, String> inputs,
      Entry<String, List<String>> inputCondition) {
    for (String inputType : inputCondition.getValue()) {
      String keyState = inputs.get(inputCondition.getKey());
      if (inputType.equals(keyState) ||
          (inputType.equals(ANY_KEY_REQUIREMENT) && keyState != null) ||
          (inputType.equals(KEY_INACTIVE_REQUIREMENT) && keyState == null)) {
        return true;
      }
    }
    return false;
  }


  private boolean checkGameVariableConditions(Entity subject, Map<String, String> gameVariables) {
    return checkVariableConditionsList(subject, gameVariables, gameVarConditions, gameVariables);
  }

  private boolean checkVariableConditionsList(Entity subject, Map<String, String> gameVariables,
      List<VariableCondition> varConditions,
      Map<String, String> targetEntityVars) {
    for (VariableCondition condition : varConditions) {
      if (!condition.isSatisfied(subject,gameVariables,targetEntityVars)) {
        return false;
      }
    }
    return true;
  }

  private boolean checkEntityVariableConditions(Entity subject, GameInternal gameInternal,
      Map<String, String> gameVariables) {
    for (Entry<String ,List<VariableCondition>> conditionEntry : entityVarConditions.entrySet()) {
      Entity identified = identifyEntityVariableSubject(subject,gameInternal,conditionEntry.getKey());
      if (identified == null) {
        return false;
      }
      if (!checkVariableConditionsList(subject,gameVariables,conditionEntry.getValue(), identified.getVariables())) {
        return false;
      }
    }
    return true;
  }

  private Entity identifyEntityVariableSubject(Entity subject, GameInternal gameInternal, String label) {
    if (label.equals(SELF_IDENTIFIER)) {
      return subject;
    }
    else {
      return otherEntitySubject(subject, gameInternal, label);
    }
  }

  private Entity otherEntitySubject(Entity subject, GameInternal gameInternal, String label) {
    String subjectVariable = subject.getVariable(label);
    if (subjectVariable != null) {
      Entity e = gameInternal.getEntityWithId(subject.getVariable(label));
      if (e != null) {
        return e;
      }
      e = gameInternal.getEntitiesWithName(subjectVariable).get(0);
      if (e != null) {
        return e;
      }
    }
    Entity e = gameInternal.getEntityWithId(label);
    if (e != null) {
      return e;
    }
    List<Entity> entitiesWithName = gameInternal.getEntitiesWithName(label);
    if (!entitiesWithName.isEmpty()) {
      return entitiesWithName.get(0);
    }
    return null;
  }

  private boolean anyCollisionConditionsUnsatisfied(Map<Entity, Map<String, List<Entity>>> collisionInfo,
                                                    Map<List<String>, String> collisionConditions, boolean required) {
    for(Map.Entry<List<String>, String> collisionConditionEntry : collisionConditions.entrySet()){
      String entity1Info = collisionConditionEntry.getKey().get(0);
      String entity2Info = collisionConditionEntry.getKey().get(1);
      String direction = collisionConditionEntry.getValue();
      if(checkCollisionCondition(collisionInfo, entity1Info, entity2Info, direction) != required) return true;
    }
    return false;
  }

  private boolean checkCollisionCondition(Map<Entity, Map<String, List<Entity>>> collisionInfo, String entity1Info, String entity2Info, String direction) {
    for(Entity entity : collisionInfo.keySet()){
      if(entityMatches(entity1Info, entity)){
        if(direction.equals(ANY_DIRECTION)){
          for(String possibleDirection : collisionInfo.get(entity).keySet()){
            if(hasCollisionInDirection(collisionInfo, entity2Info, possibleDirection, entity)) return true;
          }
        } else if (hasCollisionInDirection(collisionInfo, entity2Info, direction, entity)) return true;
      }
    }
    return false;
  }

  private boolean hasCollisionInDirection(Map<Entity, Map<String, List<Entity>>> collisionInfo, String entity2Info, String direction, Entity entity) {
    List<Entity> collidingWithInDirection = collisionInfo.get(entity).get(direction);
    for(Entity collidingEntity : collidingWithInDirection){
      if(entityMatches(entity2Info, collidingEntity)){
        return true;
      }
    }
    return false;
  }

  /**
   * collision/terrain specific!
   * @param entity1Info
   * @param entity
   * @return
   */
  private boolean entityMatches(String entity1Info, Entity entity) {
    return entity.getName().equals(entity1Info) ||
            (entity.getVariable("TerrainID") != null && entity.getVariable("TerrainID").equals(entity1Info));
  }

  /**
   * executes the actions owned by this behavior
   * @param elapsedTime time in ms
   * @param subject entity that owns this conditional behavior
   * @param variables map of variables in the game/level
   * @param collisionInfo current collision info
   * @param gameInternal what game this is run from
   */
  @Override
  public void doActions(double elapsedTime, Entity subject, Map<String, String> variables,
                        Map<Entity, Map<String, List<Entity>>> collisionInfo, GameInternal gameInternal) {
    for(Action action : actions){
      action.doAction(elapsedTime, subject, variables, collisionInfo, gameInternal);
    }
  }
}
