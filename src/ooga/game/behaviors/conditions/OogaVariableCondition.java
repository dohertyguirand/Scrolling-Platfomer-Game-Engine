package ooga.game.behaviors.conditions;

import java.util.Map;
import ooga.game.EntityInternal;
import ooga.game.GameInternal;
import ooga.game.behaviors.Condition;
import ooga.game.behaviors.VariableComparatorFactory;
import ooga.game.behaviors.comparators.VariableComparator;

/**
 * @author sam thompson
 * Checks whether a Comparator evaluation between some variable value and and a given value is true.
 * Since Ooga variables can have String OR Double values, and the variable given could be
 * a Game or an Entity variable, it has a built-in priority order for finding out what to check.
 * Dependencies:  Relies on EntityInternal to check whether the Entity that owns this condition
 * has the variable, and what its value is.
 *                Relies on VariableComparator for the type of comparison between variable and value.
 * Example: A Behavior that starts the game over might require the Condition to be true
 * that the Game's Lives variable is less than 1.
 */
public abstract class OogaVariableCondition implements Condition {

  public static final String COMPARATOR_LABEL = "Comparison";
  public static final String TARGET_VALUE_LABEL = "ComparedTo";
  public static final String VAR_NAME_LABEL = "VariableName";
  private final VariableComparator myComparator;
  private final String myCompareTo;
  private final String myVariableName;

  /**
   * @param args A String-String Map with the following:
   *             "Comparison" maps to the comparator to use.
   *             "ComparedTo" maps to the value to compare to (the target value).
   *             "VariableName" maps to the name of the variable to compare with the target value.
   */
  public OogaVariableCondition(Map<String,String> args) {
    myComparator = new VariableComparatorFactory().makeComparator(args.get(COMPARATOR_LABEL));
    myCompareTo = args.get(TARGET_VALUE_LABEL);
    myVariableName = args.get(VAR_NAME_LABEL);
  }

  /**
   * {@inheritDoc}
   * NOTE: This dereferences the value to 'compare to' in the following priority order:
   * 1. It checks for a Game variable with that name.
   * 2. It checks the Entity variables of Subject for a variable with that name.
   * 3. It uses the immediate value of myCompareTo to as given.
   */
  @Override
  public boolean isSatisfied(EntityInternal subject, GameInternal game, Map<String, String> inputs,
      Map<EntityInternal, Map<EntityInternal, String>> collisions) {
    String variableValue = findVariableValue(subject,game,myVariableName);
    String compareToValue = findCompareToValue(subject,game);
    return (myComparator.compareVars(variableValue,compareToValue));
  }

  /**
   * @param subject The Entity whose Behavior owns this Condition.
   * @param game The Game that holds game-wide information relevant to the Entity owning this Behavior.
   * @param variableName The name of the variable specified when the Condition was created.
   * @return  The actual value to compare to the target value when evaluating this Condition.
   *          Can be a String representing a Double, or some other String, or null.
   */
  public abstract String findVariableValue(EntityInternal subject, GameInternal game, String variableName);

  //Carries out the order for dereferencing the value to compare to, as described in isSatisfied
  //documentation.
  private String findCompareToValue(EntityInternal subject, GameInternal game) {
    String compareToValue = myCompareTo;
    if (game.getVariables().containsKey(myCompareTo)) {
      compareToValue = game.getVariables().get(myCompareTo);
    }
    else if (subject.getVariable(myCompareTo) != null) {
      compareToValue = subject.getVariable(myCompareTo);
    }
    return compareToValue;
  }

  /**
   * @return A String representation of this Condition.
   * Example: "Lives == 0.0"
   */
  @Override
  public String toString() {
    return myVariableName + " " + myComparator.toString() + " " + myCompareTo;
  }
}