package main.java;





import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.text.*;

public class Header extends HBox {
    private Text titleText;

    Header(String headerText) {
        titleText = new Text(headerText);
        this.getChildren().add(titleText);
        this.setAlignment(Pos.CENTER);
    }

    void changeTitleText(String text) {
        titleText.setText(text);
    }
}