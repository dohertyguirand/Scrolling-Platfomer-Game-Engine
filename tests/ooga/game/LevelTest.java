package ooga.game;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;
import javafx.collections.ObservableList;
import ooga.EntityAPI;
import ooga.data.ImageEntity;
import ooga.game.asyncbehavior.DestroySelfBehavior;
import org.junit.jupiter.api.Test;

public class LevelTest {
  @Test
  void testRemoveEntity() {
    EntityAPI removable = new ImageEntity();
    Level level = new OogaLevel(List.of(removable));
    assertEquals(1,level.getEntities().size());
    level.removeEntity(removable);
    assertEquals(0,level.getEntities().size());
  }
}
