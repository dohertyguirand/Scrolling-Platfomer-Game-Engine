package ooga.game;

import java.util.List;

/**
 * Returns true when two entities have collided with each other at least once.
 */
public class CollisionEndCondition implements LevelEndCondition{

  private final String firstEntityName;
  private final String secondEntityName;
  private boolean satisfied;

  public CollisionEndCondition(List<String> args) {
    firstEntityName = args.get(0);
    secondEntityName = args.get(1);
    satisfied = false;
  }

  @Override
  public boolean isLevelDone() {
    return satisfied;
  }

  @Override
  public void registerCollision(String firstEntity, String secondEntity) {
    satisfied = (firstEntity.equals(firstEntityName) && secondEntity.equals(secondEntityName) ||
        secondEntity.equals(firstEntityName) && firstEntity.equals(secondEntityName));
    if (satisfied) {
      System.out.println("LEVEL CONDITION SATISFIED");
    }
  }
}
