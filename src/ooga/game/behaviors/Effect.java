package ooga.game.behaviors;

import java.util.Map;
import ooga.Entity;
import ooga.game.GameInternal;

/**
 * Determines how an entity reacts to in-game user input of any kind.
 * Example: In Super Mario Bros, Mario can be moved with left and right keys.
 * Relies on a mapping (usually data-driven) between Strings and types of inputs.
 */
public interface Effect {

  /**
   * Handles reaction to controls. Requires the ControlsBehavior to have a reference to the
   * instance that uses it in order to have an effect on that instance.
   * @param subject The entity that owns this controls behavior. This is the entity that should
 *                be modified.
   * @param otherEntity
   * @param elapsedTime
   * @param variables
   * @param game
   */
  void doEffect(Entity subject, Entity otherEntity, double elapsedTime, Map<String, String> variables, GameInternal game);

  /**
   * attempts to convert the given data into a double. If it can't, tries to get a value from game variables, then from entity variables.
   *  If that doesn't work, uses default value.
   * @param data string given by DataReader
   * @param subject entity the effect is taking place on
   * @param variables game variables
   * @param defaultValue what to return if no match is found
   * @return the parsed value
   */
  default double parseData(String data, Entity subject, Map<String, String> variables, double defaultValue){
  double parsedData;
  /*
  Have string theValue
  Look for matching game variable, set theValue to it
  If not found, look for matching entity variable, set theValue to it
  Try to parse theValue to double and try to parse variable.getValue to double, and compare them as doubles
  Compare theValue to variable.getValue as strings
   */
  String finalValue = data;
  if(variables.containsKey(data)){
    finalValue = variables.get(data);
  } else if(subject.getVariable(data) != null){
    finalValue = subject.getVariable(data);
  }
  try{
    parsedData = Double.parseDouble(finalValue);
  } catch (NumberFormatException e){
    parsedData = defaultValue;
  }
  return parsedData;
  }
}
