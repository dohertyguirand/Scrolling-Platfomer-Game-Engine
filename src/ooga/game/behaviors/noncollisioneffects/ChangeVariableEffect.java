package ooga.game.behaviors.noncollisioneffects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;
import ooga.game.behaviors.ExpressionEvaluator;
import ooga.game.behaviors.TimeDelayedEffect;

import javax.script.ScriptException;

@SuppressWarnings("unused")
public class ChangeVariableEffect extends TimeDelayedEffect {

  private String variableName;
  private String operatorData;
  private String changeValueData;

  public ChangeVariableEffect(List<String> args) throws IndexOutOfBoundsException {
    super(args);
  }

  /**
   * This should be variable name, operator (e.g. +), value
   * @param args The String arguments given for this effect in the data file.
   */
  @Override
  public void processArgs(List<String> args) {
    int index = 0;
    variableName = args.get(index++);
    operatorData = args.get(index++);
    changeValueData = args.get(index);
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
    double changeValue = parseData(changeValueData, subject, variables, 1.0);
    String operator = Effect.doVariableSubstitutions(operatorData, subject, variables);
    if (variables.containsKey(variableName)) {
      try {
        variables.put(variableName, evaluateOperation(variables.get(variableName), changeValue, operator));
        return;
      }catch (NumberFormatException | ScriptException ignored){
        System.out.println("Couldn't increment the game variable " + variableName);
      }
    }
    String entityVariableValue = subject.getVariable(variableName);
    if(entityVariableValue != null){
      try {
        subject.addVariable(variableName, evaluateOperation(entityVariableValue, changeValue, operator));
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
