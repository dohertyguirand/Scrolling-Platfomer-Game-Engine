package ooga.game.behaviors;

import ooga.Entity;
import ooga.game.GameInternal;

import java.util.List;
import java.util.Map;

public abstract class Action {

  public abstract void doAction(double elapsedTime, Entity subject, Map<String, Double> variables,
                       Map<Entity, Map<String, List<Entity>>> collisionInfo, GameInternal gameInternal);
}
