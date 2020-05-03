package ooga.game.behaviors.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ooga.game.EntityInternal;
import ooga.game.GameInternal;
import ooga.game.behaviors.Action;
import ooga.game.behaviors.Effect;

/**
 * @author sam thompson, caryshindell
 * Represents a set of Effects that only need one Entity (the Entity who owns the Behavior) in
 * order to work.
 * Dependencies:  Relies on EntityInternal because it returns a List of Entities affected by
 *                the Action's effects.
 *                Relies on Effect because its constructor gives it a built-in list of Effects
 *                that it owns.
 * Example: Mario colliding with a Goalpost might end the level, which doesn't modify any Entities
 * or require any additional Entity-based information.
 */
@SuppressWarnings("unused")
public class IndependentAction extends Action {
  /**
   * @param args Unused except for standardizing the format of Actions for reflection.
   * @param effects The effects that this action executes when it finds its targets.
   */
  public IndependentAction(List<String> args, List<Effect> effects) throws IndexOutOfBoundsException {
    super(effects);
  }

  /**
   * @param subject The EntityInternal that owns the behavior with this effect.
   * @param variables The Map of Game variables, mapping variable names to String values.
   * @param collisionInfo Maps EntityInternals with the entities that they collided with
   *                      this frame and the String identifying the direction.
   * @param gameInternal The Game utility class that provides access to game level data
   *                     like the list of entities.
   * @return A List containing null, since there are no otherEntities required for these effects.
   */
  @Override
  public List<EntityInternal> findOtherEntities(EntityInternal subject,
                                        Map<String, String> variables, Map<EntityInternal, Map<String, List<EntityInternal>>> collisionInfo,
                                        GameInternal gameInternal) {
    List<EntityInternal> otherEntities = new ArrayList<>();
    otherEntities.add(null);
    return otherEntities;
  }
}
