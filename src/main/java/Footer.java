package main.java;





import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

public class Footer extends HBox {
    
    public Button leftButton;
    public Button centerButton;
    public Button rightButton;

    Footer() {
        leftButton = new Button();
        centerButton = new Button();
        rightButton = new Button();

        this.getChildren().addAll(leftButton, centerButton, rightButton);
        this.setAlignment(Pos.CENTER);
    }

    public void setButtons(String leftText, String centerText, String rightText) {
        leftButton.setText(leftText);
        leftButton.setVisible(leftText != "");
        centerButton.setText(centerText);
        centerButton.setVisible(centerText != "");
        rightButton.setText(rightText);
        rightButton.setVisible(rightText != "");
    }

    public void setLeftButtonAction(EventHandler<ActionEvent> eventHandler) {
        leftButton.setOnAction(eventHandler);
    }

    public void setCenterButtonAction(EventHandler<ActionEvent> event) {
        centerButton.setOnAction(event);
    }

    public void setRightButtonAction(EventHandler<ActionEvent> eventHandler) {
        rightButton.setOnAction(eventHandler);
    }
}
