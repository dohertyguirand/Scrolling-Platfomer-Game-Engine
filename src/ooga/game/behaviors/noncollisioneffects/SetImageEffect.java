package ooga.game.behaviors.noncollisioneffects;

import java.util.Map;
import ooga.Entity;
import ooga.data.ImageEntity;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;
import ooga.game.behaviors.TimeDelayedEffect;

import java.util.List;

public class SetImageEffect extends TimeDelayedEffect {

  String newImageFileName = "";

  public SetImageEffect(List<String> args) throws IndexOutOfBoundsException {
    newImageFileName = args.get(0);
    if(args.size() > 1){
      setTimeDelay(args.get(1));
    }
  }

  /**
   * Checks if the specified data value maps to an entity variable. Changes image to that variable, otherwise to preset value.
   * @param subject     The entity that owns this. This is the entity that should be modified.
   * @param otherEntity entity we are "interacting with" in this effect
   * @param elapsedTime time between steps in ms
   * @param variables   game variables
   * @param game        game instance
   */
  @Override
  protected void doTimeDelayedEffect(Entity subject, Entity otherEntity, double elapsedTime, Map<String, Double> variables, GameInternal game) {
    //TODO: find a better way than using instanceof
    if(subject instanceof ImageEntity){
      ImageEntity imageEntity = (ImageEntity)subject;
      String newImageFilePath = "file:data/games-library/";
      if(subject.getVariable(newImageFileName) != null){
        newImageFilePath += subject.getVariable(newImageFileName);
      } else{
        newImageFilePath += newImageFileName;
      }
      imageEntity.setImageLocation(newImageFilePath);
    }
  }
}
