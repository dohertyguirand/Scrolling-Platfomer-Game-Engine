// Handles both 'banned collision conditions' and 'required collision conditions'.
// It borrows much of the algorithm from BehaviorInstance's collision condition checking,
// which is exactly what is needed, since BehaviorInstance shouldn't have to worry about those
// implementation details. This class also relies on a different representation of collisions,
// where an Entity is mapped to its collisions, and its collisions are themselves a Map of
// String-EntityInternal pairs representing who is being collided with and in what direction.
// This representation was slightly more readable to navigate.
// It handles not being given anything with a 'Banned' tag, since 'Banned' isn't listed in the
// properties file (which means it's not required, assuming that the Data module uses the
// properties file). It handles this just by using 'true' for collisionAllowed, which is
// intuitive.

package ooga.game.behaviors.conditions;

import java.util.ArrayList;
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
 * NOTE: These collisions may not be symmetrical. For example, if Mario is colliding downward with
 * a Koopa, the Koopa is NOT colliding downward with Mario.
 * @see Condition
 */
public class OogaCollisionCondition implements Condition {

  public static final String ANY_COLLISION = "ANY";
  public static final String FIRST_ENTITY_LABEL = "FirstEntity";
  public static final String SECOND_ENTITY_LABEL = "SecondEntity";
  public static final String COLLISION_TYPE_LABEL = "Direction";
  public static final String BANNED_LABEL = "Banned";
  private String firstEntity;
  private String secondEntity;
  private String requiredDirection;
  private boolean collisionAllowed;

  /**
   * @param args A String-String Map with the following:
   *             "FirstEntity" maps to the name of the first entity in the collision.
   *             "SecondEntity" maps to the name of the second entity in the collision.
   *             "Direction" maps to the direction (or more generally, 'type') of collision.
   *             "Banned" maps to a Boolean saying whether
   */
  public OogaCollisionCondition(Map<String,String> args) {
    firstEntity = args.get(FIRST_ENTITY_LABEL);
    secondEntity = args.get(SECOND_ENTITY_LABEL);
    requiredDirection = args.get(COLLISION_TYPE_LABEL);
    collisionAllowed = !(Boolean.parseBoolean(args.get(BANNED_LABEL)));
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