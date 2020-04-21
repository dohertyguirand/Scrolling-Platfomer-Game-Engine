package ooga.game.behaviors.actions;

import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.Action;
import ooga.game.behaviors.Effect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CollisionDeterminedAction extends Action {

  private String direction;
  private String collidingEntityInfo;
  public static final String ANY = "ANY";

  /**
   * make a CollisionDeterminedAction
   * @param args should be colliding entity info (name or ID), direction
   * @param effects list of effects to execute
   * @throws IndexOutOfBoundsException if the args list is too short
   */
  public CollisionDeterminedAction(List<String> args, List<Effect> effects) throws IndexOutOfBoundsException{
    super(effects);
    collidingEntityInfo = args.get(0);
    direction = args.get(1);
  }

  @Override
  public List<Entity> findOtherEntities(double elapsedTime, Entity subject, Map<String, Double> variables, Map<Entity, Map<String, List<Entity>>> collisionInfo, GameInternal gameInternal) {
    List<Entity> otherEntities = new ArrayList<>();
    Map<String, List<Entity>> myCollisionInfo = collisionInfo.get(subject);
    if(direction.equals(ANY)){
      for(String possibleDirection : myCollisionInfo.keySet()){
        otherEntities.addAll(getCollidingEntities(myCollisionInfo.get(possibleDirection)));
      }
      return otherEntities;
    }
    otherEntities.addAll(getCollidingEntities(myCollisionInfo.get(direction)));
    return otherEntities;
  }

  private List<Entity> getCollidingEntities(List<Entity> collidingWithList) {
    List<Entity> otherEntities = new ArrayList<>();
    for(Entity collidingWith : collidingWithList){
      if(entityMatches(collidingEntityInfo, collidingWith)){
        otherEntities.add(collidingWith);
      }
    }
    return otherEntities;
  }

  private boolean entityMatches(String entity1Info, Entity entity) {
    return entity.getName().equals(entity1Info) || entity.getVariable("ID").equals(entity1Info);
  }
}
