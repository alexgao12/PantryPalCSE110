package main.java;




import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

import javafx.scene.shape.Path;

import java.nio.file.Paths;

import java.net.URI;
 

public class RequestSender {
    public String performCheck() {
        try {
            String urlString = "http://localhost:8100/connectionTest";
            // Create the HTTP Client
            HttpClient client = HttpClient.newHttpClient();
            URI ur = URI.create(urlString);
            // Create the request object
            HttpRequest request = HttpRequest
            .newBuilder()
            .uri(ur)
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("test"))
            .build();

             // Send the request and receive the response
            HttpResponse<String> response = client.send(
            request,
            HttpResponse.BodyHandlers.ofString()
            );
            
            // Process the response
            String responseBody = response.body();
            return responseBody;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error: " + ex.getMessage();
        }
    }

    public String performTranscribe(File recording){
        //Code taken from ChatGPT 3.5
        //Prompt used; write me code to send a file to this server using java
        //Done after the prompt used in WhisperHandler
        try{
            String serverUrl = "http://localhost:8100/transcribe";
            if (!recording.exists()) {
                throw new FileNotFoundException("File Not Found");
            }
    
            URL url = new URI(serverUrl).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/octet-stream");
            connection.setRequestProperty("Content-Disposition", "attachment; filename=\"" + recording.getName() + "\"");
    
            OutputStream outputStream = connection.getOutputStream();
            FileInputStream fileInputStream = new FileInputStream(recording);
    
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
    
            outputStream.flush();
            outputStream.close();
            fileInputStream.close();
    
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Reading the response from the server
                String response = new String(connection.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
                connection.disconnect();
                return response;
            } else {
                connection.disconnect();
                return "File upload failed with response code: " + responseCode;
            }   
        } catch (Exception e){
            e.printStackTrace();
            return "Error: File Not Found";
        }
    }
    public File performGenerateImage(String title){
        try{
            String serverUrl = "http://localhost:8100/image";
            URL url = new URI(serverUrl).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);  


            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(title);
            out.flush();
            out.close();

            InputStream in = connection.getInputStream();
            String response = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            in.close();
            Path image = (Path) Paths.get(response + ".png");
            File f = ((java.nio.file.Path) image).toFile();

            return f;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public String performGenerateRecipe(String ingredients, String mealtype) throws IOException, InterruptedException {
            String urlString = "http://localhost:8100/generate";
            // Create a request body which you will pass into request object
            JSONObject requestBody = new JSONObject();
            requestBody.put("ingredients", ingredients);
            requestBody.put("type", mealtype);

            // Create the HTTP Client
            HttpClient client = HttpClient.newHttpClient();

            URI ur = URI.create(urlString);
            // Create the request object
            HttpRequest request = HttpRequest
            .newBuilder()
            .uri(ur)
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
            .build();

            // Send the request and receive the response
            HttpResponse<String> response = client.send(
            request,
            HttpResponse.BodyHandlers.ofString()
            );
            
            // Process the response
            String responseBody = response.body();
            //System.out.println(responseBody);
            return responseBody;
    }
    
    public String performLogin(String username, String password){
        String urlString = "http://localhost:8100/login";
        // Create a request body which you will pass into request object
        JSONObject requestBody = new JSONObject();
        requestBody.put("user", username);
        requestBody.put("pw", password);

        try{
            // Create the HTTP Client
            HttpClient client = HttpClient.newHttpClient();

            URI ur = URI.create(urlString);
            // Create the request object
            HttpRequest request = HttpRequest
            .newBuilder()
            .uri(ur)
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
            .build();

            // Send the request and receive the response
            HttpResponse<String> response = client.send(
            request,
            HttpResponse.BodyHandlers.ofString());
            // Process the response
            String responseBody = response.body();
            //System.out.println(responseBody);
            //response will be "true" upon valid login, and error message to be displayed upon invalid login
            return responseBody;
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "Error";
        }
    }

    public String performCreateAccount(String username, String password){
        String urlString = "http://localhost:8100/createAccount";
        JSONObject requestBody = new JSONObject();
        requestBody.put("user", username);
        requestBody.put("pw", password);
        System.out.println(requestBody);
        try{
            // Create the HTTP Client
            HttpClient client = HttpClient.newHttpClient();

            URI ur = URI.create(urlString);
            // Create the request object
            HttpRequest request = HttpRequest
            .newBuilder()
            .uri(ur)
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
            .build();

            // Send the request and receive the response
            HttpResponse<String> response = client.send(
            request,
            HttpResponse.BodyHandlers.ofString());
            // Process the response
            String responseBody = response.body();
            // System.out.println(responseBody);
            //response will be "true" upon valid account creation, and error message to be displayed upon invalid creation
            System.out.println(responseBody);
            return responseBody;
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "Error";
        }
    }

    public String performDeleteRecipe(String username, String recipeTitleDate) throws IOException, InterruptedException{
        String urlString = "http://localhost:8100/deleteRecipe";
        // Create a request body which you will pass into request object
        JSONObject requestBody = new JSONObject();
        requestBody.put("username", username);
        requestBody.put("titleDate", recipeTitleDate);

        // Create the HTTP Client
        HttpClient client = HttpClient.newHttpClient();

        URI ur = URI.create(urlString);
        // Create the request object
        HttpRequest request = HttpRequest
        .newBuilder()
        .uri(ur)
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
        .build();

        // Send the request and receive the response
        HttpResponse<String> response = client.send(
        request,
        HttpResponse.BodyHandlers.ofString()
        );
        
        // Process the response
        String responseBody = response.body();
        //System.out.println(responseBody);
        return responseBody;
    }

    public String performGetAllRecipes(String username) throws IOException, InterruptedException{
        String urlString = "http://localhost:8100/requestAll";
        // Create a request body which you will pass into request object
        JSONObject requestBody = new JSONObject();
        requestBody.put("user", username);

        // Create the HTTP Client
        HttpClient client = HttpClient.newHttpClient();

        URI ur = URI.create(urlString);
        // Create the request object
        HttpRequest request = HttpRequest
        .newBuilder()
        .uri(ur)
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
        .build();

        // Send the request and receive the response
        HttpResponse<String> response = client.send(
        request,
        HttpResponse.BodyHandlers.ofString()
        );
        
        // Process the response
        String responseBody = response.body();
        //System.out.println(responseBody);
        return responseBody;
    }

    public String performSaveRecipe(String username, String title, String date, String recipe, String mealType) throws IOException, InterruptedException{
        String urlString = "http://localhost:8100/saveRecipe";
        // Create a request body which you will pass into request object
        JSONObject requestBody = new JSONObject();
        requestBody.put("user", username);
        JSONObject recipeJSON = new JSONObject();
        recipeJSON.put("title",title);
        recipeJSON.put("date", date);
        recipeJSON.put("mealType",mealType);
        recipeJSON.put("recipe",recipe);
        requestBody.put(title+"@"+date,recipeJSON);
        requestBody.put("titleDate",title+"@"+date);

        // Create the HTTP Client
        HttpClient client = HttpClient.newHttpClient();

        URI ur = URI.create(urlString);
        // Create the request object
        HttpRequest request = HttpRequest
        .newBuilder()
        .uri(ur)
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
        .build();

        // Send the request and receive the response
        HttpResponse<String> response = client.send(
        request,
        HttpResponse.BodyHandlers.ofString()
        );
        
        // Process the response
        String responseBody = response.body();
        //System.out.println(responseBody);
        return responseBody;
    }

    public String performEditRecipe(String username, String title, String date, String recipe) throws IOException, InterruptedException{
        String urlString = "http://localhost:8100/editRecipe";
        // Create a request body which you will pass into request object
        JSONObject requestBody = new JSONObject();
        requestBody.put("user", username);
        requestBody.put("titleDate",title+"@"+date);
        requestBody.put("recipe",recipe);


        // Create the HTTP Client
        HttpClient client = HttpClient.newHttpClient();

        URI ur = URI.create(urlString);
        // Create the request object
        HttpRequest request = HttpRequest
        .newBuilder()
        .uri(ur)
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
        .build();

        // Send the request and receive the response
        HttpResponse<String> response = client.send(
        request,
        HttpResponse.BodyHandlers.ofString()
        );
        
        // Process the response
        String responseBody = response.body();
        //System.out.println(responseBody);
        return responseBody;
    }

}

