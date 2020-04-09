package ooga.game;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import ooga.Entity;
import ooga.UserInputListener;

public class OogaCollisionDetector implements CollisionDetector, UserInputListener {

  @Override
  public boolean isColliding(Entity a, Entity b) {
    return (makeShapeFromEntity(a).getBoundsInParent().intersects(makeShapeFromEntity(b).getBoundsInParent()));
  }

  private Shape makeShapeFromEntity(Entity e) {
    Shape s = new Rectangle( e.getPosition().get(0),e.getPosition().get(1),
        e.getWidth(), e.getHeight());
    System.out.println("e.getWidth() = " + e.getWidth());
    System.out.println("s.getBoundsInParent().getWidth() = " + s.getBoundsInParent().getWidth());
    return new Rectangle( e.getPosition().get(0),e.getPosition().get(1),
                          e.getWidth(), e.getHeight());
  }

  @Override
  public void reactToRightButton() {

  }

  @Override
  public void reactToLeftButton() {

  }

  @Override
  public void reactToUpButton() {

  }

  @Override
  public void reactToDownButton() {

  }

  @Override
  public void reactToActionButton() {

  }

  @Override
  public void reactToMouseClick(double mouseX, double mouseY) {

  }

  @Override
  public void reactToButton(String buttonID) {

  }

  @Override
  public void reactToKeyPress(String keyName) {

  }

  @Override
  public void reactToGameSelect(String filePath) {

  }

  @Override
  public void reactToGameSave() {

  }

  @Override
  public void reactToGameQuit() {

  }

  @Override
  public void reactToPauseButton(boolean paused) {

  }
}
