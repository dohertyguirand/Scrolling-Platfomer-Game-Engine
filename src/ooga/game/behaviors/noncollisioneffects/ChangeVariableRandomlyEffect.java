package ooga.game.behaviors.noncollisioneffects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.script.ScriptException;
import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;
import ooga.game.behaviors.ExpressionEvaluator;
import ooga.game.behaviors.TimeDelayedEffect;

@SuppressWarnings("unused")
public class ChangeVariableRandomlyEffect extends TimeDelayedEffect {

  private String variableName;
  private String operatorData;
  private String randomRangeMin;
  private String randomRangeMax;

  public ChangeVariableRandomlyEffect(List<String> args) throws IndexOutOfBoundsException {
    super(args);
  }

  /**
   * This should be variable name, operator (e.g. +), value
   * @param args The String arguments given for this effect in the data file.
   */
  @Override
  public void processArgs(List<String> args) {
    variableName = args.get(0);
    operatorData = args.get(1);
    randomRangeMin = args.get(2);
    randomRangeMax = args.get(3);
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

    double changeValueData = (new Random().nextDouble()) * (randomMaxValue-randomMinValue);
    String operator = Effect.doVariableSubstitutions(operatorData, subject, variables);
    if (variables.containsKey(variableName)) {
      try {
        variables.put(variableName, evaluateOperation(variables.get(variableName), changeValueData, operator));
        System.out.println("Changed " + variableName + " by (" + operator + ") " + changeValueData);
        return;
      }catch (NumberFormatException | ScriptException ignored){
        System.out.println("Couldn't increment the game variable " + variableName);
      }
    }
    String entityVariableValue = subject.getVariable(variableName);
    if(entityVariableValue != null){
      try {
        subject.addVariable(variableName, evaluateOperation(entityVariableValue, changeValueData, operator));
        System.out.println("Changed " + variableName + " by (" + operator + ") " + changeValueData);
      } catch (NumberFormatException | ScriptException ignored){
        System.out.println("Couldn't increment the entity variable of " + subject.getName());
      }
    }
  }

  private String evaluateOperation(String varValue, double changeValue, String operator) throws NumberFormatException, ScriptException {
    String formattedVarValue = BigDecimal.valueOf(Double.parseDouble(varValue)).toPlainString();
    return String.valueOf(ExpressionEvaluator.eval(formattedVarValue + operator + changeValue));
  }
}
