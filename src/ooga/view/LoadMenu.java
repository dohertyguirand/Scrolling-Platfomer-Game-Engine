package ooga.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import ooga.data.DataReader;
import java.util.ArrayList;
import java.util.List;

public class LoadMenu extends OptionMenu {
    private static final String NEWGAME = "New Game";
    private StringProperty dateSelected = new SimpleStringProperty();

    /**
     * View where user can decide to load a saved game or a new game
     * @param gamename - name of game selected, used to find past saves
     * @param profilename - user's profile name, used to find saved games
     * @param reader - DataReader used to get past saves from data
     * @param backButton - button that allows user to get back to startmenu screen
     */
    public LoadMenu(String gamename, String profilename, DataReader reader, Node backButton){
        super(gamename);
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

    private List<Node> createButtons(Node backButton, DataReader dataReader, String profileName, String gameName) {
        List<Node> buttons = new ArrayList<>();
        Button button = new Button(NEWGAME);
        button.setOnAction(e-> setDateSelected("  "));
        buttons.add(button);
//        for(List<String> dates: dataReader.getAllPreviousSaves(profileName,gameName) ){
//           buttons.add(makeButton(dates.get(1), dates.get(0) + " -  " + dates.get(1)));
//        }
        buttons.add(backButton);
        return buttons;
    }
}
