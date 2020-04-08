package ooga.game;

import java.util.ArrayList;
import java.util.List;
import ooga.EntityAPI;
import ooga.MovementBehavior;
import ooga.game.framebehavior.MoveForwardBehavior;

public class GameBuilder {
  public Game buildGame() {
    List<EntityAPI> entities = new ArrayList<>();
    MovementBehavior birdMovement = new MoveForwardBehavior();
    EntityAPI bird = new OogaEntity(birdMovement);
    entities.add(bird);
//    return new OogaGame();
    return null;
  }
}
