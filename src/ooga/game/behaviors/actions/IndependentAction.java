package ooga.game.behaviors.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.Action;
import ooga.game.behaviors.Effect;

/**
 * IndependentAction: no other entity is necessary for the effect
 */
@SuppressWarnings("unused")
public class IndependentAction extends Action {

  public IndependentAction(List<String> args, List<Effect> effects) throws IndexOutOfBoundsException {
    super(effects);
  }

  @Override
  public List<Entity> findOtherEntities(double elapsedTime, Entity subject,
                                        Map<String, String> variables, Map<Entity, Map<String, List<Entity>>> collisionInfo,
                                        GameInternal gameInternal) {
    List<Entity> otherEntities = new ArrayList<>();
    otherEntities.add(null);
    return otherEntities;
  }
}
