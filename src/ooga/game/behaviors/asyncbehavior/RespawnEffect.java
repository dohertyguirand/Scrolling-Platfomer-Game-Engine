package ooga.game.behaviors.asyncbehavior;

import ooga.Entity;
import ooga.game.GameInternal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RespawnEffect extends DirectionlessCollision {

  private List<Double> respawnLocation = new ArrayList<>();

  public RespawnEffect(List<String> args){
    if(args.size() >= 2) {
      respawnLocation.add(Double.parseDouble(args.get(0)));
      respawnLocation.add(Double.parseDouble(args.get(1)));
    } else{
      respawnLocation = List.of(0.0 ,0.0);
    }
  }

  @Override
  public void doCollision(Entity subject, Entity collidingEntity, double elapsedTime, Map<String, Double> variables, GameInternal game) {
    subject.setPosition(respawnLocation);
  }
}
