package br.usp.larc;

import br.usp.larc.Benchmark.PasswordHashing.BCrypt;
import br.usp.larc.Benchmark.PasswordHashing.BCryptInstrumented;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(value = 2)
@Warmup(iterations = 2)
@Measurement(iterations = 10)
public class BCryptBenchmark {
    private static final String test_vector[] = { "abc",
			"$2a$10$WvvTPHKwdBJ3uk0Z37EMR.",
			"$2a$10$WvvTPHKwdBJ3uk0Z37EMR.hLA2W6N9AEBhEgrAOljy2Ae5MtaSIUi" };

    @Benchmark
    public String[] testHashpwInstrumented() {
        String plain = test_vector[0];
        String salt = test_vector[1];
        String expected = test_vector[2];
        String hashed = BCryptInstrumented.hashpw(plain, salt);

        String[] values = new String[2];
        values[0] = expected;
        values[1] = hashed;

        return values;
	}

    @Benchmark
    public String[] testHashpw() {
        String plain = test_vector[0];
        String salt = test_vector[1];
        String expected = test_vector[2];
        String hashed = BCrypt.hashpw(plain, salt);

        String[] values = new String[2];
        values[0] = expected;
        values[1] = hashed;

        return values;
	}

    public static void main(String[] args) {
        BCryptBenchmark benchmark = new BCryptBenchmark();
        
        benchmark.testHashpw();
        benchmark.testHashpwInstrumented();
    }
}
