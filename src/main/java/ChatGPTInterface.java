package main.java;



import java.io.*;
import java.net.*;

public interface ChatGPTInterface {
    public String generate(String ingredients, String mealType) throws IOException, InterruptedException, URISyntaxException;
    // public String generateTitle(String ingredients, String mealType) throws IOException, InterruptedException, URISyntaxException;
}