package ooga.game.behaviors;

import ooga.Entity;

import java.util.Map;

/**
 * Handles per-frame actions that an entity does regardless of triggers like collisions
 * or input.
 * Must have access to the updating entity if it wants to make changes, and this access
 * must come from the constructor.
 * Example: Goomba moving forward.
 */
public interface FrameBehavior {

  /**
   * Performs the subclass-specific implementation that happens per frame.
   * @param elapsedTime The time since the previous frame.
   * @param subject The entity to perform the update upon.
   * @param variables map of variables
   */
  void doFrameUpdate(double elapsedTime, Entity subject, Map<String, Double> variables);
}
