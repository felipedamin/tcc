package teste;

import java.io.File;
import java.util.List;

public class ListFilesToParse {

    public static void listFiles(File dirPath, List<String> filesToParse) {
        File[] filesList = dirPath.listFiles();
        for (File file : filesList) {
            if (file.isFile()) {
                if (file.getName().endsWith(".java")) {
                    filesToParse.add(file.toString());
                }
            } else {
                listFiles(file, filesToParse);
            }
        }
    }
}
