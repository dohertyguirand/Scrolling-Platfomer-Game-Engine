package ooga.game.behaviors.noncollisioneffects;

import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.TimeDelayedEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SetPositionEffect extends TimeDelayedEffect {

  private List<String> desiredLocation = new ArrayList<>();

  /**
   * Construct the set position effect by setting desiredLocation. Note that it adds strings because it could depend on variables.
   * @param args list of arguments from DataReader
   */
  public SetPositionEffect(List<String> args) throws IndexOutOfBoundsException{
    desiredLocation.add(args.get(0));
    desiredLocation.add(args.get(1));
    if(args.size() > 2){
      setTimeDelay(args.get(2));
    }
  }

  /**
   * Sets the location to the stored location. If stored location is variable dependent, attempts to find a matching game variable.
   * If there is none, attempts to find a matching entity variable. If there is none, defaults to 0.0.
   * @param subject     The entity that owns this. This is the entity that should be modified.
   * @param otherEntity entity we are "interacting with" in this effect
   * @param elapsedTime time between steps in ms
   * @param variables   game variables
   * @param game        game instance
   */
  @Override
  protected void doTimeDelayedEffect(Entity subject, Entity otherEntity, double elapsedTime, Map<String, Double> variables, GameInternal game) {
    List<Double> newLocation = new ArrayList<>();
    for(String coordinateData : desiredLocation){
      newLocation.add(parseData(coordinateData, subject, variables, 0.0));
    }
    subject.setPosition(newLocation);
  }
}
