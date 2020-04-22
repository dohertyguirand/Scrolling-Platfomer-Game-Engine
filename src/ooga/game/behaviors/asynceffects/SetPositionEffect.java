package ooga.game.behaviors.asynceffects;

import ooga.Entity;
import ooga.game.GameInternal;
import ooga.game.behaviors.Effect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SetPositionEffect implements Effect {

  private List<String> desiredLocation = new ArrayList<>();

  /**
   * Construct the set position effect by setting desiredLocation. Note that it adds strings because it could depend on variables.
   * @param args list of arguments from DataReader
   */
  public SetPositionEffect(List<String> args) throws IndexOutOfBoundsException{
    desiredLocation.add(args.get(0));
    desiredLocation.add(args.get(1));
  }

  /**
   * Sets the location to the stored location. If stored location is variable dependent, attempts to find a matching game variable.
   *  If there is none, attempts to find a matching entity variable. If there is none, defaults to 0.0.
   * @param subject entity subject
   * @param otherEntity other entity
   * @param elapsedTime how much time passed
   * @param variables game variables
   * @param game internal game instance
   */
  @Override
  public void doEffect(Entity subject, Entity otherEntity, double elapsedTime, Map<String, Double> variables, GameInternal game) {
    List<Double> newLocation = new ArrayList<>();
    for(String coordinateData : desiredLocation){
      newLocation.add(parseData(coordinateData, subject, variables, 0.0));
    }
    subject.setPosition(newLocation);
  }
}
