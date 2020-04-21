package ooga.game.behaviors.asyncbehavior;

import java.util.List;
import java.util.Map;
import ooga.game.GameInternal;
import ooga.Entity;
import ooga.game.behaviors.CollisionEffect;

public abstract class RunIntoTerrain implements CollisionEffect {

  public RunIntoTerrain(List<String> args) {
    //arguments have no effect on this behavior
  }

  public abstract void doCollision(Entity subject, Entity collidingEntity, double elapsedTime,
                             Map<String, Double> variables, GameInternal game);
}
