package main.java;





import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public abstract class Screen extends BorderPane {
    protected Header header;
    protected Center center;
    protected Footer footer;
    private String title;

    Screen() {
        header = new Header("");
        center = new Center();
        footer = new Footer();

        this.setTop(header);
        this.setCenter(center);
        this.setBottom(footer);
    }

    protected abstract Screen createNextScreen();
    protected abstract Screen createPreviousScreen();

    public void setHeaderText(String text) {
        header.changeTitleText(text);
    }

    public void setCenterObject() {

    }

    public void setNextTitle(String title) {
        this.title = title;
    }

    public void setFooterButtons(String leftText, String centerText, String rightText) {
        footer.setButtons(leftText, centerText, rightText);
    }

    public void setLeftButtonAction(String title, EventHandler<ActionEvent> event) {
        setNextTitle(title);
        footer.setLeftButtonAction(event);
    }

    public void setCenterButtonAction(String title, EventHandler<ActionEvent> event) {
        setNextTitle(title);
        footer.setCenterButtonAction(event);
    }

    public void setRightButtonAction(String title, EventHandler<ActionEvent> event) {
        setNextTitle(title);
        footer.setRightButtonAction(event);
    }

    public void changeScreen(Screen screenTwo) {
        Scene scene = getScene();
        Window screen = scene.getWindow();
        if (screen instanceof Stage) {
            Stage current = (Stage) screen;
            current.setTitle(title);
            current.setScene(new Scene(screenTwo, 500, 500));
            current.setResizable(false);
            current.show();
        }
    }

    // action event 
    public void changeNextScreenEvent (ActionEvent e) {
        Screen nextScreen = createNextScreen();
        changeScreen(nextScreen);
    } 

    // action event 
    public void changePreviousScreenEvent (ActionEvent e) {
        Screen previousScreen = createPreviousScreen();
        changeScreen(previousScreen);
    } 
}
