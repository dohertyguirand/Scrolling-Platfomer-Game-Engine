package ooga.game.behaviors.actions;

import ooga.game.EntityInternal;
import ooga.game.GameInternal;
import ooga.game.behaviors.Action;
import ooga.game.behaviors.Effect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class CollisionDeterminedAction extends Action {

  private final String direction;
  private final String collidingEntityInfo;
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
  public List<EntityInternal> findOtherEntities(EntityInternal subject, Map<String, String> variables, Map<EntityInternal, Map<String, List<EntityInternal>>> collisionInfo, GameInternal gameInternal) {
    List<EntityInternal> otherEntities = new ArrayList<>();
    Map<String, List<EntityInternal>> myCollisionInfo = collisionInfo.get(subject);
    if(direction.equals(ANY)){
      for(String possibleDirection : myCollisionInfo.keySet()){
        otherEntities.addAll(getCollidingEntities(myCollisionInfo.get(possibleDirection)));
      }
      return otherEntities;
    }
    otherEntities.addAll(getCollidingEntities(myCollisionInfo.get(direction)));
    return otherEntities;
  }

  private List<EntityInternal> getCollidingEntities(List<EntityInternal> collidingWithList) {
    List<EntityInternal> otherEntities = new ArrayList<>();
    for(EntityInternal collidingWith : collidingWithList){
      if(entityMatches(collidingEntityInfo, collidingWith)){
        otherEntities.add(collidingWith);
      }
    }
    return otherEntities;
  }

  private boolean entityMatches(String entity1Info, EntityInternal entity) {
    return entity.getName().equals(entity1Info) ||
            (entity.getVariable("TerrainID") != null && entity.getVariable("TerrainID").equals(entity1Info));
  }
}
