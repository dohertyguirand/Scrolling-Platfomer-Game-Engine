package ooga.game.behaviors.noncollisioneffects;

import ooga.game.EntityInternal;
import ooga.game.GameInternal;
import ooga.game.behaviors.TimeDelayedEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class ResizeEffect extends TimeDelayedEffect {

  private List<String> newImageSizeData;

  public ResizeEffect(List<String> args) throws IndexOutOfBoundsException {
    super(args);
  }

  /**
   * Processes the String arguments given in the data file into values used by this effect.
   *
   * @param args The String arguments given for this effect in the data file.
   */
  @Override
  public void processArgs(List<String> args) {
    newImageSizeData = new ArrayList<>();
    newImageSizeData.add(args.get(0));
    newImageSizeData.add(args.get(1));
  }

  /**
   * Performs the effect
   * @param subject     The entity that owns this. This is the entity that should be modified.
   * @param otherEntity entity we are "interacting with" in this effect
   * @param elapsedTime time between steps in ms
   * @param variables   game variables
   * @param game        game instance
   */
  @Override
  protected void doTimeDelayedEffect(EntityInternal subject, EntityInternal otherEntity, double elapsedTime, Map<String, String> variables, GameInternal game) {
    subject.setWidth(parseData(newImageSizeData.get(0), subject, variables, subject.getWidth()));
    subject.setHeight(parseData(newImageSizeData.get(1), subject, variables, subject.getHeight()));
  }
}
