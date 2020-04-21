package ooga.game.behaviors.asyncbehavior;

import java.util.List;
import java.util.Map;
import ooga.game.GameInternal;
import ooga.Entity;

public abstract class RunIntoTerrain extends QuadDirectionCollision {

  public RunIntoTerrain(List<String> args) {
    //arguments have no effect on this behavior
  }

  public abstract void doCollision(Entity subject, Entity collidingEntity, double elapsedTime,
                             Map<String, Double> variables, GameInternal game);
}
