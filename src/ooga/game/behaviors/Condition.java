package ooga.game.behaviors;

import java.util.Map;
import ooga.game.EntityInternal;
import ooga.game.GameInternal;

/**
 * @author Sam Thompson
 * Represents some kind of predicate that must be true in order for the Effects of a Behavior
 * to execute. Is owned by a Behavior for assessment each game step.
 * For Reflection: Each concrete implementation should have a constructor that takes in a
 *              Map of String,String pairs as arguments and throw an exception if it is invalid.
 * Dependencies:  Relies on EntityInternal for access to information about them, such as their Map
 *                of variables, or their position.
 *                Relies on GameInternal for access to game-wide information and variables.
 * Examples:  1. The Game variable 'Lives' must be less than 1.
 *            2. There must not be any Koopa colliding downwards with a Button.
 */
public interface Condition {

  /**
   * @param subject The Entity whose Behavior owns this Condition.
   * @param game The GameInternal that this Entity is part of (that it will use for checking
   *             game-wide information).
   * @param inputs The Map with String keys representing the standardized key code of an input
   *               and the String values representing their status this game step.
   * @param collisions The Map where EntityInternal keys are each active Entity and the
   *                   Map values map each Entity they are colliding with to a String representing
   *                   the collision type (direction).
   * @return True if the condition is met this game step. Generally, if all of a Behavior's Conditions
   * assess to true, then a Behavior's Effects are executed.
   */
  boolean isSatisfied(EntityInternal subject, GameInternal game, Map<String,String> inputs, Map<EntityInternal, Map<EntityInternal, String>> collisions);
}