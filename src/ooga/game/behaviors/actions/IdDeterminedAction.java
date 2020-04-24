package ooga.game.behaviors.actions;

import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.Action;
import ooga.game.behaviors.Effect;

//  IDDeterminedAction: executes the effect on the entity with the specified ID
@SuppressWarnings("unused")
@Deprecated
public class IdDeterminedAction extends Action {

  private final String targetID;

  public IdDeterminedAction(List<String> args, List<Effect> effects) throws IndexOutOfBoundsException {
    super(effects);
    targetID = args.get(0);
  }

  @Override
  public List<Entity> findOtherEntities(Entity subject,
                                        Map<String, String> variables, Map<Entity, Map<String, List<Entity>>> collisionInfo,
                                        GameInternal gameInternal) {
    Entity target = gameInternal.getEntityWithId(targetID);
    return List.of(target);
  }
}
