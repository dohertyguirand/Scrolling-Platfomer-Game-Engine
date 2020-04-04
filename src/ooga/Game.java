package ooga;

import java.util.List;

public interface Game {

  List<Entity> getEntities();

  void doGameStart();

  void doCollisionLoop();

  void doUpdateLoop(double elapsedTime);
}
