

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.json.JSONException;

import javafx.scene.image.Image;

public class testImgGen{
    private File imageFile;
    private String recipeFileName;
    @Test
    public void testRegen() throws IOException, InterruptedException, URISyntaxException{
        String mockTitle = "Herb-Crusted Chicken and Egg Bake";
        mockDallE dallE = new mockDallE();
        dallE.image(mockTitle);
        recipeFileName = mockTitle.replaceAll("\\s+", "_").toLowerCase();
        imageFile = new File(recipeFileName + ".png");
        assertTrue(imageFile.exists(), "Image should exist");
    }
}