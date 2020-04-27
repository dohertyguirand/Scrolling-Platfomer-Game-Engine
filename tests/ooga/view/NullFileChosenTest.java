package ooga.view;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

public class NullFileChosenTest {

private final File file = null;
    @Test
    public void openFileChooser(){
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
        } catch (IllegalArgumentException | IOException e) {
            fail("Should Not Have Shown Exception");
        }
    }
}
