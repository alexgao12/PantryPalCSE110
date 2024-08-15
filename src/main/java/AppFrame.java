package main.java;



import javafx.scene.layout.BorderPane;

public class AppFrame extends BorderPane {
    public static RecipeList recipeList;
    private static RequestSender request;
    private LoginScreen startScreen;

    AppFrame() {
        AppFrame.recipeList = new RecipeList();
        AppFrame.request = new RequestSender();
        startScreen = new LoginScreen();
        if(startScreen.getAutoLogin()){
            this.setCenter(new HomeScreen(startScreen.getUser()));
        }
        else{
            this.setCenter(startScreen);
        }
    }
    
    public static RecipeList getAppRecipeList() {
        return recipeList;
    }

    public static RequestSender getRequest() {
        return request;
    }
}
