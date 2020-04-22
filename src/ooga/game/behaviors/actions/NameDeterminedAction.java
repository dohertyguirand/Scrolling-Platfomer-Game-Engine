package ooga.game.behaviors.actions;

import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.Action;
import ooga.game.behaviors.Effect;

/**
 * NameDeterminedAction: executes the effect on all entities with the specified name "howToFind"
 */
public class NameDeterminedAction extends Action {

  String myTargetName;

  public NameDeterminedAction(List<String> args, List<Effect> effects) throws IndexOutOfBoundsException {
    super(effects);
    myTargetName = args.get(0);
  }

  @Override
  public List<Entity> findOtherEntities(double elapsedTime, Entity subject,
      Map<String, Double> variables, Map<Entity, Map<String, List<Entity>>> collisionInfo,
      GameInternal gameInternal) {
    return gameInternal.getEntitiesWithName(myTargetName);
  }
}
