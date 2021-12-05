package teste;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FlaggedConditions {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(getConditions());
    }

    public static Map<String, ArrayList<String>> getConditions() throws FileNotFoundException {
        Map<String, ArrayList<String>> outerMap = new HashMap<String, ArrayList<String>>();
        // File file = new File("/Users/victorkim/Documents/POLI/TCC/tcc/logFile.out");
        // TODO: mudar "teste" para "xisnove"
        Path frequencyAnalysisFile = Paths.get("src/main/java/teste/frequency_analyser/conditions_of_interest.out").toAbsolutePath();
        // Path frequencyAnalysisFile = Paths.get("tcc/teste/src/main/java/teste/frequency_analyser/conditions_of_interest.out").toAbsolutePath();

        // File file = new File("C:\\Users\\felip\\Programs\\mycodes\\tcc\\tcc\\logFile.out");
        // ClassLoader classLoader = FlaggedConditions.class.getClassLoader();
        File file = new File(frequencyAnalysisFile.normalize().toString());
        Scanner sc = new Scanner(file);
        // int counter = 0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] split = line.split("\\#");
            // Array map = new HashMap();
            String className = split[0];
            String methodName = split[1];
            String statementType = split[2];
            String condition = split[3];
            if (!outerMap.containsKey(className + "#" + methodName + '#' + statementType)) {
                ArrayList<String> conditions = new ArrayList<String>();
                conditions.add(condition);
                outerMap.put(className + "#" + methodName + '#' + statementType, conditions);
            } else {
                ArrayList<String> conditions = outerMap.get(className + "#" + methodName + '#' + statementType);
                if (!conditions.contains(condition)) {
                    conditions.add(condition);
                }
            }
        }
        sc.close();
        return outerMap;
    }
}
