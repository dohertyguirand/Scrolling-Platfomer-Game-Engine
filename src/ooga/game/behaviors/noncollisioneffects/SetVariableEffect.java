package ooga.game.behaviors.noncollisioneffects;

import ooga.Entity;

import java.util.List;
import java.util.Map;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;
import ooga.game.behaviors.TimeDelayedEffect;

public class SetVariableEffect extends TimeDelayedEffect {

  private String variableName;
  private String variableValue;

  public SetVariableEffect(List<String> args) throws IndexOutOfBoundsException {
    super(args);
  }

  /**
   * Processes the String arguments given in the data file into values used by this effect.
   *
   * @param args The String arguments given for this effect in the data file.
   */
  @Override
  public void processArgs(List<String> args) {
    variableName = args.get(0);
    variableValue = args.get(1);
  }

  /**
   * Performs the effect
   *
   * @param subject     The entity that owns this. This is the entity that should be modified.
   * @param otherEntity entity we are "interacting with" in this effect
   * @param elapsedTime time between steps in ms
   * @param variables   game variables
   * @param game        game instance
   */
  @Override
  protected void doTimeDelayedEffect(Entity subject, Entity otherEntity, double elapsedTime, Map<String, Double> variables, GameInternal game) {
    //in the variable map, increment variableName by variableValue
    if (variables.containsKey(variableName)) {
      variables.put(variableName,parseData(variableValue, subject, variables, 0.0));
    }
    if(subject.getVariable(variableName) != null){
      subject.addVariable(variableName, variableValue);
    }
    //TODO: add ability to set value of one entity variable to that of another variable?
  }
}
