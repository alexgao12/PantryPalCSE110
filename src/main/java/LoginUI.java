package main.java;



import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;

class LoginUI extends GridPane {
    private Label username;
    private Label password;
    private TextField usernameInput;
    private TextField passwordInput;
    private Button createButton;
    private Button loginButton;
    private CheckBox rememberOption;
    private Alert alert;
    private boolean autologin;
    private String user;

    LoginUI() {
        try{
            JSONObject logindetails = new JSONObject(Files.readString(new File("loginDetails.json").toPath()));
            this.autologin = false;
            if(logindetails.getString("autoLogin").equals("true")){
                this.autologin = true;
                this.user = logindetails.getString("user");
                String password = logindetails.getString("pw");
                loadRecipes(user, password);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        username = new Label("Username:");
        usernameInput = new TextField(); 
        usernameInput.setMaxSize(150, 5);
        usernameInput.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); 
        usernameInput.setPadding(new Insets(5, 0, 5, 0)); 
        this.addRow(0, username, usernameInput);

        password = new Label("Password:");
        passwordInput = new TextField(); 
        passwordInput.setMaxSize(150, 5);
        passwordInput.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); 
        passwordInput.setPadding(new Insets(5, 0, 5, 0)); 
        this.addRow(1, password, passwordInput);

        rememberOption = new CheckBox("Remember Me?");
        this.addRow(10, rememberOption);

        createButton = new Button("Create");
        loginButton = new Button("Login");
        this.addRow(12, loginButton, createButton);

        this.setHgap(5);
        this.setVgap(5);
        this.setAlignment(Pos.CENTER);
        addListeners();

        this.alert = new Alert(Alert.AlertType.WARNING);
        }

    public String getUsernameInput() {
        return usernameInput.getText();
    }
    public String getPasswordInput() {
        return passwordInput.getText();
    }

    public Boolean checkAutomaticLogin() {
        return rememberOption.isSelected();
    }

    public void addListeners(){
        loginButton.setOnAction(e -> {
            if (checkAutomaticLogin()) {
                System.out.println("Automatic Login has been enabled");  
            }
            String username = getUsernameInput();
            String password = getPasswordInput();
            System.out.println(username);
            System.out.println(password);
            // Check login validation here
            String response = AppFrame.getRequest().performLogin(username, password);
            if(response.equals("true")) {
                try {
                    JSONObject recipes = new JSONObject(AppFrame.getRequest().performGetAllRecipes(username));
                    AppFrame.getAppRecipeList().loadRecipes(recipes, username);
                    AppFrame.getAppRecipeList().sortRecipesByDate();
                } catch (JSONException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (ParseException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                moveToNextScreen(username);
            }
            else {
                alert.setContentText(response);
                alert.showAndWait();
            }
        });

        createButton.setOnAction(e -> {
            if (checkAutomaticLogin()) {
                System.out.println("Automatic login was selected but won't be enabled by clicking 'create'");
            }
            String username = getUsernameInput();
            String password = getPasswordInput();
            System.out.println(username);
            System.out.println(password);
            // Post username key if it doesn't exist before moving to next screen
            String response = AppFrame.getRequest().performCreateAccount(username, password);
            if(response.equals("true")) {
                moveToNextScreen(username);
            }
            else {
                alert.setContentText("Error: " + response);
                alert.showAndWait();
            }
            
        });
    }
    public void moveToNextScreen(String username) {
        Scene scene = getScene();
        Window screen = scene.getWindow();
        if (screen instanceof Stage) {
            Stage current = (Stage) screen;
            HomeScreen screenTwo = new HomeScreen(username);
            current.setScene(new Scene(screenTwo, 500, 500));
            current.setResizable(false);
            current.show();
        }
    }

    public boolean getAutoLogin(){
        return autologin;
    }
    public String getUser(){
        return user;
    }
    private void loadRecipes(String username, String password){
        try {
            JSONObject recipes = new JSONObject(AppFrame.getRequest().performGetAllRecipes(username));
            for(String key : recipes.keySet()){
                JSONObject recipe = recipes.getJSONObject(key);
                String rTitle = recipe.getString("title");
                DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                Date rDate = df.parse(recipe.getString("date"));
                String rType = recipe.getString("mealType");
                String rBody = recipe.getString("recipe");


                String recipeTitle = recipe.getString("title");
                String recipeFileName = recipeTitle.replaceAll("\\s+", "_").toLowerCase();
                Image currImage = new Image("file:"+recipeFileName+".png");
                ImageView recipeImage = new ImageView();
                recipeImage.setImage(currImage);

                Recipe currRecipe = new Recipe(username, rTitle, rBody, rType, rDate, recipeImage);
                AppFrame.getAppRecipeList().getChildren().add(currRecipe);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
