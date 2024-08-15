package server.java;



import java.io.*;
import java.nio.file.Files;

import org.json.JSONObject;


public class Accounts {
    //all the user account : password pairs in one JSON
    private JSONObject userPw; 
    /*separate JSONObjects key'd by username, username object contains object keyd by recipetitle+date, recipetitle+date objects contain 4
    key value pairs, mealtype:mealtype(ex breakfast), date:creation date, title:recipe title, recipe: actual recipe*/
    private JSONObject allRecipes; 
    public Accounts(){
        userPw = null;
        allRecipes = null;
        loaduserPW(new File("userPw.json"));
        loadallRecipes(new File("allRecipes.json"));
    }
    
    private void loaduserPW(File userPw){ //loads user:password pairs from json file
        try{
            this.userPw = new JSONObject(Files.readString(userPw.toPath()));
        }
        catch(Exception e){
            this.userPw = new JSONObject();
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void loadallRecipes(File allRecipes){ //loads allreacipes from json file
        try{
            this.allRecipes = new JSONObject(Files.readString(allRecipes.toPath()));
        }catch(Exception e){
            this.allRecipes = new JSONObject();
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void writeToFilePw() throws IOException{ //writes jsonObjects to Json files
        File f = new File("userPw.json");
        FileOutputStream fw = new FileOutputStream(f);
        OutputStreamWriter writer = new OutputStreamWriter(fw);
        writer.write(this.userPw.toString());
        writer.flush();
        writer.close();
    }

    private void writeToFileRecipes() throws IOException{ //writes jsonObjects to Json files
        File f = new File("allRecipes.json");
        FileOutputStream fw = new FileOutputStream(f);
        OutputStreamWriter writer = new OutputStreamWriter(fw);
        writer.write(this.allRecipes.toString());
        writer.flush();
        writer.close();
    }

    //when user creates new account, adds login details to database json
    public boolean addUserAccount(String username, String password) throws IOException{ 
        if(userPw.isNull(username)){
            userPw.put(username,password);
            allRecipes.put(username, new JSONObject());
            writeToFilePw();
            writeToFileRecipes();
            return true;
        }
        return false;

    }

    public boolean editSavedRecipe(String username, String recipeTitleDate, String newRecipe){
        try{
            System.out.println(allRecipes.getJSONObject(username).has(recipeTitleDate));
            allRecipes.getJSONObject(username).getJSONObject(recipeTitleDate).remove("recipe");
            allRecipes.getJSONObject(username).getJSONObject(recipeTitleDate).put("recipe",newRecipe);
            writeToFileRecipes();
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    //when user saves
    public boolean saveRecipeToAccount(String username, JSONObject recipe){
        try{
            allRecipes.getJSONObject(username).put(recipe.getString("title")+"@"+recipe.getString("date"),recipe);
            writeToFileRecipes();
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteRecipeFromAccount(String username, String recipeTitleDate){
        try{
            allRecipes.getJSONObject(username).remove(recipeTitleDate);
            writeToFileRecipes();
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean checkLogin(String username, String password){ //checks if login is valid
        return password.equals(userPw.getString(username));
    }

    public JSONObject getUserRecipes(String username){ //gets all recipes from a user and returns the JSONObject
        return allRecipes.getJSONObject(username);
    }
}