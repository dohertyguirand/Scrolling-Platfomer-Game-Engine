package ooga.game.behaviors.conditions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import ooga.game.EntityInternal;
import ooga.game.GameInternal;
import ooga.game.behaviors.Condition;

/**
 * @author Sam Thompson
 * Evaluates to true when there exists a collision this game step between two entities with
 * specified names, and the collision is of a specified type (such as Up, Down, Left Right).
 * Can also evaluate to false when such a collision exists, if that is specified.
 * @see Condition
 */
public class OogaCollisionCondition implements Condition {

  //TODO: This doesn't work ALGORITHMICALLY, since it only checks against the collisions of the
  // FIRST ENTITY WITH THE GIVEN NAME.
  public static final String ANY_COLLISION = "ANY";
  private String firstEntity;
  private String secondEntity;
  private String requiredDirection;
  private boolean collisionAllowed;

  public OogaCollisionCondition(String first, String second, String direction, boolean allowed) {
    firstEntity = first;
    secondEntity = second;
    requiredDirection = direction;
    collisionAllowed = allowed;
  }

  /**
   * Evaluates based on whether the specific desired collision exists in the given collisions Map.
   * The required collision occurring could return true or false, depending on whether the Condition
   * allows or bans that collision.
   * {@inheritDoc}
   */
  @Override
  public boolean isSatisfied(EntityInternal subject, GameInternal game, Map<String, String> inputs,
      Map<EntityInternal, Map<EntityInternal, String>> collisions) {
    List<Map<EntityInternal, String>> collisionsWithSubject = getSubjectCollisions(firstEntity,collisions);
    for (Map<EntityInternal,String> collisionsWithIndividual : collisionsWithSubject) {
      if (collisionsMeetRequirement(collisionsWithIndividual)) {
        return collisionAllowed;
      }
    }
    return (!collisionAllowed);
  }

  //Returns true if the Map containing an individual entity's collisions this frame contains
  //the collision that this condition is looking for.
  private boolean collisionsMeetRequirement(Map<EntityInternal, String> collisionsWithIndividual) {
    for (Entry<EntityInternal,String> collision : collisionsWithIndividual.entrySet()) {
      if (secondEntity.equals(collision.getKey().getName()) && directionMatches(requiredDirection,
          collision.getValue())) {
        return true;
      }
    }
    return false;
  }

  //Returns true if the required direction is ANY or if it matches the actual collision direction.
  //Could be inaccurate if 'value' is null, so we make sure to avoid that.
  private boolean directionMatches(String requiredDirection, String value) {
    return (requiredDirection.equals(ANY_COLLISION) || requiredDirection.equals(value));
  }

  //Returns the Map of collisions with an Entity of the given name this game step.
  private List<Map<EntityInternal, String>> getSubjectCollisions(String firstEntity, Map<EntityInternal, Map<EntityInternal, String>> collisions) {
    List<Map<EntityInternal,String>> collisionsWithFirst = new ArrayList<>();
    for (Entry<EntityInternal, Map<EntityInternal, String>> entityCollisions : collisions.entrySet()) {
      if (entityCollisions.getKey().getName().equals(firstEntity)) {
        collisionsWithFirst.add(entityCollisions.getValue());
      }
    }
    return collisionsWithFirst;
  }
}