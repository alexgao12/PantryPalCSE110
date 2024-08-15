

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONException;



public class mockDallE implements DallEInterface{
    @Override
    public void image(String recipeTitle) throws IOException {
        System.out.println("Image generated");
        String recipeFileName = recipeTitle.replaceAll("\\s+", "_").toLowerCase();
        File imageFile = new File(recipeFileName + ".png");
        Path path = Paths.get(imageFile.getAbsolutePath());
        //Add new file to directory
        Files.write(path, new byte[0]);
    }
}