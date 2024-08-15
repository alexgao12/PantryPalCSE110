package main.java;



import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

public class HomeScreen extends Screen {
    private RecipeDisplay recipeDisplay;
    private FilterSortButtons fsButtons;
    private String currentUsername;

    HomeScreen(String username) {
        super();
        currentUsername = username;
        setHeaderText("PantryPal");
        setCenterObject();
        setFooterButtons("", "New Recipe", "");
        setCenterButtonAction("Pick Meal", this::changeNextScreenEvent);
        recipeDisplay = new RecipeDisplay();
        this.setCenter(recipeDisplay);
        fsButtons = new FilterSortButtons();
        this.setTop(fsButtons);
    }

    // This should be a server request
    public RecipeDisplay getRecipeDisplay() {
        return this.recipeDisplay;
    }

    @Override
    protected Screen createNextScreen() {
        return new PromptScreen(currentUsername);
    }

    @Override
    protected Screen createPreviousScreen() {
        return new HomeScreen(currentUsername);
    }
}

class FilterSortButtons extends HBox {
    private ComboBox<String> filters;
    private Button sortButtonName;
    private Button sortButtonDate;
    
    FilterSortButtons() {
        filters = new ComboBox<String>();
        filters.getItems().addAll("None", "Breakfast", "Lunch", "Dinner");
        filters.setPromptText("Filter Recipes");
        sortButtonName = new Button("Sort (Name)");
        sortButtonDate = new Button("Sort (Date)");
        this.getChildren().addAll(filters, sortButtonName, sortButtonDate);
        this.setAlignment(Pos.CENTER);
        addListeners();
    }

    public void addListeners() {
        filters.setOnAction(e -> AppFrame.recipeList.filterRecipes(filters.getValue()));
        sortButtonName.setOnAction(e -> {
            AppFrame.recipeList.sortRecipesByName();
        });
        sortButtonDate.setOnAction(e -> {
            AppFrame.recipeList.sortRecipesByDate();
        });
    }
}