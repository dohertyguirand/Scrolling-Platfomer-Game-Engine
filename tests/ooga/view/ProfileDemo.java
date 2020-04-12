package ooga.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;

public class ProfileDemo extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ViewProfile profile = new ViewProfile();
        profile.setProfileName("Testing");
        profile.setStats(new HashMap<>(){{
            put("SuperMario", 100);
            put("Dino", 1000000);
            put("Doodle Jump", 3000);
        }});
        Scene scene = new Scene(profile.getPane());
        stage.setScene(scene);
        stage.show();
    }
}
