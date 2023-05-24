package benchmarkTest;

import xisnove.logFile.LogFile;
import java.io.IOException;

public class BenchmarkInstrumented100 {

    private static Integer a = 5;

    private static Integer b = 10;

    private static Boolean boolean1 = true;

    private static Boolean boolean2 = false;

    public static long main() throws IOException {
        long startTime = System.nanoTime();
        LogFile.write("Benchmark100#main", "ifStmt", "a != 10", a != 10, "[a]", String.valueOf(a));
        if (a != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "b != 10", b != 10, "[b]", String.valueOf(b));
        if (b != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean1", boolean1);
        if (boolean1) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean2", boolean2);
        if (boolean2) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a == b", a == b, "[a, b]", String.valueOf(a), String.valueOf(b));
        if (a == b) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a != 10", a != 10, "[a]", String.valueOf(a));
        if (a != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "b != 10", b != 10, "[b]", String.valueOf(b));
        if (b != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean1", boolean1);
        if (boolean1) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean2", boolean2);
        if (boolean2) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a == b", a == b, "[a, b]", String.valueOf(a), String.valueOf(b));
        if (a == b) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a != 10", a != 10, "[a]", String.valueOf(a));
        if (a != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "b != 10", b != 10, "[b]", String.valueOf(b));
        if (b != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean1", boolean1);
        if (boolean1) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean2", boolean2);
        if (boolean2) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a == b", a == b, "[a, b]", String.valueOf(a), String.valueOf(b));
        if (a == b) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a != 10", a != 10, "[a]", String.valueOf(a));
        if (a != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "b != 10", b != 10, "[b]", String.valueOf(b));
        if (b != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean1", boolean1);
        if (boolean1) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean2", boolean2);
        if (boolean2) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a == b", a == b, "[a, b]", String.valueOf(a), String.valueOf(b));
        if (a == b) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a != 10", a != 10, "[a]", String.valueOf(a));
        if (a != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "b != 10", b != 10, "[b]", String.valueOf(b));
        if (b != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean1", boolean1);
        if (boolean1) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean2", boolean2);
        if (boolean2) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a == b", a == b, "[a, b]", String.valueOf(a), String.valueOf(b));
        if (a == b) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a != 10", a != 10, "[a]", String.valueOf(a));
        if (a != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "b != 10", b != 10, "[b]", String.valueOf(b));
        if (b != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean1", boolean1);
        if (boolean1) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean2", boolean2);
        if (boolean2) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a == b", a == b, "[a, b]", String.valueOf(a), String.valueOf(b));
        if (a == b) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a != 10", a != 10, "[a]", String.valueOf(a));
        if (a != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "b != 10", b != 10, "[b]", String.valueOf(b));
        if (b != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean1", boolean1);
        if (boolean1) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean2", boolean2);
        if (boolean2) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a == b", a == b, "[a, b]", String.valueOf(a), String.valueOf(b));
        if (a == b) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a != 10", a != 10, "[a]", String.valueOf(a));
        if (a != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "b != 10", b != 10, "[b]", String.valueOf(b));
        if (b != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean1", boolean1);
        if (boolean1) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean2", boolean2);
        if (boolean2) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a == b", a == b, "[a, b]", String.valueOf(a), String.valueOf(b));
        if (a == b) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a != 10", a != 10, "[a]", String.valueOf(a));
        if (a != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "b != 10", b != 10, "[b]", String.valueOf(b));
        if (b != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean1", boolean1);
        if (boolean1) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean2", boolean2);
        if (boolean2) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a == b", a == b, "[a, b]", String.valueOf(a), String.valueOf(b));
        if (a == b) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a != 10", a != 10, "[a]", String.valueOf(a));
        if (a != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "b != 10", b != 10, "[b]", String.valueOf(b));
        if (b != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean1", boolean1);
        if (boolean1) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean2", boolean2);
        if (boolean2) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a == b", a == b, "[a, b]", String.valueOf(a), String.valueOf(b));
        if (a == b) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a != 10", a != 10, "[a]", String.valueOf(a));
        if (a != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "b != 10", b != 10, "[b]", String.valueOf(b));
        if (b != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean1", boolean1);
        if (boolean1) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean2", boolean2);
        if (boolean2) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a == b", a == b, "[a, b]", String.valueOf(a), String.valueOf(b));
        if (a == b) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a != 10", a != 10, "[a]", String.valueOf(a));
        if (a != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "b != 10", b != 10, "[b]", String.valueOf(b));
        if (b != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean1", boolean1);
        if (boolean1) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean2", boolean2);
        if (boolean2) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a == b", a == b, "[a, b]", String.valueOf(a), String.valueOf(b));
        if (a == b) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a != 10", a != 10, "[a]", String.valueOf(a));
        if (a != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "b != 10", b != 10, "[b]", String.valueOf(b));
        if (b != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean1", boolean1);
        if (boolean1) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean2", boolean2);
        if (boolean2) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a == b", a == b, "[a, b]", String.valueOf(a), String.valueOf(b));
        if (a == b) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a != 10", a != 10, "[a]", String.valueOf(a));
        if (a != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "b != 10", b != 10, "[b]", String.valueOf(b));
        if (b != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean1", boolean1);
        if (boolean1) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean2", boolean2);
        if (boolean2) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a == b", a == b, "[a, b]", String.valueOf(a), String.valueOf(b));
        if (a == b) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a != 10", a != 10, "[a]", String.valueOf(a));
        if (a != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "b != 10", b != 10, "[b]", String.valueOf(b));
        if (b != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean1", boolean1);
        if (boolean1) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean2", boolean2);
        if (boolean2) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a == b", a == b, "[a, b]", String.valueOf(a), String.valueOf(b));
        if (a == b) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a != 10", a != 10, "[a]", String.valueOf(a));
        if (a != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "b != 10", b != 10, "[b]", String.valueOf(b));
        if (b != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean1", boolean1);
        if (boolean1) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean2", boolean2);
        if (boolean2) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a == b", a == b, "[a, b]", String.valueOf(a), String.valueOf(b));
        if (a == b) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a != 10", a != 10, "[a]", String.valueOf(a));
        if (a != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "b != 10", b != 10, "[b]", String.valueOf(b));
        if (b != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean1", boolean1);
        if (boolean1) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean2", boolean2);
        if (boolean2) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a == b", a == b, "[a, b]", String.valueOf(a), String.valueOf(b));
        if (a == b) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a != 10", a != 10, "[a]", String.valueOf(a));
        if (a != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "b != 10", b != 10, "[b]", String.valueOf(b));
        if (b != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean1", boolean1);
        if (boolean1) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean2", boolean2);
        if (boolean2) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a == b", a == b, "[a, b]", String.valueOf(a), String.valueOf(b));
        if (a == b) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a != 10", a != 10, "[a]", String.valueOf(a));
        if (a != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "b != 10", b != 10, "[b]", String.valueOf(b));
        if (b != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean1", boolean1);
        if (boolean1) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean2", boolean2);
        if (boolean2) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a == b", a == b, "[a, b]", String.valueOf(a), String.valueOf(b));
        if (a == b) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a != 10", a != 10, "[a]", String.valueOf(a));
        if (a != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "b != 10", b != 10, "[b]", String.valueOf(b));
        if (b != 10) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean1", boolean1);
        if (boolean1) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "boolean2", boolean2);
        if (boolean2) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        LogFile.write("Benchmark100#main", "ifStmt", "a == b", a == b, "[a, b]", String.valueOf(a), String.valueOf(b));
        if (a == b) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
        long estimatedTime = System.nanoTime() - startTime;
        System.out.println("Time for 100 conditions: (ns)");
        System.out.println(estimatedTime);
        return estimatedTime;
    }
}
