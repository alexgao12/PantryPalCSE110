package main.java;




import javafx.util.Pair;
import java.io.IOException;
import java.net.URISyntaxException;
import org.json.JSONException;
import java.io.*;
import java.net.*;
import org.json.*;

public interface RegenInterface {
    public Pair<String, String> regen(String ingreds, String mealType)throws IOException, InterruptedException, URISyntaxException;
}
