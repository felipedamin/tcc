package teste.logFile;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;

public class LogFile {

    public static void write(String classAndMethodName, String conditionType, String condition, String[] conditionParams, Object[] paramValue, boolean finalValue) throws IOException {
        FileWriter file = new FileWriter("logFile.out", true);
        // array, se for nested, se nao for, loga mais uma linha
        System.out.println("class#methodName#[statementType1:params1,...,statementTypeN:paramsN]");
        // ordem de implementaçao:
        // "class#methodName#[a<10,b!=a]#true
        // "class#methodName#a<10 && b!=a#[a.toString(),b.toString()]#true
        // "class#methodName#(if|for|while|switch)#a<10 && b!=a#[a,b]#[a.toString(),b.toString()]#true"
        // "class#methodName#(if|for|while|switch)#a<10 && b!=a#[a,b]#[a.toString(),b.toString()]#true#(numLinha|hash)"
        String formatted = String.format("%s#%s#%s\n", classAndMethodName);
        try {
            file.write(formatted);
        } catch (IOException e) {
            System.err.println(e);
        }
        file.close();
    }

    public static void write(String classAndMethodName, String conditionType, String condition, boolean finalValue, String allTokens, String ...tokenValues) throws IOException {
        // "class#methodName#a<10 && b!=a#true
        Path projectRoot = Paths.get("src/main/java/teste/logFile").toAbsolutePath();
        FileWriter file = new FileWriter(projectRoot.normalize().toString() + "/logFile.out", true);

        String formatted = String.format("%s#%s#%s#%b#%s#%s\n", classAndMethodName, conditionType, condition, finalValue, allTokens, String.join(",", tokenValues));
        try {
            file.write(formatted);
        } catch (IOException e) {
            System.err.println(e);
        }

        file.close();
    }

    public static void write(String classAndMethodName, String conditionType, String condition, boolean finalValue) throws IOException {
        // "class#methodName#a<10 && b!=a#true
        // TODO: mudar "teste" para "xisnove"
        Path projectRoot = Paths.get("src/main/java/teste/logFile").toAbsolutePath();
        FileWriter file = new FileWriter(projectRoot.normalize().toString() + "/logFile.out", true);
        // array, se for nested, se nao for, loga mais uma linha
        System.out.println(conditionType);
        String formatted = String.format("%s#%s#%s#%b\n", classAndMethodName, conditionType, condition, finalValue);
        try {
            file.write(formatted);
        } catch (IOException e) {
            System.err.println(e);
        }
        file.close();
    }
}
