package ooga.game.behaviors.asynceffects;

import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;

public class ChangeVariable implements Effect {

  private String variableName;
  private Double incrementValue;

  public ChangeVariable(List<String> args) throws IndexOutOfBoundsException, NumberFormatException{
    variableName = args.get(0);
    incrementValue = Double.parseDouble(args.get(1));
  }


  @Override
  public void doEffect(Entity subject, Entity otherEntity, double elapsedTime, Map<String, Double> variables, GameInternal game) {
    //in the variable map, increment variableName by variableValue
    if (variables.containsKey(variableName)) {
      variables.put(variableName,variables.get(variableName)+incrementValue);
    }
  }
}
