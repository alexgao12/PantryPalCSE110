package main.java;



import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONException;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;

public class PromptScreen extends Screen {
    private PromptButtons prompt;
    private String recipeType;
    private String currentUsername;
    private Button recordButton;
    private String transcript;
    private Recorder recorder;
    private Boolean isRecording = false;

    PromptScreen(String username) {
        super();
        this.currentUsername = username;
        setHeaderText("What would you like to make right now? Say 'Breakfast', 'Lunch', or 'Dinner'.");
        setFooterButtons("Back", "", "");
        // prompt = new PromptButtons(this.getUsername());
        // this.setCenter(prompt);
        recordButton = new Button("Record");
        recordButton.setStyle("-fx-background-color: #43ED58");
        recorder = new Recorder();
        this.setCenter(recordButton);
        setRightButtonAction("PantryPal", this::changeNextScreenEvent);
        setLeftButtonAction("PantryPal", this::changePreviousScreenEvent);
        addListeners();
    }

    public void toggleRecord() throws JSONException, IOException, URISyntaxException {
        isRecording = !isRecording;
        if(isRecording) {
            recordButton.setText("Recording...");
            recordButton.setStyle("-fx-background-color: #eb6f5b;");
            recorder.startRecording();
        }
        else {
            recordButton.setText("Record");
            recordButton.setStyle("-fx-background-color: #43ED58;");
            recorder.stopRecording();
            Path recording = Paths.get("./recording.wav");

            moveToNextScreen(currentUsername, AppFrame.getRequest().performTranscribe(recording.toFile()));
        }
    }

    public void addListeners() {
        recordButton.setOnAction(e -> {
            try {
                toggleRecord();
            } catch (JSONException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            }
        });
    }

    public String getUsername() {
        return currentUsername;
    }

    @Override
    protected Screen createNextScreen() {
        // return new RecordScreen(recipeType, currentUsername);
        return new PromptConfirmScreen(currentUsername, transcript);
    }

    @Override
    protected Screen createPreviousScreen() {
        return new HomeScreen(currentUsername);
    }

    public void moveToNextScreen(String username, String mealTypeTranscription) {
        this.transcript = mealTypeTranscription;
        Screen nextScreen = createNextScreen();
        changeScreen(nextScreen);
    }
}
