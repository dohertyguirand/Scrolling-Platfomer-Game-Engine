package ooga.game;

import static org.junit.Assert.assertEquals;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import ooga.Entity;
import ooga.data.ImageEntity;
import org.junit.jupiter.api.Test;

public class OogaLevelTest {

  public static final int NUM_ASSERTS = 10;

  @Test
  void testRemoveEntity() {
    Entity removable = new ImageEntity();
//    Level level = new OogaLevel(List.of(removable));
//    assertEquals(1,level.getEntities().size());
//    level.removeEntity(removable);
//    assertEquals(0,level.getEntities().size());
  }

  @Test
  void testAddEntity() {
    Entity addable = new ImageEntity("add");
//    Level level = new OogaLevel(new ArrayList<>());
//    assertEquals(0,level.getEntities().size());
//    level.addEntity(addable);
//    assertEquals(1,level.getEntities().size());
//    assertEquals(addable.getName(),level.getEntities().get(0).getName());
    for (int i = 0; i < NUM_ASSERTS-1; i ++) {
//      level.addEntity(new ImageEntity());
//      assertEquals(i+2,level.getEntities().size());
    }
    List<Entity> testList = List.of(new ImageEntity(), new ImageEntity());
//    level.addEntities(testList);
//    assertEquals(NUM_ASSERTS+2,level.getEntities().size());
  }

  @Test
  void testLevelId() {
//    Level level = new OogaLevel(new ArrayList<>());
    byte [] rand = new byte[10];
    new Random().nextBytes(rand);
    String randString = new String(rand, StandardCharsets.UTF_8);
//    level.setNextLevelID(randString);
//    assertEquals(randString,level.nextLevelID());
  }
}
