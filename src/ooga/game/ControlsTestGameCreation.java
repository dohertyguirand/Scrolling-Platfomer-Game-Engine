package ooga.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.ControlsBehavior;
import ooga.Entity;
import ooga.data.ImageEntity;
import ooga.game.asyncbehavior.DestroySelfBehavior;
import ooga.game.asyncbehavior.StopDownwardVelocity;
import ooga.game.framebehavior.GravityBehavior;
import ooga.game.framebehavior.MoveForwardBehavior;
import ooga.game.inputbehavior.JumpBehavior;
import ooga.game.inputbehavior.MoveInputBehavior;

public class ControlsTestGameCreation {
  //TODO: Remove, or transform into a reasonable test
  public static OogaGame getGame() {
    List<Entity> entities = new ArrayList<>();
    Entity sampleEntity = new ImageEntity("dino", "file:data/games-library/example-dino/googe_dino.bmp");
    sampleEntity.setMovementBehaviors(List.of(new GravityBehavior(0,100.0/1000)));
    double speed = 8;
    Map<String, List<ControlsBehavior>> controls = new HashMap<>();
    controls.put("UpKey",List.of(new JumpBehavior(-1 * 0.125 * speed)));
    controls.put("DownKey",List.of(new MoveInputBehavior(0,speed)));
    controls.put("LeftKey",List.of(new MoveInputBehavior(-1 * speed,0)));
    controls.put("RightKey",List.of(new MoveInputBehavior(speed,0)));
    sampleEntity.setControlsBehaviors(controls);
    sampleEntity.setCollisionBehaviors(Map.of("cactus",List.of(new StopDownwardVelocity())));
    sampleEntity.setPosition(List.of(400.0-300,400.0));
    entities.add(sampleEntity);

    for (int i = 0; i < 10; i ++) {
      Entity otherEntity = new ImageEntity("cactus","file:data/games-library/example-dino/cactus.jpeg");
//      otherEntity.setMovementBehaviors(List.of(new MoveForwardBehavior(-450.0/1000,0)));
      otherEntity.setPosition(List.of(100 + 100.0 * i,600.0));
      entities.add(otherEntity);
    }

    return new OogaGame(new OogaLevel(entities));
  }
}
