package ooga.game.behaviors.noncollisioneffects;

import ooga.Entity;
import ooga.data.ImageEntity;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResizeImageEffect implements Effect {

  List<String> newImageSizeData = new ArrayList<>();

  public ResizeImageEffect(List<String> args) throws IndexOutOfBoundsException {
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
  public void doEffect(Entity otherEntity, Entity subject, double elapsedTime, Map<String, Double> variables, GameInternal game) {
    //TODO: find a better way than using instanceof
    if(subject instanceof ImageEntity){
      ImageEntity imageEntity = (ImageEntity)subject;
      List<Double> newSize = new ArrayList<>();
      for(String coordinateData : newImageSizeData){
        try{
          newSize.add(Double.parseDouble(coordinateData));
        } catch(NumberFormatException e){
          if(variables.containsKey(coordinateData)){
            newSize.add(variables.get(coordinateData));
          } else if(subject.getVariable(coordinateData) != null){
            try {
              newSize.add(Double.parseDouble(subject.getVariable(coordinateData)));
            } catch(NumberFormatException e2){
              newSize.add(0.0);
            }
          } else{
            newSize.add(0.0);
          }
        }
      }
      subject.setWidth(newSize.get(0));
      subject.setHeight(newSize.get(1));
    }
  }
}
