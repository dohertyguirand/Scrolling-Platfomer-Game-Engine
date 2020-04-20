package ooga.game.behaviors;

import java.util.Map;
import ooga.Entity;
import ooga.game.GameInternal;

/**
 * Determines how an entity reacts to in-game user input of any kind.
 * Example: In Super Mario Bros, Mario can be moved with left and right keys.
 * Relies on a mapping (usually data-driven) between Strings and types of inputs.
 */
public interface NonCollisionEffect {

  /**
   * Handles reaction to controls. Requires the ControlsBehavior to have a reference to the
   * instance that uses it in order to have an effect on that instance.
   * @param elapsedTime
   * @param subject The entity that owns this controls behavior. This is the entity that should
   *                be modified.
   * @param variables
   * @param game
   */
  void doEffect(double elapsedTime, Entity subject,
      Map<String, Double> variables, GameInternal game);

}
