package ooga.game.behaviors;

import java.util.Map;

import ooga.game.EntityInternal;

/**
 * @author sam thompson
 * Represents a condition to a behavior's execution that depends on the value of
 * a Game or Entity variable. Given the status of the game's variables and the subject's variables,
 * and given a variable name to check for when it's instantiated, it checks whether the variable
 * meets a requirement related to a target value.
 * Dependencies: Relies on EntityInternal to access variable mappings, but also takes Maps of the Game's and
 * the subject Entity's variables.
 * Example: A Behavior that starts the game over might require that the Game's Lives variable is less
 * than 1.
 */
public interface VariableCondition {

  /**
   * @param behaviorEntity The EntityInternal that owns this behavior.
   * @param gameVariables The Game's Map from variable names to String values. Doesn't have to
   *                      be mutable.
   * @param subjectVariables The Map of variables of the EntityInternal that owns this behavior.
   * @return True if the condition is satisfied based on the variable values.
   */
  boolean isSatisfied(EntityInternal behaviorEntity, Map<String, String> gameVariables, Map<String, String> subjectVariables);
}
