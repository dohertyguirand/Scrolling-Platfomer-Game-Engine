package ooga.game.behaviors.framebehavior;

import ooga.Entity;
import ooga.game.behaviors.FrameBehavior;

import java.util.List;
import java.util.Map;

public class SetVariableBehavior implements FrameBehavior {

  public static final String DEFAULT_VARIABLE_NAME = "DEFAULT";
  public static final double DEFAULT_VARIABLE_VALUE = 0.0;
  private String variableName;
  private Double variableValue;

  public SetVariableBehavior(List<String> args) {
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
   *  @param elapsedTime The time since the previous frame.
   * @param subject     The entity to perform the update upon.
   * @param variables map of variables
   */
  @Override
  public void doFrameUpdate(double elapsedTime, Entity subject, Map<String, Double> variables) {
    //in the variable map, increment variableName by variableValue
    System.out.println("variables = " + variables);
    System.out.println("variableName = " + variableName);
    System.out.println("setValue = " + variableValue);
    if (variables.containsKey(variableName)) {
      variables.put(variableName,variableValue);
    }
  }
}
