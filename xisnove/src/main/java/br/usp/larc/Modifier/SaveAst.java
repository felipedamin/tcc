package br.usp.larc.Modifier;

import java.io.FileWriter;
import java.io.PrintWriter;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.DotPrinter;

public class SaveAst {

    public static void saveAsDotFile(String fileName, CompilationUnit compilationUnit){
        DotPrinter printer = new DotPrinter(true);
        try (FileWriter fileWriter = new FileWriter("astBefore.dot")) {
            try (PrintWriter printWriter = new PrintWriter(fileName)) {
                printWriter.print(printer.output(compilationUnit));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
