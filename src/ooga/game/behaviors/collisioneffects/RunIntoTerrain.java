package ooga.game.behaviors.collisioneffects;

import java.util.List;

import ooga.game.behaviors.TimeDelayedEffect;

public abstract class RunIntoTerrain extends TimeDelayedEffect {

  public RunIntoTerrain(List<String> args) {
    if(args.size() > 0){
      setTimeDelay(args.get(0));
    }
  }
}
