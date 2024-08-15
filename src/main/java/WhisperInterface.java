package main.java;


import org.json.JSONException;
import java.io.*;
import java.net.*;
import org.json.*;

public interface WhisperInterface {
    public String transcribe(File recording) throws IOException, URISyntaxException, JSONException ;
}