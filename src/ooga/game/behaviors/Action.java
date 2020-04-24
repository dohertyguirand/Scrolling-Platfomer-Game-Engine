package ooga.game.behaviors;

import ooga.Entity;
import ooga.game.GameInternal;

import java.util.List;
import java.util.Map;

public abstract class Action {

  final List<Effect> myEffects;

  public Action(List<Effect> effects){
    myEffects = effects;
  }

  public void doAction(double elapsedTime, Entity subject, Map<String, String> variables,
                       Map<Entity, Map<String, List<Entity>>> collisionInfo, GameInternal gameInternal) {
    List<Entity> otherEntities = findOtherEntities(elapsedTime,subject,variables,collisionInfo,gameInternal);
    for (Entity e : otherEntities) {
      doEffects(elapsedTime,subject,e,variables,gameInternal);
    }
  }

  public abstract List<Entity> findOtherEntities(double elapsedTime, Entity subject, Map<String, String> variables,
                                                 Map<Entity, Map<String, List<Entity>>> collisionInfo, GameInternal gameInternal);

  protected void doEffects(double elapsedTime, Entity subject, Entity otherEntity, Map<String, String> variables, GameInternal gameInternal){
    for(Effect effect : myEffects){
      effect.doEffect(subject, otherEntity, elapsedTime, variables, gameInternal);
    }
  }
}
