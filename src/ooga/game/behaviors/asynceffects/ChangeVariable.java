package ooga.game.behaviors.asynceffects;

import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;

public class ChangeVariable implements Effect {

  public static final String DEFAULT_VARIABLE_NAME = "DEFAULT";
  public static final double DEFAULT_INCREMENT_VALUE = 1.0;
  private String variableName;
  private Double incrementValue;

  public ChangeVariable(List<String> args) {
    if (args.size() >= 2) {
      variableName = args.get(0);
      incrementValue = Double.parseDouble(args.get(1));
    }
    else {
      variableName = DEFAULT_VARIABLE_NAME;
      incrementValue = DEFAULT_INCREMENT_VALUE;
    }
    System.out.println("variableName = " + variableName);
    System.out.println("incrementValue = " + incrementValue);
  }


  @Override
  public void doEffect(Entity subject, Entity otherEntity, double elapsedTime, Map<String, Double> variables, GameInternal game) {
    //in the variable map, increment variableName by variableValue
    System.out.println("variables = " + variables);
    System.out.println("variableName = " + variableName);
    System.out.println("incrementValue = " + incrementValue);
    if (variables.containsKey(variableName)) {
      variables.put(variableName,variables.get(variableName)+incrementValue);
    }
  }
}
