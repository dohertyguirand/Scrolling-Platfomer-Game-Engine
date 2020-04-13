package ooga.view;

import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import ooga.data.OogaEntity;
import ooga.data.TextEntity;

public class ViewTextEntity implements ViewEntity {

  private Text text = new Text();
  private static final double SIGNIFICANT_DIFFERENCE = 5.0;
  private static final double DEFAULT_FONT_SIZE = 12.0;
  private static final double FONT_SIZE_INCREMENT = 0.2;

  public ViewTextEntity(TextEntity entity) {
    bindGenericProperties(entity);
    bindTextProperty(entity.textProperty());
    bindFontProperties(entity);
  }

  public void bindGenericProperties(OogaEntity entity) {
    text.xProperty().bind(entity.xProperty());
    text.yProperty().bind(entity.yProperty());
    text.wrappingWidthProperty().bind(entity.widthProperty());
    // add more properties here if needed
  }

  public Node getNode() {
    return text;
  }

  private void bindTextProperty(StringProperty string){
    text.textProperty().bind(string);
  }

  private void bindFontProperties(TextEntity entity){
    entity.fontNameProperty().addListener((o, oldVal, newVal) -> resizeFont(entity));
    entity.textProperty().addListener((o, oldVal, newVal) -> resizeFont(entity));
    entity.heightProperty().addListener((o, oldVal, newVal) -> resizeFont(entity));
    entity.widthProperty().addListener((o, oldVal, newVal) -> resizeFont(entity));
    resizeFont(entity);
  }

  /**
   * adjusts the font size to try to fit the text entity within the desired height
   * @param entity the text entity
   */
  private void resizeFont(TextEntity entity){
    Font font = new Font(entity.getFontName(), DEFAULT_FONT_SIZE);
    text.setFont(font);
    double adjustedFontSize = DEFAULT_FONT_SIZE;
    double difference = text.getLayoutBounds().getHeight() - entity.getHeight();
    double previousDifference;
    while(Math.abs(difference) > SIGNIFICANT_DIFFERENCE){
      if(difference > 0) adjustedFontSize -= FONT_SIZE_INCREMENT;
      else adjustedFontSize += FONT_SIZE_INCREMENT;
      font = new Font(entity.getFontName(), adjustedFontSize);
      text.setFont(font);
      previousDifference = difference;
      difference = text.getLayoutBounds().getHeight() - entity.getHeight();
      if(difference < 0 && previousDifference > 0) break;
    }
  }


}
