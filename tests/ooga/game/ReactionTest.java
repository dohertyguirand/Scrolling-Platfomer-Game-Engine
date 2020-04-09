package ooga.game;

import ooga.data.ImageEntity;
import ooga.game.framebehavior.GravityBehavior;
import ooga.game.framebehavior.MoveUpBehavior;
import ooga.game.inputbehavior.JumpBehavior;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReactionTest {
    @Test
    void testReactions(){
        int numTests = 10;
        double xGrav = 0;
        double yGrav = -10;
        double testElapsedTime = 1.0;
        ImageEntity subject = new ImageEntity();
        subject.setReactionBehavior("ooga/reactions");
        subject.setMovementBehaviors(List.of(new MoveUpBehavior()));
        subject.setReactions(Map.of("Up", List.of(new JumpBehavior(10.0))));
        subject.setPosition(List.of(0.0,100.0));
        subject.react("UpKey", "Surface");
        subject.updateSelf(1.0);
        double expectedVelocity = 10;
        double expectedYPosition = 10;
        System.out.println("exp = " + expectedVelocity);
        System.out.println("expYPos" + expectedYPosition);
        assertEquals(List.of(0.0,110.0),subject.getPosition());
        }
}
