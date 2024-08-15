package server.java;



import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class FileSender {

    public static void main(String[] args) throws IOException, URISyntaxException {
        String serverUrl = "http://localhost:8100/transcribe"; // Change this to your server's URL
        String filePath = "recording.wav"; // Replace with the path to your file

        sendFile(serverUrl, filePath);
    }

    public static void sendFile(String serverUrl, String filePath) throws IOException, URISyntaxException {
        File uploadFile = new File(filePath);

        if (!uploadFile.exists()) {
            System.err.println("File not found!");
            return;
        }

        URL url = new URI(serverUrl).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/octet-stream");
        connection.setRequestProperty("Content-Disposition", "attachment; filename=\"" + uploadFile.getName() + "\"");

        OutputStream outputStream = connection.getOutputStream();
        FileInputStream fileInputStream = new FileInputStream(uploadFile);

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
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } else {
            System.out.println("File upload failed with response code: " + responseCode);
        }

        connection.disconnect();
    }
}
