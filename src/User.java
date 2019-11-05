import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class User {
    String userId;
    Map<String, Submission> submissionList;
    ArrayList <Problem> problemList;
    Scanner cin = new Scanner(System.in);

    User(String userId){
        this.userId = userId;
        problemList = Problem.getProblemList();
        submissionList = new HashMap();
        for(Problem tmp: problemList){
            Submission s = new Submission(tmp);
            submissionList.put(tmp.problemId, s);
        }
    }

    public void viewProblemList(){
        System.out.println("-----------Problem List--------------------");
        System.out.println("-------------------------------------------");
        for (Problem p : problemList) System.out.println(" " + p.problemId);
        System.out.println("");
    }

    public void viewProblem(){
        System.out.println("-----------View Problem--------------------");
        System.out.println("-------------------------------------------");
        System.out.print("Enter problem ID: ");
        String problemId = cin.next();
        Submission s = submissionList.get(problemId);
        if(s!=null){
            try {
                s.problem.view();
            } catch (Exception e) {
                System.out.println("Error occured. No one knows why.");
            }
        }
        else System.out.println("Invalid problem ID");
        System.out.println("");
    }

    public void editSol() throws IOException {
        System.out.println("-----------Edit Solution-------------------");
        System.out.println("-------------------------------------------");
        System.out.print("Enter problem ID: ");
        String problemId = cin.next();
        Submission s = submissionList.get(problemId);
        if(s!=null) s.editSol();
        else System.out.println("Invalid problem ID");
        System.out.println("");
    }

    public void runJudge() throws IOException, InterruptedException {
        System.out.println("-----------Edit Solution-------------------");
        System.out.println("-------------------------------------------");
        System.out.print("Enter problem ID: ");
        String problemId = cin.next();
        Submission s = submissionList.get(problemId);
        if(s!=null){
            System.out.println("Problem Submitted\nWait for verdict");
            System.out.println("Verdict: " + s.judge());
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
