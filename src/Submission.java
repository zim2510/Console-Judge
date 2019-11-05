import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Submission {
    private String probPath = null;
    private String userSolPath = null;
    private String verdict = null;

    Submission(String probPath, String userSolPath){
        this.probPath = probPath;
        this.userSolPath = userSolPath;
        verdict = "Not Judged yet";
    }

    private void clean() throws IOException {
        ProcessBuilder PB = new ProcessBuilder();
        PB.directory(new File(probPath));
        PB.command("rm", "Run", "DiffOut", "TESTOUT", "ProcessError", "ProcessOut");
        PB.start();
    }

    String judge() throws InterruptedException, IOException {
        File problemDir = new File(probPath);
        ProcessBuilder init = new ProcessBuilder();

        init.directory(problemDir);
        init.redirectError(new File(problemDir + "/ProcessError"));
        init.redirectOutput(new File(problemDir + "/ProcessOut"));

        /**Compilation*/
        ProcessBuilder compile = init;
        compile.command("g++", "-O2", "-static", "-std=c++17", "-DONLINE-JUDGE", "-Wall", userSolPath + "/" + "Solution.cpp", "-o", "Run");
        Process compileProcess = compile.start();
        compileProcess.waitFor();

        int eCode = compileProcess.exitValue();
        if (eCode != 0) return verdict = "Compilation error";

        /**Run code*/
        ProcessBuilder run = init;
        run.redirectInput(new File(problemDir + "/JUDGEIN"));
        run.redirectOutput(new File(probPath + "/TESTOUT"));
        run.command("./Run");
        Process runProcess = run.start();
        runProcess.waitFor(3, TimeUnit.SECONDS);

        if (runProcess.isAlive()) {
            runProcess.destroyForcibly();
            clean();
            return verdict = "Time limit exceed";
        }

        eCode = runProcess.exitValue();

        if (eCode != 0) {
            return verdict = "Runtime error";
        } else {

            File diffFile = new File(probPath + "/DiffOut");

            ProcessBuilder diff = init;
            diff.command("diff", "TESTOUT", "JUDGEOUT");
            diff.redirectOutput(diffFile);
            Process diffProcess = diff.start();

            diffProcess.waitFor();

            if (diffFile.length() == 0){
                clean();
                return verdict = "Accepted";
            }
            else {
                clean();
                return verdict = "Wrong Answer";
            }

        }
    }

    public String getVerdict(){
        return verdict;
    }


}
