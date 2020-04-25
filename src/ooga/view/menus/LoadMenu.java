package ooga.view.menus;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import ooga.OogaDataException;
import ooga.data.gamedatareaders.GameDataReaderExternal;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LoadMenu extends OptionMenu {

    public static final String NEW_GAME = "NewGame";
    public static final String SAVED_GAME_ERROR = "SavedGameError";
    private StringProperty dateSelected = new SimpleStringProperty();
    private String ERROR_MESSAGE;
    private String NEWGAME;

    /**
     * View where user can decide to load a saved game or a new game
     * @param gamename - name of game selected, used to find past saves
     * @param profilename - user's profile name, used to find saved games
     * @param reader - DataReader used to get past saves from data
     * @param backButton - button that allows user to get back to startmenu screen
     */
    public LoadMenu(ResourceBundle languageResources, String gamename, String profilename, GameDataReaderExternal reader, Node backButton){
        super(languageResources, gamename);
        NEWGAME = languageResources.getString(NEW_GAME);
        ERROR_MESSAGE = languageResources.getString(SAVED_GAME_ERROR);
        this.setLeft(setMenuItems(createButtons(backButton, reader,profilename,gamename)));
    }

    /**
     * User chooses to either load a saved game or enter a new game
     * @return String Property containing the date of the game selected. If new game, returns an empty string.
     */
    public StringProperty getDateSelected() {
        return dateSelected;
    }

    private Node makeButton(String date, String text){
        Button button = new Button(text);
        button.setOnAction(e-> setDateSelected(date));
        return new Button(text);
    }

    private void setDateSelected(String optionSelected) {
        this.dateSelected.set(null);
        this.dateSelected.set(optionSelected);
    }

    private List<Node> createButtons(Node backButton, GameDataReaderExternal dataReader, String profileName, String gameName) {
        List<Node> buttons = new ArrayList<>();
        Button button = new Button(NEWGAME);
        button.setOnAction(e-> setDateSelected("  "));
       buttons.add(button);
//        try {
//            for(List<String> dates: dataReader.getGameSaves(profileName,gameName) ){
//              buttons.add(makeButton(dates.get(1), dates.get(0) + "- " + dates.get(1)));
//            }
//        } catch (OogaDataException e) {
//            showError(e.getMessage());
//        }
        buttons.add(backButton);
        return buttons;
    }


    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ERROR_MESSAGE);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
