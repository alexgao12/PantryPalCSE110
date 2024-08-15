package server.java;



import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class RequestHandler implements HttpHandler {
    //private final Map<String, String> data;
    private ArrayList<String> data;
    //private int postCount = 0;
    //private int getCount = 0;
    private int count = 0;

    public RequestHandler(ArrayList<String> data) {
        this.data = data;
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Request Received";
        String method = httpExchange.getRequestMethod();

        try {
            if (method.equals("GET")) {
                response = handleGet(httpExchange);
            } else if (method.equals("POST")) {
                response = handlePost(httpExchange);
            } /*else if (method.equals("PUT")) {
                response = handlePut(httpExchange);
            } else if (method.equals("DELETE")) {
                response = handleDelete(httpExchange);
            }*/ else {
                throw new Exception("Not Valid Request Method");
            }
        } catch (Exception e) {
            System.out.println("An erroneous request");
            response = e.toString();
            e.printStackTrace();
        }

        // Sending back response to the client
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream outStream = httpExchange.getResponseBody();
        outStream.write(response.getBytes());
        outStream.close();
    }

    private String handleGet(HttpExchange httpExchange) throws IOException {
        String response = "Invalid GET request";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();
        System.out.println(query);
        if (query != null) {
            String value = query.substring(query.indexOf("=") + 1);
            String postData;
            if (count < data.size()) {
                postData = data.get(count);
                response = postData;
                System.out.println("Queried for " + query + " and found " + postData);
                count += 1;
            }
            else {
                response = "Invalid";
            }
        }
        System.out.println(response);
        return response;
    }

    private String handlePost(HttpExchange httpExchange) throws IOException {
        InputStream inStream = httpExchange.getRequestBody();
        System.out.println(inStream.toString());
        String postData = new String(inStream.readAllBytes(), StandardCharsets.UTF_8);
        // Scanner scanner = new Scanner(inStream);
        // String postData = scanner.nextLine();
        /*String language = postData.substring(
                0,
                postData.indexOf(",")), year = postData.substring(postData.indexOf(",") + 1);*/

        // Store data in hashmap
        //data.put(language, year);
        // Store data in arraylist
        data.add(postData);
        //postCount += 1;
        String recipeTitle = postData.substring(
                0,
                postData.indexOf(",")), recipe = postData.substring(postData.indexOf(",") + 1);

        String response = "Posted entry {" + recipeTitle + "," + recipe + "}";
        System.out.println("post data which is the format string,string: " + postData);
        System.out.println("data when its separated: " + response);
        //scanner.close();

        return response;
    }

    /*private String handlePut(HttpExchange httpExchange) throws IOException {
        InputStream inStream = httpExchange.getRequestBody();
        Scanner scanner = new Scanner(inStream);
        String postData = scanner.nextLine();
        String language = postData.substring(
                0, 
                postData.indexOf(",")), year = postData.substring(postData.indexOf(",") + 1);

        String response;
        if (data.containsKey(language)) {
            String previousYear = data.get(language);  
            data.put(language, year);
            response = "Updated entry {" + language + ", " + year + "}" + "(previous year: " + previousYear + ")";
        }
        else {
            data.put(language, year);
            response = "Added entry {" + language + ", " + year + "}";    
        }
        System.out.println(response);
        scanner.close();

        return response;    
    }

    private String handleDelete(HttpExchange httpExchange) throws IOException {
        String response = "Invalid DELETE request";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();
        if (query != null) {
            // this is the Language, the key
            String value = query.substring(query.indexOf("=") + 1);
            // year is value
            String year = data.get(value); // Retrieve data from hashmap
            //if the value returned by the hashmap is not null then that means key exists
            if (year != null) {
                response = "Deleted entry {" + value + ", " + year + "}";
                data.remove(value);
            } else {
                response = "No data found for " + value;
            }
        }
        return response;
    }*/

}