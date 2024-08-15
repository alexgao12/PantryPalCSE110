package main.java;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.geometry.Pos;
import javafx.scene.layout.*;

class PromptButtons extends HBox {
    private Button breakfastButton;
    private Button lunchButton;
    private Button dinnerButton;
    private String currentUsername;

    PromptButtons(String username) {
        this.currentUsername = username;
        breakfastButton = new Button("BreakFast");
        lunchButton = new Button("Lunch");
        dinnerButton = new Button("Dinner");
        this.getChildren().addAll(breakfastButton, lunchButton, dinnerButton);
        this.setAlignment(Pos.CENTER);
        addListeners();
    }

    public void addListeners(){
        breakfastButton.setOnAction(e -> {
            String breakfast = "Breakfast";
            moveToNextScreen(breakfast);
        });

        lunchButton.setOnAction(e -> {
            String lunch = "Lunch";
            moveToNextScreen(lunch);
        });

        dinnerButton.setOnAction(e -> {
            String dinner = "Dinner";
            moveToNextScreen(dinner);
        });
    }

    public void moveToNextScreen(String type) {
        Scene scene = getScene();
        Window screen = scene.getWindow();
        if (screen instanceof Stage) {
            Stage current = (Stage) screen;
            RecordScreen screenTwo = new RecordScreen(type, currentUsername);
            current.setTitle("Recording");
            current.setScene(new Scene(screenTwo, 500, 500));
            current.setResizable(false);
            current.show();
        }
    }

}
