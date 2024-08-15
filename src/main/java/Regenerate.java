package main.java;



import javafx.scene.image.ImageView;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;

public class Regenerate implements RegenInterface{
    private String ingreds;
    private String mealType;
    private String recipe;
    private String recipeTitle;
    private Date date;
    private ImageView recipeImage;
    private ChatGPT gpt;
    private Recipe newRecipe;
    private DallE dallE;
    private Pair<String, String> recipePair;

    public Pair<String, String> regen(String ingreds, String mealType) throws IOException, InterruptedException, URISyntaxException{
        gpt = new ChatGPT();
        dallE = new DallE();
        recipe = gpt.generate(ingreds, mealType);
        recipeTitle = gpt.generateTitle(ingreds, mealType);
        dallE.image(recipeTitle);
        recipePair = new Pair<String, String>(recipe, recipeTitle);
        return recipePair;
    }
}
