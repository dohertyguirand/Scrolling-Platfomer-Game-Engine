package ooga.game.behaviors.conditions;

import java.util.Map;
import ooga.game.EntityInternal;
import ooga.game.GameInternal;
import ooga.game.behaviors.Condition;

/**
 * @author Sam Thompson
 * Is satisfied if a specific input has a specific status, with both of these being defined
 * when the Condition was initialized.
 * Example: To make Mario jump, UpKey must be Pressed this game step.
 * @see Condition
 */
public class OogaInputCondition implements Condition {

  private static final String ANY_KEY_REQUIREMENT = "ANY";
  private static final String KEY_INACTIVE_REQUIREMENT = "Inactive";
  public static final String INPUT_NAME_LABEL = "Key";
  public static final String INPUT_REQUIREMENT_LABEL = "InputRequirement";
  private String inputName;
  private String requiredStatus;

  /**
   * @param args A String-String Map with the following:
   *             "Key" maps to the standardized input name.
   *             "InputRequirement" maps to the input status to check for.
   */
  public OogaInputCondition(Map<String,String> args) {
    inputName = args.get(INPUT_NAME_LABEL);
    requiredStatus = args.get(INPUT_REQUIREMENT_LABEL);
  }

  /**
   * Evaluates to true when the specific desired input has the desired status.
   * {@inheritDoc}
   */
  @Override
  public boolean isSatisfied(EntityInternal subject, GameInternal game, Map<String, String> inputs,
      Map<EntityInternal, Map<EntityInternal, String>> collisions) {
    String keyState = inputs.get(inputName);
    return requiredStatus.equals(keyState) ||
        keyAnySatisfied(requiredStatus, keyState) ||
        keyInactiveSatisfied(requiredStatus, keyState);
  }

  //Returns true if the input is not inactive and the requirement accepts any active input state.
  private boolean keyAnySatisfied(String inputType, String keyState) {
    return inputType.equals(ANY_KEY_REQUIREMENT) && keyState != null;
  }

  //Returns true if the input needs to be inactive and is inactive.
  private boolean keyInactiveSatisfied(String inputType, String keyState) {
    return inputType.equals(KEY_INACTIVE_REQUIREMENT) && keyState == null;
  }
}