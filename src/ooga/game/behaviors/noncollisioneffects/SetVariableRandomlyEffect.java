package ooga.game.behaviors.noncollisioneffects;

import java.util.List;
import java.util.Map;
import java.util.Random;
import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;
import ooga.game.behaviors.TimeDelayedEffect;

@SuppressWarnings("unused")
public class SetVariableRandomlyEffect extends TimeDelayedEffect {

  private String variableName;
  private String randomRangeMin;
  private String randomRangeMax;

  public SetVariableRandomlyEffect(List<String> args) throws IndexOutOfBoundsException {
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
    randomRangeMin = args.get(1);
    randomRangeMax = args.get(2);
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
    double randomMinValue = parseData(randomRangeMin, subject, variables, 0.0);
    double randomMaxValue = parseData(randomRangeMax, subject, variables, 1.0);

    double variableValue = (new Random().nextDouble()) * (randomMaxValue-randomMinValue);

    System.out.println("variableValue = " + variableValue);
    if (variables.containsKey(variableName)) {
      System.out.println("Set " + variableName + " to " + variableValue);
      variables.put(variableName, Effect.doVariableSubstitutions(Double.toString(variableValue), subject, variables));
    }
    if(subject.getVariable(variableName) != null){
      System.out.println("Set " + variableName + " to " + variableValue);
      subject.addVariable(variableName, Double.toString(variableValue));
    }
    //TODO: add ability to set value of one entity variable to that of another variable?
  }
}
