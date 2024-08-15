package server.java;



import java.net.URISyntaxException;
import com.sun.net.httpserver.*;
import java.io.*;

public class WhisperHandler implements HttpHandler{
    private BusinessLogic bl;

    public WhisperHandler(BusinessLogic bl){
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
        //Get filename from header - taken from ChatGPT 3.5
        // prompt used: "write me code for receiving a file through an http request using java"
        String fileName = httpExchange.getRequestHeaders().getFirst("Content-Disposition")
                        .replaceFirst(".*filename=\"([^\"]+)\".*", "$1");

        // Save the uploaded file to the server
        OutputStream outputStream = new FileOutputStream("recording2.wav");
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.close();
        inStream.close();
        //end of ChatGPT code

        //Create response through Whisper
        File f = new File("recording2.wav");
        String response = bl.transcribe(f);
        return response;
    }
}
