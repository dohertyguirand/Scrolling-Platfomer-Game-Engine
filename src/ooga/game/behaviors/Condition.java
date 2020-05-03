package ooga.game.behaviors;

import java.util.Map;
import ooga.game.EntityInternal;
import ooga.game.GameInternal;

public interface Condition {
  boolean isSatisfied(EntityInternal subject, GameInternal game, Map<String,String> inputs, Map<EntityInternal, Map<EntityInternal, String>> collisions);
}
