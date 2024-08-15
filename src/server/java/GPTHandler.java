package server.java;



import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

public class GPTHandler implements HttpHandler{
    private BusinessLogic bl;

    public GPTHandler(BusinessLogic bl){
        this.bl = bl;
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Request Received";
        String method = httpExchange.getRequestMethod();

        try {// Throwing exception so nothing is being written to response
            if (method.equals("POST")) {
                response = handlePost(httpExchange);
            }
            else {
                throw new Exception("Not Valid Request Method");
            }
        } catch (Exception e) {
            System.out.println("An erroneous request");
            response = e.toString();
            e.printStackTrace();
        }
        // Sending back response to the client
        //System.out.println(response);
        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream outStream = httpExchange.getResponseBody();
        OutputStreamWriter out = new OutputStreamWriter(outStream, StandardCharsets.UTF_8);
        //System.out.println(response);
        out.write(response);
        out.close();
    }
    private String handlePost(HttpExchange httpExchange) throws IOException, InterruptedException, URISyntaxException{
        InputStream inStream = httpExchange.getRequestBody();
        String requestBody = new String(inStream.readAllBytes(), StandardCharsets.UTF_8);
        System.out.println(requestBody);
        
        //Expects a json to be used as request body, with ingredients and type as keys
        JSONObject requestJson = new JSONObject(requestBody);
        String ingredients = requestJson.getString("ingredients");
        String mealtype = requestJson.getString("type");
        System.out.println(requestJson.toString());
        JSONObject responseJson = new JSONObject();
        // String test = """
        // Ingredients:
        
        // -2 large eggs
        
        // -3 ounces of cooked ham, cubed
        
        // -2 tablespoons of butter
        
        // -½ cup of shredded cheese (cheddar, Parmesan, or your preferred type)
        // -½ teaspoon of Italian seasoning
        // -Salt and pepper to taste    
        // """;
        // responseJson.put("recipe",test);
        // responseJson.put("title","fakeTitle");
        responseJson.put("recipe",bl.generate(ingredients, mealtype));
        responseJson.put("title",bl.generateTitle(ingredients, mealtype));
        String response = responseJson.toString();
        return response;
    }
}
