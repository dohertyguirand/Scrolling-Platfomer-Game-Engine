package ooga.game.collisiondetection;

import static org.junit.jupiter.api.Assertions.*;

import ooga.Entity;
import ooga.data.ImageEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DirectionalCollisionDetectorTest {

  final double ELAPSED_TIME = 1.0;

  DirectionalCollisionDetector detector;
  Entity a;
  Entity b;
  Entity c;
  Entity d;

  @BeforeEach
  void setUp() {
    detector = new DirectionalCollisionDetector();
    a = new ImageEntity("a","",0.0,0.0,9.0,9.0);
    b = new ImageEntity("a","",10.0,0.0,9.0,9.0);
    c = new ImageEntity("a","",0.0,10.0,9.0,9.0);
    d = new ImageEntity("a","",10.0,10.0,9.0,9.0);
  }


  @Test
  void testGetCollisionDirection() {
    a.setVelocity(1.1,0.0);
    assertEquals("Right",detector.getCollisionDirection(a,b,ELAPSED_TIME));
    assertEquals("Left",detector.getCollisionDirection(b,a,ELAPSED_TIME));
    assertNull(detector.getCollisionDirection(d, a, ELAPSED_TIME));

    a.setVelocity(0.0,1.1);
    assertEquals("Down",detector.getCollisionDirection(a,c,ELAPSED_TIME));
    assertEquals("Up",detector.getCollisionDirection(c,a,ELAPSED_TIME));
    assertNull(detector.getCollisionDirection(d, a, ELAPSED_TIME));
  }
}