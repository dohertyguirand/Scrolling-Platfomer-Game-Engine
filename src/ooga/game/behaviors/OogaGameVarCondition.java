package ooga.game.behaviors;

import ooga.game.EntityInternal;
import ooga.game.GameInternal;
import ooga.game.behaviors.comparators.VariableComparator;

/**
 * @author Sam Thompson
 * Compares a global (Game) variable value to the required value.
 * {@inheritDoc}
 */
public class OogaGameVarCondition extends OogaVariableCondition {

  /**
   * @param varName    The name of the variable to check the value of. It could be a Game variable or
   *                   a variable related to the Entity that whose behavior owns this condition.
   * @param comparator The VariableComparator to use to compare the value of the variable to the
   *                   target value.
   * @param value      The target value to compare the variable to. Can be a variable name.
   */
  public OogaGameVarCondition(String varName,
      VariableComparator comparator, String value) {
    super(varName, comparator, value);
  }

  /**
   * @param subject The Entity whose Behavior owns this Condition.
   * @param game The Game that holds game-wide information relevant to the Entity owning this Behavior.
   * @param variableName The name of the variable specified when the Condition was created.
   * @return The Game's variable value associated with this variable name.
   */
  @Override
  public String findVariableValue(EntityInternal subject, GameInternal game, String variableName) {
    return subject.getVariable(variableName);
  }
}