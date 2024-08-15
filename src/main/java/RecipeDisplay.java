package main.java;





import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;

public class RecipeDisplay extends BorderPane{

    RecipeDisplay() {
        ScrollPane scroller = new ScrollPane(AppFrame.getAppRecipeList());
        scroller.setFitToWidth(true);
        scroller.setFitToHeight(true);
        this.setCenter(scroller);
    }

}
