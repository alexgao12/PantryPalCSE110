package main.java;





import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

public class RecipeScreen extends Screen{
    private TextArea generatedRecipe;
    private String recipe;
    private String recipeTitle;
    private String ingreds;
    private String mealType;
    private RequestSender request = new RequestSender();
    private Recipe recipeObj;
    private Date date;
    private ChatGPT gpt;
    private String currentUsername;
    private ImageView recipeImage;
    private DallE dallE;

    RecipeScreen(String username, String recipe, String recipeTitle, String ingreds, String mealType, Date date, ImageView recipeImage){
        setHeaderText("Here is your recipe!");
        this.currentUsername = username;
        this.recipe = recipe;
        this.recipeTitle = recipeTitle;
        this.mealType = mealType;
        this.date = date;
        this.ingreds = ingreds;
        this.mealType = mealType;
        this.recipeImage = recipeImage;

        recipeImage.setFitHeight(250);
        recipeImage.setFitWidth(250);
        recipeImage.setPreserveRatio(true);

        generatedRecipe = new TextArea(recipeTitle + "\n\n" + recipe);
        generatedRecipe.setMaxHeight(400);
        generatedRecipe.setMaxWidth(400);
        generatedRecipe.setEditable(false);
        generatedRecipe.setWrapText(true);
        setFooterButtons("Cancel", "Regenerate", "Save");
        setLeftButtonAction("PantryPal", this::changeNextScreenEvent);
        setCenterButtonAction("PantryPal", event -> {
            try {
                changeScreenGenerateRecipeEvent(event);
            } catch (URISyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        setRightButtonAction("PantryPal", arg0 -> {
            try {
                changeScreenSaveRecipe(arg0);
            } catch (IOException | InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        this.setTop(recipeImage);
        this.setCenter(generatedRecipe);
        this.request = new RequestSender();
    }

    @Override
    protected Screen createNextScreen() {
        return new HomeScreen(currentUsername);
    }

    @Override
    protected Screen createPreviousScreen() {
        return new HomeScreen(currentUsername);
    }

    protected Screen createSameScreen() {
        return new RecipeScreen(currentUsername, recipe, recipeTitle, ingreds, mealType, date, recipeImage);
    }

    public void changeScreenSaveRecipe (ActionEvent e) throws IOException, InterruptedException {
        Screen nextScreen = new HomeScreen(currentUsername);
        recipeObj = new Recipe(currentUsername, recipeTitle, recipe, mealType, date, recipeImage);
        AppFrame.getRequest().performSaveRecipe(currentUsername, recipeTitle, date.toString(), recipe, mealType);
        AppFrame.getAppRecipeList().getChildren().add(0, recipeObj);
        changeScreen(nextScreen);
    } 

    public void changeScreenGenerateRecipeEvent (ActionEvent e) throws URISyntaxException {
        dallE = new DallE();
        try {
            String data = AppFrame.getRequest().performGenerateRecipe(ingreds, mealType);
            JSONObject dataJson = new JSONObject(data);
            recipe = dataJson.getString("recipe");
            recipeTitle = dataJson.getString("title");
            date = new Date();
            dallE.image(recipeTitle);
            String recipeFileName = recipeTitle.replaceAll("\\s+", "_").toLowerCase();
            Image currImage = new Image("file:"+recipeFileName+".png");
            recipeImage = new ImageView();
            recipeImage.setImage(currImage);
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        Screen sameScreen = createSameScreen();
        changeScreen(sameScreen);
    } 
}
