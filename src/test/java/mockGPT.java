

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.json.JSONException;



public class mockGPT implements ChatGPTInterface{
    @Override
    public String generate(String ingredients, String mealType) {return "regenerated";}
}