package ooga.game.behaviors.asyncbehavior;

import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.game.DirectionlessCollision;

public class ChangeVariable extends DirectionlessCollision {

  private String variableName;
  private Double incrementValue;

  public ChangeVariable(List<String> args) {
    variableName = args.get(0);
    incrementValue = Double.parseDouble(args.get(1));
  }

  @Override
  public void doCollision(Entity subject, Entity collidingEntity, double elapsedTime,
      Map<String, Double> variables) {
    //in the variable map, increment variableName by variableValue
    if (variables.containsKey(variableName)) {
      variables.put(variableName,variables.get(variableName)+incrementValue);
    }
    System.out.println("VARIABLE MODIFIED");
  }
}
