package br.usp.larc;

import br.usp.larc.Benchmark.ApacheCommons.ArrayUtils;
import br.usp.larc.Benchmark.ApacheCommons.ArrayUtilsInstrumented;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Warmup;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(1)
@Warmup(iterations = 1)
@Measurement(iterations = 5)
public class ArrayUtilsBenchmark {
    private static final int ARRAY_SIZE = 100_000;
    private static final Integer OBJECT_TO_FIND = ARRAY_SIZE - 1;
    private static Integer[] array;

    @Benchmark
    public void baseline() {
        // do nothing, this is a baseline
    }

    @Benchmark
    public int indexOfWithStartIndex() {
        return ArrayUtils.indexOf(array, OBJECT_TO_FIND, 0);
    }

    @Benchmark
    public int indexOfWithStartIndexInstrumented() {
        return ArrayUtilsInstrumented.indexOf(array, OBJECT_TO_FIND, 0);
    }

    public static void main(String[] args) {
        array = generateIntegerArray(ARRAY_SIZE);

        ArrayUtilsBenchmark benchmark = new ArrayUtilsBenchmark();
        
        benchmark.baseline();
        benchmark.indexOfWithStartIndex();
        benchmark.indexOfWithStartIndexInstrumented();
    }

    private static Integer[] generateIntegerArray(int size) {
        Integer[] array = new Integer[size];
        Arrays.fill(array, 0, size - 1, 0);
        array[size - 1] = size - 1;
        return array;
    }

}
