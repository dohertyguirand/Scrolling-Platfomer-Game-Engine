package ooga.game.behaviors.conditions;

import java.util.Map;
import ooga.game.EntityInternal;
import ooga.game.GameInternal;

/**
 * @author Sam Thompson
 * Compares a variable value from the Entity that owns this Condition's Behavior to the
 * required value.
 * {@inheritDoc}
 */
public class OogaEntityVarCondition extends OogaVariableCondition {

  /**
   * {@inheritDoc}
   */
  public OogaEntityVarCondition(Map<String, String> args) {
    super(args);
  }

  /**
   * @param subject The Entity whose Behavior owns this Condition.
   * @param game The Game that holds game-wide information relevant to the Entity owning this Behavior.
   * @param variableName The name of the variable specified when the Condition was created.
   * @return The subject Entity's variable value associated with this variable name.
   */
  @Override
  public String findVariableValue(EntityInternal subject, GameInternal game, String variableName) {
    return subject.getVariable(variableName);
  }
}