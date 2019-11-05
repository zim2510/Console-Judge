import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class Problem
{
    private static String basePath = System.getProperty("user.home") + "/CJ/Problems";

    private String problemId = null;
    String problemPath = null;

    Problem(String problemId){

        this.problemId = problemId;
        this.problemPath = basePath + "/" + problemId;
    }

    void view() throws IOException {
        ProcessBuilder p = new ProcessBuilder();
        p.directory(new File(problemPath));
        p.command("xdg-open", "Statement.pdf");
        p.start();
    }


    static ArrayList getProblemList()
    {
        String [] pList = new File(basePath).list();
        ArrayList<String> probList = new ArrayList();
        for(String problemId: pList){
            if(!problemId.substring(0, 3).equals("NAP")) probList.add(problemId);
        }
        return probList;
    }
}