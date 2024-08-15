package server.java;



import java.net.URISyntaxException;
import com.sun.net.httpserver.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class ImageHandler implements HttpHandler{
    private BusinessLogic bl;

    public ImageHandler(BusinessLogic bl){
        this.bl = bl;
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Request Received";
        String method = httpExchange.getRequestMethod();

        try {
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
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream outStream = httpExchange.getResponseBody();
        outStream.write(response.getBytes());
        outStream.close();
    }

    private String handlePost(HttpExchange httpExchange) throws IOException, InterruptedException, URISyntaxException{
        InputStream inStream = httpExchange.getRequestBody();
        String response = new String(inStream.readAllBytes(), StandardCharsets.UTF_8);

        bl.generateImage(response);
        return response;
    }
}
