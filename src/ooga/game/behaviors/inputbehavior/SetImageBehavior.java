package ooga.game.behaviors.inputbehavior;

import ooga.Entity;
import ooga.data.ImageEntity;
import ooga.game.behaviors.ControlsBehavior;

import java.util.List;

public class SetImageBehavior implements ControlsBehavior {

  String newImageLocation = "";

  public SetImageBehavior(List<String> args){
    if(args.size() >= 1){
      newImageLocation = args.get(0);
    }
  }

  /**
   * Handles reaction to controls. Requires the ControlsBehavior to have a reference to the
   * instance that uses it in order to have an effect on that instance.
   *
   * @param subject The entity that owns this controls behavior. This is the entity that should
   *                be modified.
   */
  @Override
  public void reactToControls(Entity subject) {
    //TODO: find a better way than using instanceof
    if(subject instanceof ImageEntity){
      ImageEntity imageEntity = (ImageEntity)subject;
      imageEntity.setImageLocation(newImageLocation);
    }
  }
}
