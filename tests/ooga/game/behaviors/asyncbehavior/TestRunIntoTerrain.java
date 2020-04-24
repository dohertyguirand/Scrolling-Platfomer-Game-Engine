package ooga.game.behaviors.asyncbehavior;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import ooga.Entity;
import ooga.data.ImageEntity;
import org.junit.jupiter.api.Test;

public class TestRunIntoTerrain {

  @Test
  void testHorizontalCollision() {
//    CollisionBehavior terrainBehavior = new RunIntoTerrain(new ArrayList<>());
    final double ELAPSED_TIME = 1.0;
    Entity a = new ImageEntity("a");
    a.setWidth(10.0);
    a.setPosition(List.of(0.0,0.0));
    Entity b = new ImageEntity("b");
    b.setWidth(11.0);
    b.setPosition(List.of(a.getWidth()+0.1,0.0));
    a.setVelocity(1.0,0.0);
//    terrainBehavior.doHorizontalCollision(a,b,ELAPSED_TIME,new HashMap<>(),null);
//    a.updateSelf(ELAPSED_TIME);
//    b.updateSelf(ELAPSED_TIME);
    assertEquals((Double)0.0,a.getVelocity().get(0));
    assertEquals((Double)0.0,a.getVelocity().get(0));
  }

  @Test
  void testVerticalCollision() {
//    CollisionBehavior terrainBehavior = new RunIntoTerrain(new ArrayList<>());
    final double ELAPSED_TIME = 1.0;
    Entity a = new ImageEntity("a");
    a.setHeight(10.0);
    a.setPosition(List.of(0.0,0.0));
    Entity b = new ImageEntity("b");
    b.setHeight(11.0);
    b.setPosition(List.of(0.0,a.getHeight()+0.1));
    a.setVelocity(1.0,1.0);
//    terrainBehavior.doVerticalCollision(a,b,ELAPSED_TIME,new HashMap<>(),null);
//    a.updateSelf(ELAPSED_TIME);
//    b.updateSelf(ELAPSED_TIME);
    assertEquals((Double)0.0,a.getVelocity().get(1));
    assertEquals((Double)0.0,a.getVelocity().get(1));
  }
}
