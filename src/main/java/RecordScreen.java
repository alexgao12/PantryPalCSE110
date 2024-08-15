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

public class RecordScreen extends Screen {
    private Button recordButton;
    private String recipeType;
    private String transcript;
    private Boolean isRecording = false;
    private Recorder recorder;
    // private Whisper testWhisper = new Whisper();
    // private RequestSender request = new RequestSender();
    private String currentUsername;

    RecordScreen(String type, String username) {
        recipeType = type;
        currentUsername = username;
        setHeaderText("What ingredients do you have now? You said you wanted: " + getRecipeType());
        setFooterButtons("Back", "", "");
        setLeftButtonAction("PantryPal", this::changePreviousScreenEvent);
        recordButton = new Button("Record");
        recordButton.setStyle("-fx-background-color: #43ED58");
        recorder = new Recorder();
        this.setCenter(recordButton);
        addListeners();
    }

    @Override
    protected Screen createNextScreen() {
        return new TranscriptionScreen(currentUsername, transcript, recipeType);
    }

    @Override
    protected Screen createPreviousScreen() {
        return new HomeScreen(currentUsername);
    }

    public String getRecipeType() {
        return recipeType;
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

            moveToNextScreen(AppFrame.getRequest().performTranscribe(recording.toFile()), getRecipeType());
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

    public void moveToNextScreen(String transcription, String mealType) {
        Scene scene = getScene();
        Window screen = scene.getWindow();
        if (screen instanceof Stage) {
            Stage current = (Stage) screen;
            TranscriptionScreen screenTwo = new TranscriptionScreen(currentUsername, transcription, mealType);
            current.setTitle("Ingredients");
            current.setScene(new Scene(screenTwo, 500, 500));
            current.setResizable(false);
            current.show();
        }
    }
}
