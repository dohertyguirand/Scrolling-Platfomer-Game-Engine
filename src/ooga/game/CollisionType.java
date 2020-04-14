package ooga.game;

import ooga.Entity;

@FunctionalInterface
public interface CollisionType<T> {
  public boolean isColliding(T target, T with, double time);
}
