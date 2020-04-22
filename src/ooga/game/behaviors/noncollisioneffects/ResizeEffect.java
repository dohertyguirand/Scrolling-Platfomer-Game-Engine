package ooga.game.behaviors.noncollisioneffects;

import ooga.Entity;
import ooga.data.ImageEntity;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;
import ooga.game.behaviors.TimeDelayedEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResizeEffect extends TimeDelayedEffect {

  List<String> newImageSizeData = new ArrayList<>();

  public ResizeEffect(List<String> args) throws IndexOutOfBoundsException {
    newImageSizeData.add(args.get(0));
    newImageSizeData.add(args.get(1));
    if(args.size() > 2){
      setTimeDelay(args.get(2));
    }
  }

  /**
   * Performs the effect
   *
   * @param subject     The entity that owns this. This is the entity that should be modified.
   * @param otherEntity entity we are "interacting with" in this effect
   * @param elapsedTime time between steps in ms
   * @param variables   game variables
   * @param game        game instance
   */
  @Override
  protected void doTimeDelayedEffect(Entity subject, Entity otherEntity, double elapsedTime, Map<String, Double> variables, GameInternal game) {
    subject.setWidth(parseData(newImageSizeData.get(0), subject, variables, subject.getWidth()));
    subject.setHeight(parseData(newImageSizeData.get(1), subject, variables, subject.getHeight()));
  }
}
