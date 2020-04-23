package ooga.game.behaviors.noncollisioneffects;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.TimeDelayedEffect;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

@SuppressWarnings("unused")
public class ChangeVariableEffect extends TimeDelayedEffect {

  private String variableName;
  private String operatorData;
  private String changeValueData;
  //private static final String OPERATOR_RESOURCES_LOCATION = "ooga/game/behaviors/resources/operators";

  public ChangeVariableEffect(List<String> args) throws IndexOutOfBoundsException {
    super(args);
  }

  @Override
  public void processArgs(List<String> args) {
    variableName = args.get(0);
    //ResourceBundle operatorResources = ResourceBundle.getBundle(OPERATOR_RESOURCES_LOCATION);
    operatorData = args.get(1);
    changeValueData = args.get(2);
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
    String operator = doVariableSubstitutions(operatorData, subject, variables);
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
    double varNumberValue = Double.parseDouble(varValue);
    ScriptEngineManager mgr = new ScriptEngineManager();
    ScriptEngine engine = mgr.getEngineByName("JavaScript");
    return (String) engine.eval(varNumberValue + operator + changeValue);
  }
}
