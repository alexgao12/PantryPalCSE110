package server.java;



import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import org.json.JSONObject;

public class SaveRecipeHandler implements HttpHandler{

    private BusinessLogic bl;

    SaveRecipeHandler(BusinessLogic bl){
        this.bl = bl;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "Request received";
        try{
            if(exchange.getRequestMethod().equals("POST")){
                response = handlePost(exchange);
            }
        } catch (Exception e){
            System.out.println("An erroneous request");
            response = e.toString();
            e.printStackTrace();
        }
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream outStream = exchange.getResponseBody();
        OutputStreamWriter out = new OutputStreamWriter(outStream, StandardCharsets.UTF_8);
        //System.out.println(response);
        out.write(response);
        out.close();
    }

    public String handlePost(HttpExchange exchange) throws IOException{
        String response;
        InputStream inStream = exchange.getRequestBody();
        String requestBody = new String(inStream.readAllBytes(), StandardCharsets.UTF_8); //reads in request body json

        System.out.println(requestBody);

        //takes json from request body and gets login details
        JSONObject requestDetails = new JSONObject(requestBody);

        //matches login details to known database
        response = bl.saveRecipeToAccount(requestDetails.getString("user"), requestDetails.getJSONObject(requestDetails.getString("titleDate"))) ? "true": "Recipe failed to save";
        return response;
    }
}
