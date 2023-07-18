package br.usp.larc;

import br.usp.larc.Benchmark.PasswordHashing.BCryptHashing;
import br.usp.larc.Benchmark.PasswordHashing.BCryptHashingInstrumented;
import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(value = 2)
@Warmup(iterations = 2)
@Measurement(iterations = 10)
public class PasswordValidatorBenchmark {

    private static final String PASSWORD = "myPassword123";
    private static final String HASHED_PASSWORD = BCryptHashing.hashPassword(PASSWORD);

    @Benchmark
    public boolean verifyPasswordBenchmark() {
        return BCryptHashing.verifyPassword(PASSWORD, HASHED_PASSWORD);
    }

    @Benchmark
    public boolean verifyPasswordBenchmarkInstrumented() {
        return BCryptHashingInstrumented.verifyPassword(PASSWORD, HASHED_PASSWORD);
    }


    public static void main(String[] args) {
        PasswordValidatorBenchmark benchmark = new PasswordValidatorBenchmark();
        
        benchmark.verifyPasswordBenchmark();        
        benchmark.verifyPasswordBenchmarkInstrumented();
    }
}
