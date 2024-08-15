
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;



public class testAccountCreation {
    //create a mock username and password
    //assert that it is in the json file
    private String mockUser;
    private String mockPass;
    private String fakeUser;
    private String fakePass;
    private Accounts account;
    private Boolean thrown;

    @Test
    public void testAccountCreate() throws IOException, InterruptedException, URISyntaxException{
        thrown = false;
        mockUser = "testUsername";
        mockPass = "testPassword";
        
        fakeUser = "nonExistentUser";
        fakePass = "nonExistentPass";
        
        account = new Accounts();
        //addUserAccount()
        //add mockUser and mockPass through alex's :( functions to add to userPw.json
        
        assertTrue(account.addUserAccount(mockUser, mockPass));
        assertTrue(account.checkLogin(mockUser, mockPass));

        try{
            account.checkLogin(fakeUser, fakePass);
        } catch (Exception e){
            thrown = true;
        }
        assertTrue(thrown);
    }
}