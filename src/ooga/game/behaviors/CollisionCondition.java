package ooga.game.behaviors;

import java.util.Map;
import java.util.Map.Entry;
import ooga.game.EntityInternal;
import ooga.game.GameInternal;

public class CollisionCondition implements Condition {

  //TODO: Revisit this and make it abstract so it can be implemented by
  // BannedCollisionCondition and RequiredCollisionCondition
  public static final String ANY_COLLISION = "ANY";
  private String firstEntity;
  private String secondEntity;
  private String requiredDirection;

  public CollisionCondition(String firstEntity, String secondEntity, String requiredDirection) {

  }

  @Override
  public boolean isSatisfied(EntityInternal subject, GameInternal game, Map<String, String> inputs,
      Map<EntityInternal, Map<EntityInternal, String>> collisions) {
    Map<EntityInternal, String> collisionsWithSubject = getSubjectCollisions(firstEntity,collisions);
    if (collisionsWithSubject == null) {
      return false;
    }
    for (Entry<EntityInternal,String> collision : collisionsWithSubject.entrySet()) {
      if (secondEntity.equals(collision.getKey().getName()) && directionMatches(requiredDirection,
          collision.getValue())) {
        return true;
      }
    }
    return false;
  }

  private boolean directionMatches(String requiredDirection, String value) {
    return (requiredDirection.equals(ANY_COLLISION) || requiredDirection.equals(value));
  }

  private Map<EntityInternal, String> getSubjectCollisions(String firstEntity, Map<EntityInternal, Map<EntityInternal, String>> collisions) {
    //TODO: NOTE, MAYBE GO OVER ENTRY SET?
    for (EntityInternal collidingEntity : collisions.keySet()) {
      if (collidingEntity.getName().equals(firstEntity)) {
        return collisions.get(collidingEntity);
      }
    }
    return null;
  }
}
