import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Submission {
    private String dirPath = null;
    private String verdict = null;
    public Problem problem = null;

    Submission(Problem prob){
        dirPath = System.getProperty("user.home") + "/OJ" + "/Problems/" + prob.problemId;
        verdict = "Not Judged yet";
        problem = prob;
    }

    String judge() throws InterruptedException, IOException {
        File problemDir = new File(dirPath);

        ProcessBuilder init = new ProcessBuilder();

        init.directory(problemDir);
        init.redirectError(new File(dirPath + "/ProcessError"));
        init.redirectOutput(new File(dirPath + "/ProcessOut"));

        /**Compilation*/
        ProcessBuilder compile = init;
        compile.command("g++", "-O2", "-static", "-std=c++17", "-DONLINE-JUDGE", "-Wall", "Solution.cpp", "-o", "Run");
        Process compileProcess = compile.start();
        compileProcess.waitFor();

        int eCode = compileProcess.exitValue();
        if (eCode != 0) return verdict = "Compilation error";

        /**Run code*/
        ProcessBuilder run = init;
        run.command("./Run");
        run.redirectOutput(new File(dirPath + "/TESTOUT"));
        Process runProcess = run.start();
        runProcess.waitFor(1, TimeUnit.SECONDS);
        runProcess.info();

        if (runProcess.isAlive()) {
            runProcess.destroyForcibly();
            return verdict = "Time limit exceed";
        }

        eCode = runProcess.exitValue();

        if (eCode != 0) {
            return verdict = "Runtime error";
        } else {

            File diffFile = new File(dirPath + "/DiffOut");

            ProcessBuilder diff = init;
            diff.command("diff", "TESTOUT", "JUDGEOUT");
            diff.redirectOutput(diffFile);
            Process diffProcess = diff.start();

            diffProcess.waitFor();

            if (diffFile.length() == 0) return verdict = "Accepted";
            else return verdict = "Wrong Answer";

        }
    }

    public String getVerdict(){
        return verdict;
    }

    void editSol() throws IOException {
        ProcessBuilder edit = new ProcessBuilder();
        edit.directory(new File(dirPath));
        System.out.println(dirPath);
        edit.command("xdg-open", "Solution.cpp");
        edit.start();
    }
}
