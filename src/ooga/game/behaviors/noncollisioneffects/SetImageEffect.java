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
    super(args);
  }

  /**
   * Processes the String arguments given in the data file into values used by this effect.
   *
   * @param args The String arguments given for this effect in the data file.
   */
  @Override
  public void processArgs(List<String> args) {
    newImageFileName = args.get(0);
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
  public void doTimeDelayedEffect(Entity subject, Entity otherEntity, double elapsedTime, Map<String, Double> variables, GameInternal game) {
    setImage(subject, newImageFileName);
  }

  public static void setImage(Entity subject, String newImageFileName) {
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
