package ooga.game.behaviors.noncollisioneffects;

import java.util.List;
import java.util.Map;
import ooga.game.EntityInternal;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;
import ooga.game.behaviors.TimeDelayedEffect;

/**
 * @author caryshindell, sam thompson
 * This will set a game or entity variable to the specified value.
 * Note: the value can be substituted with a variable but the variable name cannot.
 * Dependencies: time delayed effect, entity internal
 * Example: set game variable score to value of entity variable THIS_LEVEL_SCORE
 */
@SuppressWarnings("unused")
public class SetVariableEffect extends TimeDelayedEffect {

  private String variableName;
  private String variableValue;

  /**
   * @param args  1. The name of the variable to set.
   *              2. The value to set the variable to.
   * @throws IndexOutOfBoundsException If there are arguments missing.
   */
  public SetVariableEffect(List<String> args) throws IndexOutOfBoundsException {
    super(args);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void processArgs(List<String> args) {
    variableName = args.get(0);
    variableValue = args.get(1);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doTimeDelayedEffect(EntityInternal subject, EntityInternal otherEntity, double elapsedTime, Map<String, String> variables, GameInternal game) {
    if (variables.containsKey(variableName)) {
      game.setVariable(variableName, Effect.doVariableSubstitutions(variableValue, subject, variables));
    }
    if(subject.getVariable(variableName) != null){
      subject.addVariable(variableName, Effect.doVariableSubstitutions(variableValue, subject, variables));
    }
  }
}
