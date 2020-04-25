package ooga.view.menus;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.io.File;

public class MakeProfileMenu extends ScrollMenu {

    private Scene myScene;
    protected Pane myPane;
    private GridPane myGrid = new GridPane();
    protected Group myRoot;
    private FileChooser myFileChooser = new FileChooser();
    private ImageView mv = new ImageView();
    private final int HEADERSIZE  = 24;
    private final int INSETSIZE  = 40;
    private final int GAPSIZE = 10;
    private final int COLUMN1WIDTH = 100;
    private final int COLUMN2WIDTH = 200;
    private final int INSETMARGIN = 20;
    private final int HEADERCOLUMNSPAN = 2;
    private final int HEADERROWSPAN = 1;
    private final int WIDTHCOLUMNSPAN = 1;
    private final int SUBMITMARGIN = 20;
    private final int SUBMITWIDTH = 100;
    private final int SUBMITROWINDEX = 4;
    private final int SUBMITHEIGHT = 40;
    private final int TEXTFIELDHEIGHT = 40;

    protected MakeProfileMenu() {
        super();
        myGrid = createRegistrationFormPane();
        addUIControls(myGrid);
        myPane.getChildren().addAll(myGrid);
    }

    private GridPane createRegistrationFormPane() {

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(INSETSIZE, INSETSIZE, INSETSIZE, INSETSIZE));
        gridPane.setHgap(GAPSIZE);
        gridPane.setVgap(GAPSIZE);
        ColumnConstraints columnOneConstraints = new ColumnConstraints(COLUMN1WIDTH, COLUMN1WIDTH, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(COLUMN2WIDTH, COLUMN2WIDTH, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);
        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

        return gridPane;
    }

    private void addUIControls(GridPane gridPane) {
        // Add Header
        Label headerLabel = new Label("Make your profile");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, HEADERSIZE));
        gridPane.add(headerLabel, 0, 0, HEADERCOLUMNSPAN, WIDTHCOLUMNSPAN);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(INSETMARGIN, 0, INSETMARGIN, 0));
        Label nameLabel = new Label("Username : ");
        gridPane.add(nameLabel, 0, 1);

        TextField nameField = new TextField();
        nameField.setPrefHeight(TEXTFIELDHEIGHT);
        gridPane.add(nameField, 1, 1);

        Label picLabel = new Label("Select picture : ");
        gridPane.add(picLabel, 0, 2);

        Button browse = new Button("Browse");
        gridPane.add(browse, 1, 2);

        Button submitButton = new Button("Submit");
        submitButton.setPrefHeight(SUBMITHEIGHT);
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(SUBMITWIDTH);
        gridPane.add(submitButton, 0, SUBMITROWINDEX, HEADERCOLUMNSPAN, HEADERROWSPAN);
        GridPane.setHalignment(submitButton, HPos.CENTER);
        GridPane.setMargin(submitButton, new Insets(SUBMITMARGIN, 0, SUBMITMARGIN, 0));
    }

    private void configureFileChooser() {
        myFileChooser.setTitle("View Pictures");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg");
        myFileChooser.getExtensionFilters().add(extFilter);
    }
}


