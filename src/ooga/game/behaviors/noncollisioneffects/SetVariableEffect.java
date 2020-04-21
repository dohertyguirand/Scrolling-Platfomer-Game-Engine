package ooga.game.behaviors.noncollisioneffects;

import ooga.Entity;

import java.util.List;
import java.util.Map;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;

public class SetVariableEffect implements Effect {

  public static final String DEFAULT_VARIABLE_NAME = "DEFAULT";
  public static final double DEFAULT_VARIABLE_VALUE = 0.0;
  private String variableName;
  private Double variableValue;

  public SetVariableEffect(List<String> args) {
    if (args.size() >= 2) {
      variableName = args.get(0);
      variableValue = Double.parseDouble(args.get(1));
    }
    else {
      variableName = DEFAULT_VARIABLE_NAME;
      variableValue = DEFAULT_VARIABLE_VALUE;
    }
    System.out.println("variableName = " + variableName);
    System.out.println("variableValue = " + variableValue);
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
  public void doEffect(Entity otherEntity, Entity subject, double elapsedTime, Map<String, Double> variables, GameInternal game) {
    //in the variable map, increment variableName by variableValue
    System.out.println("variables = " + variables);
    System.out.println("variableName = " + variableName);
    System.out.println("setValue = " + variableValue);
    if (variables.containsKey(variableName)) {
      variables.put(variableName,variableValue);
    }
  }
}
