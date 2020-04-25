package ooga.game.behaviors;

import ooga.game.EntityInternal;
import ooga.game.GameInternal;

import java.util.List;
import java.util.Map;

public abstract class Action {

  final List<Effect> myEffects;

  public Action(List<Effect> effects){
    myEffects = effects;
  }

  public void doAction(double elapsedTime, EntityInternal subject, Map<String, String> variables,
                       Map<EntityInternal, Map<String, List<EntityInternal>>> collisionInfo, GameInternal gameInternal) {
    List<EntityInternal> otherEntities = findOtherEntities(subject,variables,collisionInfo,gameInternal);
    for (EntityInternal e : otherEntities) {
      doEffects(elapsedTime,subject,e,variables,gameInternal);
    }
  }

  public abstract List<EntityInternal> findOtherEntities(EntityInternal subject, Map<String, String> variables,
                                                 Map<EntityInternal, Map<String, List<EntityInternal>>> collisionInfo, GameInternal gameInternal);

  protected void doEffects(double elapsedTime, EntityInternal subject, EntityInternal otherEntity, Map<String, String> variables, GameInternal gameInternal){
    for(Effect effect : myEffects){
      effect.doEffect(subject, otherEntity, elapsedTime, variables, gameInternal);
    }
  }
}
