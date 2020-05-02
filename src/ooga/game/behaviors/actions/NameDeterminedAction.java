package ooga.game.behaviors.actions;

import java.util.List;
import java.util.Map;
import ooga.game.EntityInternal;
import ooga.game.GameInternal;
import ooga.game.behaviors.Action;
import ooga.game.behaviors.Effect;

/**
 * @author sam thompson, caryshindell
 * Causes the Effects owned by this Action to have their 'otherEntity' be every active entity
 * with a certain name. Can cause the action to execute many times or no times at all.
 * Dependencies:  Relies on EntityInternal because it returns a List of Entities affected by
 *                the Action's effects.
 *                Relies on Effect because its constructor gives it a built-in list of Effects
 *                that it owns.
 *                Relies on GameInternal to ask for a List of all Entities with a given name.
 * Example: A button might cause all "Door" entities to open in the level.
 */
@SuppressWarnings("unused")
public class NameDeterminedAction extends Action {

  final String myTargetName;

  /**
   * @param args A List whose first element is the Name of the other Entities involved in the
   *             Effects.
   * @param effects The effects that this action executes when it finds its targets.
   */
  public NameDeterminedAction(List<String> args, List<Effect> effects) throws IndexOutOfBoundsException {
    super(effects);
    myTargetName = args.get(0);
  }

  /**
   * @param subject The EntityInternal that owns the behavior with this effect.
   * @param variables The Map of Game variables, mapping variable names to String values.
   * @param collisionInfo Maps EntityInternals with the entities that they collided with
   *                      this frame and the String identifying the direction.
   * @param gameInternal The Game utility class that provides access to game level data
   *                     like the list of entities.
   * @return All active Entities with the name (type) given in the constructor.
   */
  @Override
  public List<EntityInternal> findOtherEntities(EntityInternal subject,
                                        Map<String, String> variables, Map<EntityInternal, Map<String, List<EntityInternal>>> collisionInfo,
                                        GameInternal gameInternal) {
    return gameInternal.getEntitiesWithName(myTargetName);
  }
}
