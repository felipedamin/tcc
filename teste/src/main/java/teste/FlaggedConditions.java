package teste;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class FlaggedConditions {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(getConditions());
    }

    public static Map<String, ArrayList<String>> getConditions() throws FileNotFoundException {
        Map<String, ArrayList<String>> outerMap = new HashMap<String, ArrayList<String>>();
        // File file = new File("/Users/victorkim/Documents/POLI/TCC/tcc/logFile.out");
        File file = new File("C:\\Users\\felip\\Programs\\mycodes\\tcc\\tcc\\logFile.out");
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
        sc.close();
        return outerMap;
    }
}