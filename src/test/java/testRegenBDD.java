
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.File;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.json.JSONException;

import javafx.util.Pair;

public class testRegenBDD {
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
    public void testReg() throws IOException, InterruptedException, URISyntaxException{
        mockIngreds = "Chicken and eggs";
        oldRecipeTitle = "herb-crusted_chicken_with_breadcrumbs";
        oldRecipe = "Ingredients: \r\n" + //
                "-2 boneless, skinless chicken breasts, cut into cubes\r\n" + //
                "-2 large eggs\r\n" + //
                "-2 tablespoons of fresh herbs (such as thyme, oregano, rosemary, or sage)\r\n" + //
                "-2 garlic cloves, minced\r\n" + //
                "-¼ teaspoon of black pepper\r\n" + //
                "-2 tablespoons of olive oil\r\n" + //
                "-3 tablespoons of breadcrumbs\r\n" + //
                "\r\n" + //
                "Instructions:\r\n" + //
                "\r\n" + //
                "1. Preheat the oven to 350 degrees Fahrenheit.\r\n" + //
                "\r\n" + //
                "2. In a medium bowl, mix together the fresh herbs, garlic, black pepper, breadcrumbs, and olive oil.\r\n" + //
                "\r\n" + //
                "3. Place the cubed chicken in a baking dish and top with the herb mixture.\r\n" + //
                "\r\n" + //
                "4. Crack the eggs over the top of the chicken and spread them out evenly.\r\n" + //
                "\r\n" + //
                "5. Bake for 25 minutes until the chicken is cooked through and the eggs have set.\r\n" + //
                "\r\n" + //
                "6. Serve hot and enjoy!";
        mockMealType = "Breakfast";
        regen = new mockRegen();
        actual = regen.regen(mockIngreds, mockMealType);
        newRecipe = actual.getKey();
        newRecipeTitle = actual.getValue();
        dallE = new mockDallE();
        dallE.image(newRecipeTitle);
        recipeFileName = newRecipeTitle.replaceAll("\\s+", "_").toLowerCase();
        imageFile = new File(recipeFileName + ".png");
        assertNotEquals(oldRecipe, newRecipe);
        assertNotEquals(oldRecipeTitle, newRecipeTitle);
        assertTrue(imageFile.exists(), "New image should exist");
    }
}
