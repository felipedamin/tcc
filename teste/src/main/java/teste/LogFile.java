package teste;

import java.io.FileWriter;
import java.io.IOException;

public class LogFile {

    public static void write(String className, String methodName, String conditionType, String[] conditions, Object[] paramValue, boolean[] values) throws IOException {
        FileWriter file = new FileWriter("logFile.out", true);
        // array, se for nested, se nao for, loga mais uma linha
        System.out.println("class#methodName#[statementType1:params1,...,statementTypeN:paramsN]");

        // ordem de implementaçao:
        // "class#methodName#[a<10,b!=a]#[true,false]
        // "class#methodName#[a<10,b!=a]#[a.toString(),b.toString()]#[true,false]
        // "class#methodName#(if|for|while|switch)#[a<10,b!=a]#[a,b]#[a.toString(),b.toString()]#[true,false]"
        String formatted = String.format("%s#%s#%s\n", className, methodName);
        try {
            file.write(formatted);
        } catch (IOException e) {
        }
        file.close();
    }
}
