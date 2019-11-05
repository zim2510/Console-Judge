import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        File OJbase = new File(System.getProperty("user.home") + "/OJ");
        if(!OJbase.exists()){
            OJbase.mkdir();
            new File(System.getProperty("user.home") + "/OJ/Users").mkdir();
            new File(System.getProperty("user.home") + "/OJ/Problems").mkdir();

        }
        User defaultUser = new User("Jim");
        defaultUser.beginRender();
    }

}
