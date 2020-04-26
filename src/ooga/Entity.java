package ooga;

import java.util.List;
import java.util.Map;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;

/**
 * @author sam thompson, caryshindell, doherty guirand, braeden ward
 * Represents any in-game object that has a physical place in the level.
 * Examples include terrain tiles, the player in a platformer, or an enemy.
 * Relies on access to the list of all entities if it wants to do
 * anything besides affect itself.
 */
public interface Entity {

  String textEntityType = "text";
  String imageEntityType = "image";

  /**
   * is it an image entity or text entity
   * @return string "image" or "text" or something else if new entity classes are created
   */
  String getEntityType();

  DoubleProperty nonStationaryProperty();

  DoubleProperty xProperty();

  DoubleProperty yProperty();

  BooleanProperty activeInViewProperty();

  void setActiveInView(boolean activeInView);

  /**
   * @return The width of the entity.
   */
  double getWidth();

  /**
   * @return The height of the entity.
   */
  double getHeight();

  /**
   * @return The name identifying what type of entity this is, as defined in the game file.
   */
  String getName();
  
  /**
   * @return The X and Y position of the Entity, in that order.
   */
  List<Double> getPosition();

  /**
   * @return The X and Y velocity of the Entity, in that order.
   */
  List<Double> getVelocity();

  /**
   * @return True if this entity has been destroyed and should be removed.
   */
  boolean isDestroyed();

  String getEntityID();

  Map<String,String> getVariables();

  /**
   * creates the double property stationaryProperty for the entity. 0 if false, 1 if true.
   * @param stationary
   */
  void makeNonStationaryProperty(boolean stationary);
}
