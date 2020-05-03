package ooga.game.behaviors;

import java.util.Map;
import ooga.game.EntityInternal;
import ooga.game.GameInternal;
import ooga.game.behaviors.comparators.VariableComparator;

/**
 * @author sam thompson
 * Carries out the functionality of VariableCondition for Ooga.
 * Since Ooga variables can have String OR Double values, and the variable given could be
 * a Game or an Entity variable, it has a built-in priority order for finding out what to check.
 * Dependencies:  Relies on EntityInternal to check whether the Entity that owns this condition
 * has the variable, and what its value is.
 *                Relies on VariableComparator for the type of comparison between variable and value.
 * Example: A Behavior that starts the game over might require the Condition to be true
 * that the Game's Lives variable is less than 1.
 */
public class OogaVariableCondition implements Condition {

  private final VariableComparator myComparator;
  private final String myCompareTo;
  private final String myVariableName;

  /**
   * @param varName The name of the variable to check the value of. It could be a Game variable
   *                or a variable related to the Entity that whose behavior owns this condition.
   * @param comparator The VariableComparator to use to compare the value of the variable to
   *                   the target value.
   * @param value The target value to compare the variable to. Can be a variable name.
   */
  public OogaVariableCondition(String varName, VariableComparator comparator, String value) {
    myComparator = comparator;
    myCompareTo = value;
    myVariableName = varName;
  }

  /**
   * @return A String representation of this Condition.
   * Example: "Compares variable Lives to 0.0"
   */
  @Override
  public String toString() {
    return "Compares variable " + myVariableName + " to value " + myCompareTo;
  }

  /**
   * Dereferences the given variable name using a Map that combines Game and subject Entity
   * variables.
   * Then it dereferences the target value in the following priority order:
   * 1. It checks the Game variables for a variable name matching myCompareTo.
   * 2. It checks the Entity variables of the subject for a match to myCompareTo.
   * 3. It uses the immediate value of myCompare to as given.
   * @param behaviorEntity The EntityInternal that owns this behavior.
   * @param gameVariables The Game's Map from variable names to String values. Doesn't have to
   *                      be mutable.
   * @param subjectVariables The Map of variables of the EntityInternal that owns this behavior.
   * @return True if the condition is satisfied based on the variable values.
   */
  @Override
  public boolean isSatisfied(EntityInternal subject, GameInternal game, Map<String, String> inputs,
      Map<EntityInternal, Map<EntityInternal, String>> collisions) {
    Map<String,String> subjectVariables = subject.getVariables();
    Map<String,String> gameVariables = game.getVariables();
    if (!subjectVariables.containsKey(myVariableName)) {
      return false;
    }
    String compareToValue = myCompareTo;
    if (gameVariables.containsKey(myCompareTo)) {
      compareToValue = gameVariables.get(myCompareTo);
    }
    else if (subject.getVariable(myCompareTo) != null) {
      compareToValue = subject.getVariable(myCompareTo);
    }
    return (myComparator.compareVars(subjectVariables.get(myVariableName),compareToValue));
  }
}