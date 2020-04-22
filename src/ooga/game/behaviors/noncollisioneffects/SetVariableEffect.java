package ooga.game.behaviors.noncollisioneffects;

import ooga.Entity;

import java.util.List;
import java.util.Map;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;

public class SetVariableEffect implements Effect {

  private String variableName;
  private Double variableValue;

  public SetVariableEffect(List<String> args) throws IndexOutOfBoundsException {
    variableName = args.get(0);
    variableValue = Double.parseDouble(args.get(1));
  }

  /**
   * Performs the subclass-specific implementation that happens per frame.
   * @param otherEntity
   * @param subject     The entity to perform the update upon.
   * @param elapsedTime The time since the previous frame.
   * @param variables map of variables
   * @param game
   */
  @Override
  public void doEffect(Entity subject, Entity otherEntity, double elapsedTime, Map<String, Double> variables, GameInternal game) {
    //in the variable map, increment variableName by variableValue
    if (variables.containsKey(variableName)) {
      variables.put(variableName,variableValue);
    }
  }
}
