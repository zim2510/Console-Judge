import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class User {

    private static String basePath = System.getProperty("user.home") + "/CJ/Users";

    private String userId = null;
    private String userPath = null;

    private HashMap<String, Problem> problemHashMap = null;
    private ArrayList <String> problemList = null;

    private Scanner cin = new Scanner(System.in);

    User(String userId){
        this.userId = userId;
        this.userPath = basePath + "/" + userId;

        if(!new File(userPath).exists()){
            throw new Error("User doesn't exist");
        }

        problemList = Problem.getProblemList();

        problemHashMap = new HashMap();

        for(String tmp: problemList){
            problemHashMap.put(tmp, new Problem(tmp));
        }
    }

    private void viewProblemList(){
        System.out.println("-----------Problem List--------------------");
        System.out.println("-------------------------------------------");
        for (String p : problemList) System.out.println(" " + p);
        System.out.println("");
    }

    private void viewProblem(){
        System.out.println("-----------View Problem--------------------");
        System.out.println("-------------------------------------------");
        System.out.print("Enter problem ID: ");
        String problemId = cin.next();
        Problem s = problemHashMap.get(problemId);
        if(s!=null){
            try {
                s.view();
            } catch (Exception e) {
                System.out.println("Error occurred. No one knows why.");
            }
        }
        else System.out.println("Invalid problem ID");
        System.out.println("");
    }

    private void editSol() throws IOException {
        System.out.println("-----------Edit Solution-------------------");
        System.out.println("-------------------------------------------");
        System.out.print("Enter problem ID: ");

        String problemId = cin.next();
        Problem p = problemHashMap.get(problemId);

        if(p!=null){
            File solDir = new File(userPath + "/" + problemId);

            if(!solDir.exists()){
                solDir.mkdir();
                new File(solDir + "/Solution.cpp").createNewFile();
            }

            ProcessBuilder editPB = new ProcessBuilder();
            editPB.directory(solDir);
            editPB.command("xdg-open", "Solution.cpp");
            editPB.start();
        }
        else System.out.println("Invalid problem ID");
        System.out.println("");
    }

    private void runJudge() throws IOException, InterruptedException {
        System.out.println("-----------Edit Solution-------------------");
        System.out.println("-------------------------------------------");
        System.out.print("Enter problem ID: ");

        String problemId = cin.next();

        if(problemHashMap.get(problemId)!=null){
            Submission tmpSub = new Submission(problemHashMap.get(problemId).problemPath, userPath + "/" + problemId);
            System.out.print("Solution Submitted\nWait for verdict\n");
            System.out.println("Verdict: " + tmpSub.judge());
        }

        else System.out.println("Invalid problem ID");
        System.out.println("");
    }


    public void beginRender() throws IOException, InterruptedException {
        while (true) {
            System.out.println("--------------Main Menu--------------------");
            System.out.println("-------------------------------------------");
            System.out.println("1. View Problem List");
            System.out.println("2. View Problem");
            System.out.println("3. Write/Edit Solution");
            System.out.println("4. Submit Solution");
            System.out.println("5. View User Statistics");
            System.out.println("0. Exit");
            System.out.print("\nEnter choice (0 - 4): ");

            int x = cin.nextInt();
            System.out.println("");

            if(x==0) return;
            if(x==1) viewProblemList();
            else if (x == 2) viewProblem();
            else if (x == 3) editSol();
            else if(x == 4) runJudge();
        }
    }

}

