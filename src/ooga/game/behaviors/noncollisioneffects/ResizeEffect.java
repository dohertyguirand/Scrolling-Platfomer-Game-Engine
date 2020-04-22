package ooga.game.behaviors.noncollisioneffects;

import ooga.Entity;
import ooga.data.ImageEntity;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResizeEffect implements Effect {

  List<String> newImageSizeData = new ArrayList<>();

  public ResizeEffect(List<String> args) throws IndexOutOfBoundsException {
    newImageSizeData.add(args.get(0));
    newImageSizeData.add(args.get(1));
  }

  /**
   * Handles reaction to controls. Requires the ControlsBehavior to have a reference to the
   * instance that uses it in order to have an effect on that instance.
   * @param otherEntity
   * @param subject     The entity that owns this controls behavior. This is the entity that should
 *                    be modified.
   * @param elapsedTime
   * @param variables
   * @param game
   */
  @Override
  public void doEffect(Entity subject, Entity otherEntity, double elapsedTime, Map<String, Double> variables, GameInternal game) {
    subject.setWidth(parseData(newImageSizeData.get(0), subject, variables, subject.getWidth()));
    subject.setHeight(parseData(newImageSizeData.get(1), subject, variables, subject.getHeight()));
  }
}
