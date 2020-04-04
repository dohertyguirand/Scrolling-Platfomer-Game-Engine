package ooga;

public interface Entity {

  /**
   * 'Controls' will be a String mapping to a controls type from a shared back end resource file.
   * @param controls
   */
  void reactToControls(String controls);
  void updateSelf(double elapsedTime);
  void handleCollision(String collidingEntity);
}
