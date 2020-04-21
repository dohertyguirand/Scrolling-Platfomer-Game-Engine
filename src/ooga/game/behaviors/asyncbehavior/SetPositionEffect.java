package ooga.game.behaviors.asyncbehavior;

import ooga.Entity;
import ooga.game.GameInternal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SetPositionEffect extends DirectionlessCollision {

  private List<String> respawnLocation = new ArrayList<>();

  /**
   * Construct the respawn effect by setting respawnLocation. Note that it adds strings because it could depend on variables.
   * @param args list of arguments from DataReader
   */
  public SetPositionEffect(List<String> args){
    if(args.size() >= 2) {
      respawnLocation.add(args.get(0));
      respawnLocation.add(args.get(1));
    }
  }

  /**
   * Sets the location to the stored location. If stored location is variable dependent, attempts to find a matching game variable.
   *  If there is none, attempts to find a matching entity variable. If there is none, defaults to 0.0.
   * @param subject entity subject
   * @param collidingEntity other entity
   * @param elapsedTime how much time passed
   * @param variables game variables
   * @param game internal game instance
   */
  @Override
  public void doCollision(Entity subject, Entity collidingEntity, double elapsedTime, Map<String, Double> variables, GameInternal game) {
    List<Double> newLocation = new ArrayList<>();
    for(String coordinateData : respawnLocation){
      try{
        newLocation.add(Double.parseDouble(coordinateData));
      } catch(NumberFormatException e){
        if(variables.containsKey(coordinateData)){
          newLocation.add(variables.get(coordinateData));
        } else if(subject.getVariable(coordinateData) != null){
          newLocation.add(subject.getVariable(coordinateData));
        } else{
          newLocation.add(0.0);
        }
      }
    }
    subject.setPosition(newLocation);
  }
}
