import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        File CJbase = new File(System.getProperty("user.home") + "/CJ");

        if(!CJbase.exists()){
            CJbase.mkdir();
            new File(System.getProperty("user.home") + "/CJ/Users").mkdir();
            new File(System.getProperty("user.home") + "/CJ/Problems").mkdir();

        }
        ///TODO: Login Page
        User defaultUser = new User("TestUser");
        defaultUser.beginRender();
    }

}
