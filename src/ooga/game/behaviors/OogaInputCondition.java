package ooga.game.behaviors;

import java.util.Map;
import ooga.game.EntityInternal;
import ooga.game.GameInternal;

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
  private String inputName;
  private String requiredStatus;

  public OogaInputCondition(String input, String status) {
    inputName = input;
    requiredStatus = status;
  }

  /**
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