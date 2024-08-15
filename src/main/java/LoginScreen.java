package main.java;




public class LoginScreen extends Screen {
    private LoginUI login;
    
    LoginScreen() {
        super();
        setHeaderText("PantryPal");
        setCenterObject();
        setFooterButtons("", "", "");
        login = new LoginUI();
        this.setCenter(login);
    }

    public boolean getAutoLogin(){
        return login.getAutoLogin();
    }
    public String getUser(){
        return login.getUser();
    }
    @Override
    protected Screen createNextScreen() {
        return new HomeScreen(login.getUsernameInput());
    }

    @Override
    protected Screen createPreviousScreen() {
        return new HomeScreen(login.getUsernameInput());
    }
}
