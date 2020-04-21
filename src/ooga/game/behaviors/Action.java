package ooga.game.behaviors;

import ooga.Entity;
import ooga.game.GameInternal;

import java.util.List;
import java.util.Map;

public abstract class Action {

  List<Effect> myEffects;

  public Action(List<Effect> effects){
    myEffects = effects;
  }

  public abstract void doAction(double elapsedTime, Entity subject, Map<String, Double> variables,
                       Map<Entity, Map<String, List<Entity>>> collisionInfo, GameInternal gameInternal);

  protected void doEffects(double elapsedTime, Entity subject, Entity otherEntity, Map<String, Double> variables, GameInternal gameInternal){
    for(Effect effect : myEffects){
      effect.doEffect(subject, otherEntity, elapsedTime, variables, gameInternal);
    }
  }
}
