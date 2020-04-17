package ooga.game.behaviors.framebehavior;

import java.util.List;
import java.util.Map;

import ooga.Entity;
import ooga.game.behaviors.FrameBehavior;

public class MoveForwardBehavior implements FrameBehavior {


  private Entity myEntity;
  private double xMovePerFrame;
  private double yMovePerFrame;

  public MoveForwardBehavior(List<String> args) {
    xMovePerFrame = Double.parseDouble(args.get(0));
    yMovePerFrame = Double.parseDouble(args.get(1));
  }

  public MoveForwardBehavior(double xmove, double ymove) {
    xMovePerFrame = xmove;
    yMovePerFrame = ymove;
  }

  public MoveForwardBehavior() {
    this(0,0);
  }

  @Override
  public void doFrameUpdate(double elapsedTime, Entity subject, Map<String, Double> variables) {
    subject.move(xMovePerFrame * elapsedTime,yMovePerFrame * elapsedTime);
  }
}
