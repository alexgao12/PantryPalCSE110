package main.java;



import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

public class PromptConfirmScreen extends Screen{
    private String currentUsername;
    public static String mealTypeTranscript;
    private String recipeType;
    private TextArea mealTypeArea;
    private boolean validMealType;
    private Alert invalidMealTypeAlert;

    PromptConfirmScreen(){}
    PromptConfirmScreen(String username, String transcript) {
        this.currentUsername = username;
        mealTypeTranscript = transcript.toLowerCase();

        setHeaderText("This is what we heard from you. Confirm your meal type:");
        setFooterButtons("Cancel", "", "Confirm");
        setLeftButtonAction("PantryPal", this::changePreviousScreenEvent);
        setRightButtonAction("PantryPal", this::changeNextScreenEvent);

        invalidMealTypeAlert = new Alert(Alert.AlertType.WARNING);

        mealTypeArea = new TextArea();
        mealTypeArea.setText(mealTypeTranscript);

        this.setCenter(mealTypeArea);
        validMealType = checkTranscription();
        validateMealType();

    }

    public boolean checkTranscription() {
        boolean validMealType = false;
        if(mealTypeTranscript.contains("breakfast") || mealTypeTranscript.contains("lunch") || mealTypeTranscript.contains("dinner")) {
            validMealType = true;
        }
        return validMealType;
    }

    public void validateMealType() {
        if(getValidMealType()) {
            if(mealTypeTranscript.contains("breakfast")) {
                this.recipeType = "Breakfast";
            }
            else if (mealTypeTranscript.contains("lunch")) {
                this.recipeType = "Lunch";
            }
            else {
                this.recipeType = "Dinner";
            }
        }
        else if(mealTypeTranscript.equals("")) {
            
        }
        else {
            invalidMealTypeAlert.setContentText("You said an invalid meal type. Please try again.");
            invalidMealTypeAlert.showAndWait();
            setFooterButtons("Back", "", "");
        }
    }

    public boolean getValidMealType() {
        return validMealType;
    }

    @Override
    protected Screen createNextScreen() {
        return new RecordScreen(recipeType, currentUsername);
    }

    @Override
    protected Screen createPreviousScreen() {
        return new HomeScreen(currentUsername);
    }
}
