package teste;

import teste.logFile.LogFile;
import java.io.IOException;

public class MethodsExample {

    private static Integer a = 5;

    private static Integer b = 5;

    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");
        LogFile.write("MethodsExample#main", "ifStmt", "a < 10 || b < 5", a < 10 || b < 5, "[a < 10, b < 5]", String.valueOf(a < 10), String.valueOf(b < 5));
        if (a < 10 || b < 5) {
            System.out.println("a é menor que 10");
        }
    }

    public static void funcaoNestedIfs() throws IOException {
        LogFile.write("MethodsExample#funcaoNestedIfs", "ifStmt", "a < 10 && b == 5", a < 10 && b == 5, "[a < 10, b == 5]", String.valueOf(a < 10), String.valueOf(b == 5));
        if (a < 10 && b == 5) {
            System.out.println("a é menor que 10");
            LogFile.write("MethodsExample#funcaoNestedIfs", "ifStmt", "b > 1", b > 1, "[b]", String.valueOf(b));
            if (b > 1) {
                System.out.println(b);
                LogFile.write("MethodsExample#funcaoNestedIfs", "ifStmt", "b == 1", b == 1, "[b]", String.valueOf(b));
                if (b == 1) {
                    System.out.println(b);
                }
            }
        }
    }

    public static void funcaoIfElseNested() throws IOException {
        LogFile.write("MethodsExample#funcaoIfElseNested", "ifStmt", "a < 1", a < 1, "[a]", String.valueOf(a));
        if (a < 1) {
            System.out.println(a);
        } else {
            LogFile.write("MethodsExample#funcaoIfElseNested", "ifStmt", "b > 1", b > 1, "[b]", String.valueOf(b));
            if (b > 1) {
                System.out.println(b);
            }
            LogFile.write("MethodsExample#funcaoIfElseNested", "ifStmt", "b == 1", b == 1, "[b]", String.valueOf(b));
            if (b == 1) {
                System.out.println(b);
            }
        }
    }

    public static void funcaoIfElseIf() throws IOException {
        System.out.println("Hello World!");
        LogFile.write("MethodsExample#funcaoIfElseIf", "ifStmt", "a < 10", a < 10, "[a]", String.valueOf(a));
        LogFile.write("MethodsExample#funcaoIfElseIf", "ifStmt", "b > 10 || b == 10", b > 10 || b == 10, "[b > 10, b == 10]", String.valueOf(b > 10), String.valueOf(b == 10));
        LogFile.write("MethodsExample#funcaoIfElseIf", "ifStmt", "b != a", b != a, "[b, a]", String.valueOf(b), String.valueOf(a));
        if (a < 10) {
            System.out.println("a é menor que 10");
        } else if (b > 10 || b == 10) {
            System.out.println("boolean1 é true");
        } else if (b != a) {
            System.out.println("boolean1 é true");
        } else {
            System.out.println("else sem if");
        }
    }

    public static void funcaoIfmultiplasCondicoes() throws IOException {
        LogFile.write("MethodsExample#funcaoIfmultiplasCondicoes", "ifStmt", "boolean1 || a == b", boolean1 || a == b, "[boolean1, a == b]", String.valueOf(boolean1), String.valueOf(a == b));
        if (boolean1 || a == b) {
            System.out.println("funcaoIfmultiplasCondicoes");
        }
    }
}
