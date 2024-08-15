
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.File;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.json.JSONException;

import javafx.util.Pair;

public class testRegenImageBDD {
    private String mockIngreds;
    private String mockMealType;
    private String oldRecipe;
    private String oldRecipeTitle;
    private String newRecipe;
    private String newRecipeTitle;
    private String recipeFileName;
    private Pair<String,String> actual;
    private mockRegen regen;
    private File imageFile;
    private mockDallE dallE;

    @Test
    public void testRegImage() throws IOException, InterruptedException, URISyntaxException{
        mockIngreds = "Chicken and eggs";
        mockMealType = "Breakfast";
        regen = new mockRegen();
        actual = regen.regen(mockIngreds, mockMealType);
        newRecipe = actual.getKey();
        newRecipeTitle = actual.getValue();
        dallE = new mockDallE();
        dallE.image(newRecipeTitle);
        recipeFileName = newRecipeTitle.replaceAll("\\s+", "_").toLowerCase();
        imageFile = new File(recipeFileName + ".png");
        assertTrue(imageFile.exists(), "New image should exist");
    }
}