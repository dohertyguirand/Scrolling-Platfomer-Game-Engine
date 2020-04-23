package ooga.game.behaviors.noncollisioneffects;

import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.TimeDelayedEffect;

@SuppressWarnings("unused")
public class ChangeVariableEffect extends TimeDelayedEffect {

  private String variableName;
  private String incrementValueData;

  public ChangeVariableEffect(List<String> args) throws IndexOutOfBoundsException {
    super(args);
  }

  @Override
  public void processArgs(List<String> args) {
    variableName = args.get(0);
    incrementValueData = args.get(1);
  }

  /**
   * Performs the effect
   *  @param subject     The entity that owns this. This is the entity that should be modified.
   * @param otherEntity entity we are "interacting with" in this effect
   * @param elapsedTime time between steps in ms
   * @param variables   game variables
   * @param game        game instance
   */
  @Override
  protected void doTimeDelayedEffect(Entity subject, Entity otherEntity, double elapsedTime, Map<String, String> variables, GameInternal game) {
    //in the variable map, increment variableName by variableValue
    double incrementValue = parseData(incrementValueData, subject, variables, 1.0);
    if (variables.containsKey(variableName)) {
      variables.put(variableName,String.valueOf(Double.parseDouble(variables.get(variableName)) + incrementValue));
      return;
    }
    String entityVariableValue = subject.getVariable(variableName);
    if(entityVariableValue != null){
      try {
        subject.addVariable(variableName, String.valueOf(Double.parseDouble(entityVariableValue) + incrementValue));
      } catch (NumberFormatException ignored){
        System.out.println("Couldn't increment the entity variable of " + subject.getName());
      }
    }
  }
}
