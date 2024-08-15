package main.java;





import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DetailedViewScreen extends Screen {
    private Recipe currentRecipe;
    private TextField currentRecipeTitle;
    private TextArea currentRecipeBody;
    private String currentUsername;
    private ImageView currentRecipeImage;

    DetailedViewScreen(String username, Recipe currentRecipe) {
        currentUsername = username;
        this.currentRecipe = currentRecipe;
        this.currentRecipeImage = currentRecipe.getRecipeImage();
        
        setHeaderText("Current Recipe: " + currentRecipe.getRecipeTitle());
        setFooterButtons("Back", "Delete", "Edit");
        setLeftButtonAction("PantryPal", this::changeScreenUpdateRecipe);
        setCenterButtonAction("PantryPal", arg0 -> {
            try {
                changeScreenDeleteRecipe(arg0);
            } catch (IOException | InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        setRightButtonAction("Editing", this::changeNextScreenEvent);

        System.out.println("CURRENT RECIPE IMAGE IS: " + currentRecipe.getRecipeImage());

        // ImageView testImageView = new ImageView();
        // Image testImage = new Image("file:recipeImage.png");

        // testImageView.setImage(testImage);

        currentRecipeTitle = new TextField(currentRecipe.getRecipeTitle());
        currentRecipeBody = new TextArea(currentRecipe.getRecipe());
        currentRecipeBody.setMaxHeight(400);
        currentRecipeBody.setMaxWidth(400);
        currentRecipeBody.setEditable(false);
        currentRecipeBody.setWrapText(true);

        // this.setTop(testImageView);
        this.setTop(currentRecipeImage);
        this.setCenter(currentRecipeBody);
    }

    @Override
    protected Screen createNextScreen() {
        return new EditScreen(currentUsername, currentRecipe);
    }

    @Override
    protected Screen createPreviousScreen() {
        return new HomeScreen(currentUsername);
    }
    
    public void changeScreenUpdateRecipe(ActionEvent e) {
        currentRecipe.setRecipe(currentRecipeBody.getText());
        Screen previousScreen = createPreviousScreen();
        changeScreen(previousScreen);
    }
    
    public void changeScreenDeleteRecipe(ActionEvent e) throws IOException, InterruptedException {
        AppFrame.getRequest().performDeleteRecipe(currentUsername, currentRecipe.getRecipeTitle()+"@"+currentRecipe.getDate().toString());
        AppFrame.getAppRecipeList().deleteRecipe(currentRecipe);
        Screen previouScreen = createPreviousScreen();
        changeScreen(previouScreen);
    }
}
