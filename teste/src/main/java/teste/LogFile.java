package teste;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogFile {

    public static void write(String className, String methodName, Object object) throws IOException {
        FileWriter file = new FileWriter("logFile.out", true);
        String t = object.toString();
        System.out.println(t);
        // array, se for nested, se nao for, loga mais uma linha
        System.out.println("class#methodName#[statementType1:params1,...,statementTypeN:paramsN]");
        String formatted = String.format("%s#%s#%s\n", className, methodName, t);
        try {
            file.write(formatted);
        } catch (IOException e) {
        }
        file.close();
    }
}
