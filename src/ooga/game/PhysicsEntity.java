package ooga.game;

import java.util.List;

public interface PhysicsEntity {
  void setVelocity(List<Double> velocityVector);

  void setAcceleration(List<Double> accelVector);

  void updateSelf(double elapsedTime);
}
