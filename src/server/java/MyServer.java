package server.java;



//import java.io.IOException;
import com.sun.net.httpserver.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.concurrent.*;

public class MyServer {
  // initialize server port and hostname
  private static final int SERVER_PORT = 8100;
  private static final String SERVER_HOSTNAME = "localhost";

  public static void main(String[] args) throws IOException {
    // create a thread pool to handle requests
    ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

    //Create Business Logic used for most handlers
    BusinessLogic bl = new BusinessLogic();

    // create a server
    HttpServer server = HttpServer.create(
        new InetSocketAddress(SERVER_HOSTNAME, SERVER_PORT),
        0);

    ConnectionTestHandler request = new ConnectionTestHandler();
    server.createContext("/connectionTest", request);

    GPTHandler gpthandler = new GPTHandler(bl);
    server.createContext("/generate", gpthandler);

    WhisperHandler whisperhandler = new WhisperHandler(bl);
      
    server.createContext("/transcribe", whisperhandler);

    LoginHandler loginhandler = new LoginHandler(bl);
    server.createContext("/login", loginhandler);

    AccountHandler accounthandler = new AccountHandler(bl);
    server.createContext("/createAccount", accounthandler);

    DeleteHandler deletehandler = new DeleteHandler(bl);
    server.createContext("/deleteRecipe", deletehandler);

    AllRecipeRequestHandler arrh = new AllRecipeRequestHandler(bl);
    server.createContext("/requestAll",arrh);

    ImageHandler imageHandler = new ImageHandler(bl);
    server.createContext("/image", imageHandler);

    SaveRecipeHandler srh = new SaveRecipeHandler(bl);
    server.createContext("/saveRecipe", srh);

    EditHandler eh = new EditHandler(bl);
    server.createContext("/editRecipe", eh);

    server.setExecutor(threadPoolExecutor);

    server.start();

    System.out.println("Server started on port " + SERVER_PORT);
  }
}