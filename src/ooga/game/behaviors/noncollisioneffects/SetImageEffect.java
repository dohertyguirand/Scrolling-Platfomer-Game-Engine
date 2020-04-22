package ooga.game.behaviors.noncollisioneffects;

import java.util.Map;
import ooga.Entity;
import ooga.data.ImageEntity;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;

import java.util.List;

public class SetImageEffect implements Effect {

  String newImageFileName = "";

  public SetImageEffect(List<String> args) throws IndexOutOfBoundsException {
    newImageFileName = args.get(0);
  }

  /**
   * Checks if the specified data value maps to an entity variable. Changes image to that variable, otherwise to preset value.
   * @param otherEntity
   * @param subject The entity that owns this controls behavior. This is the entity that should
 *                be modified.
   * @param elapsedTime
   * @param variables
   * @param game
   */
  @Override
  public void doEffect(Entity subject, Entity otherEntity, double elapsedTime, Map<String, Double> variables,
                       GameInternal game) {
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
