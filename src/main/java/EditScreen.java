package main.java;





import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;

public class EditScreen extends Screen {
    private Recipe currentRecipe;
    private TextArea editRecipeBox;
    private String currentUsername;

    EditScreen(String username, Recipe currentRecipe) {
        this.currentRecipe = currentRecipe;
        this.currentUsername = username;
        editRecipeBox = new TextArea(currentRecipe.getRecipe());
        editRecipeBox.setMaxHeight(400);
        editRecipeBox.setMaxWidth(400);
        editRecipeBox.setEditable(true);
        editRecipeBox.setWrapText(true);
        setHeaderText("Currently Editing: " + currentRecipe.getRecipeTitle());
        setFooterButtons("Back", "", "Save");
        setLeftButtonAction("Detailed View", this::changePreviousScreenEvent);
        setRightButtonAction("Detailed View", arg0 -> {
            try {
                changeScreenSaveEdits(arg0);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        this.setCenter(editRecipeBox);
    }

    @Override
    protected Screen createNextScreen() {
        return new DetailedViewScreen(currentUsername, currentRecipe);
    }

    @Override
    protected Screen createPreviousScreen() {
        return new DetailedViewScreen(currentUsername, currentRecipe);
    }

    public Recipe getCurrentRecipe() {
        return currentRecipe;
    }

    public void changeScreenSaveEdits (ActionEvent e) throws IOException, InterruptedException {
        currentRecipe.setRecipe(editRecipeBox.getText());
        AppFrame.getRequest().performEditRecipe(currentUsername, getCurrentRecipe().getRecipeTitle(), getCurrentRecipe().getDate().toString() ,getCurrentRecipe().getRecipe());
        Screen nextScreen = createNextScreen();
        changeScreen(nextScreen);
    } 
}
