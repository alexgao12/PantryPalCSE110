

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.json.JSONException;
import javafx.util.Pair;

public class testRegen extends mockRegen{
    @Test
    public void testReg() throws IOException, InterruptedException, URISyntaxException{
        String mockIngredients = "Onion and brussel sprouts";
        String mockMealType = "Breakfast";
        mockRegen regen= new mockRegen();
        Pair<String,String> actual = regen.regen(mockIngredients, mockMealType);
        Pair<String,String> expected = new Pair<String,String>("Scramble eggs with chicken on pan", "Chicken and egg scramble");
        assertEquals(expected, actual);
    }
}
