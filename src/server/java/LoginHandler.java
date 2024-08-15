package server.java;



import java.io.*;
import java.nio.charset.StandardCharsets;
import com.sun.net.httpserver.*;

import org.json.JSONObject;

public class LoginHandler implements HttpHandler{
    private BusinessLogic bl;

    LoginHandler(BusinessLogic bl){
        this.bl = bl;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "Invalid username or password, please try again.";
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
        JSONObject loginDetails = new JSONObject(requestBody);
        String user = loginDetails.getString("user");
        String password = loginDetails.getString("pw");

        //matches login details to known database
        response = bl.checkLogin(user, password) ? "true": "Invalid username or password please try again";
        return response;
    }
    
}
