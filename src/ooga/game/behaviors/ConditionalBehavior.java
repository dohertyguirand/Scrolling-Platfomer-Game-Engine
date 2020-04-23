package ooga.game.behaviors;

import ooga.Entity;
import ooga.game.GameInternal;

import java.util.List;
import java.util.Map;

/**
 * every behavior executes even if it does nothing
 *   collision behaviors set their value to false if there's no collision happening
 *   same for input behaviors etc
 *   collision behaviors go first, so input behaviors check if collisions are happening but not the other way around
 *   for walking through a door: input behavior checks if collision between door and player is happening. If so it executes the
 *     walk through the door
 *   So we want a new type of behavior, conditional behavior, which has a map of conditionals for each thing it is possible
 *     to depend on (inputs, collisions, variables). It also has a list of behaviors it will execute if the conditionals are true.
 *   Example: Fireboy can only go through door, ending the level, if he is colliding with door and touching ground,
 *     variable red diamonds collected is 5 (or perhaps a dynamic value?),
 *     and user is pressing up key
 *   Method call for this example would look like: new ConditionalBehavior(inputs=(up:true), collisions=(door:true, ground:true),
 *    variables=(red diamonds: 5), behaviors=(EndLevel))
 */
public interface ConditionalBehavior {

  /**
   * So we want a new type of behavior, conditional behavior, which has a map of conditionals for each thing it is possible
   * to depend on (inputs, collisions, variables). It also has a list of behaviors it will execute if the conditionals are true.
   * Example: Fireboy can only go through door, ending the level, if he is colliding with door and touching ground,
   * variable red diamonds collected is 5 (or perhaps a dynamic value?),
   * and user is pressing up key
   * Method call for this example would look like: new ConditionalBehavior(inputs=(up:true), collisions=(door:true, ground:true),
   * variables=(red diamonds: 5), behaviors=(EndLevel))
   * @param elapsedTime time passed in ms
   * @param subject     entity that owns this behavior
   * @param variables   map of game/level variables
   * @param inputs      all registered key inputs at this frame
   * @param collisionInfo Map of maps, direction name : map of collisions for that direction. map of collisions is entity : list of entities
   * @param gameInternal what game this is run from
   */
  public void doConditionalUpdate(double elapsedTime, Entity subject, Map<String, String> variables, List<String> inputs,
                                  Map<Entity, Map<String, List<Entity>>> collisionInfo, GameInternal gameInternal);


  /**
   * executes the actions owned by this behavior
   * @param elapsedTime time in ms
   * @param subject entity that owns this conditional behavior
   * @param variables map of variables in the game/level
   * @param inputs the input keys that are currently active in this frame
   * @param collisionInfo current collision info
   * @param gameInternal what game this is run from
   */
  public void doActions(double elapsedTime, Entity subject, Map<String, String> variables, List<String> inputs,
                        Map<Entity, Map<String, List<Entity>>> collisionInfo, GameInternal gameInternal);
}
