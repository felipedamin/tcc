package teste;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileNotFoundException;

public class InstrumentFinalCode {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(instrument());
    }

    public static Map instrument() throws FileNotFoundException {
        Map<String, ArrayList<String>> outerMap = new HashMap();
        File file = new File("/Users/victorkim/Documents/POLI/TCC/tcc/logFile.out");
        Scanner sc = new Scanner(file);
        // int counter = 0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] split = line.split("\\#");
            // Array map = new HashMap();
            String className = split[0];
            String methodName = split[1];
            String condition = split[2];
            if (!outerMap.containsKey(className + "#" + methodName)) {
                ArrayList<String> conditions = new ArrayList<String>();
                conditions.add(condition);
                outerMap.put(className + "#" + methodName, conditions);
            }
            else {
                ArrayList<String> conditions = outerMap.get(className + "#" + methodName);
                if (!conditions.contains(condition)) {
                    conditions.add(condition);
                }
            }
        }
        return outerMap;
    }
}