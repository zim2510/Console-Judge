import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Problem
{
    public String problemId;
    public int timeLimit;
    static String basePath = System.getProperty("user.home") + "/OJ/Problems";

    Problem(String problemId){
        this.problemId = problemId;
    }

    public void view() throws IOException {
        System.out.println(basePath);
        ProcessBuilder p = new ProcessBuilder();
        p.directory(new File(basePath + "/" + problemId));
        p.command("xdg-open", "Statement.pdf");
        p.start();
    }


    public static ArrayList getProblemList()
    {
        String [] pList = new File(basePath).list();
        ArrayList<Problem> probList = new ArrayList();
        for(String problemId: pList){
            if(!problemId.substring(0, 3).equals("NAP")) probList.add(new Problem(problemId));
        }
        return probList;
    }
}