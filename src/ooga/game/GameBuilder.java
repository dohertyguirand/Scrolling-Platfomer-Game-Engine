package ooga.game;

import java.util.ArrayList;
import java.util.List;
import ooga.Entity;
import ooga.MovementBehavior;
import ooga.game.framebehavior.MoveForwardBehavior;

public class GameBuilder {
  public Game buildGame() {
    List<Entity> entities = new ArrayList<>();
    MovementBehavior birdMovement = new MoveForwardBehavior();
    Entity bird = new OogaEntity(birdMovement);
    entities.add(bird);
    return new OogaGame();
  }
}
