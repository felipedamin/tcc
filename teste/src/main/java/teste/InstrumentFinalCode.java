package teste;

import java.util.HashMap;
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
        Map outerMap = new HashMap();
        File file = new File("/Users/victorkim/Documents/POLI/TCC/tcc/logFile.out");
        Scanner sc = new Scanner(file);
        int counter = 0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] split = line.split("\\#");
            Map map = new HashMap();
            map.put("className", split[0]);
            map.put("methodName", split[1]);
            map.put("condition", split[2]);
            outerMap.put(counter, map);
            counter++;
        }
        return outerMap;
    }
}