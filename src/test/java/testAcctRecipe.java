
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;


public class testAcctRecipe {
    //create a mock username and password
    //assert that it is in the json file
    private String mockUser;
    private String mockPass;
    private String fakeUser;
    private String fakePass;
    private Accounts account;
    private Boolean thrown;
    private JSONObject json;

    @Test
    public void testAccountRecipe() throws IOException, InterruptedException, URISyntaxException{
        //create user acct
        mockUser = "testUser1";
        mockPass = "testPass1";
        account = new Accounts();
        account.addUserAccount(mockUser, mockPass);
        //add recipe to acct
        JSONObject json = new JSONObject();
        json.put("title", "Chicken");
        json.put("date", "Mon Dec 04 22:24:54 PST 2023");
        json.put("mealType", "Lunch");
        json.put("recipe", "Chicken");
        account.saveRecipeToAccount(mockUser, json);
        //check if recipe is in acct
        JSONObject recipes = account.getUserRecipes(mockUser).getJSONObject("Chicken@Mon Dec 04 22:24:54 PST 2023");
        assertTrue(recipes == json);
        //edit saved recipe
        // String newRecipe = "Eggs";
        // account.editSavedRecipe(mockUser, "Mon Dec 04 22:24:54 PST 2023", newRecipe);
        // //check if edit recipe got saved
        // // json.put("recipe", "Eggs");
        // // assert(recipes.get("recipe") == json.get("recipe"));
        // //delete recipe from acct
        // account.deleteRecipeFromAccount(mockUser, (String) json.get("date"));
        // //check if it deleted recipe from acct
        // assertNull(account.getUserRecipes(mockUser));
    }
}