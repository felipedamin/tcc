package benchmark;

import java.util.Arrays;

public class Benchmarks {
    public static void main(String[] args) {
        long[] resultsBench10 = new long[100];
        for (int i =0; i<100; i++) {
            long bench10 = Benchmark10.main();
            resultsBench10[i] = bench10;
        }
        // System.out.println(Arrays.toString(resultsBench10));

        long[] resultsBench100 = new long[100];
        for (int i =0; i<100; i++) {
            long bench100 = Benchmark100.main();
            resultsBench100[i] = bench100;
        }

        long[] resultsBench1000 = new long[100];
        for (int i =0; i<100; i++) {
            long bench1000 = Benchmark1000.main();
            resultsBench1000[i] = bench1000;
        }

        // Average: 52260.73
        // Standard Deviation: 44697.64359177226
        getBenchmarkStatistics(resultsBench10);
        getBenchmarkStatistics(resultsBench100);
        getBenchmarkStatistics(resultsBench1000);


    }

    private static void getBenchmarkStatistics(long[] bench) {
        // Calculate the average
        double sum = 0;
        for (long time : bench) {
            sum += time;
        }
        double average = sum / bench.length;
        System.out.println("Average: " + average);

        // Calculate the standard deviation
        double deviationSum = 0;
        for (long time : bench) {
            double deviation = time - average;
            deviationSum += deviation * deviation;
        }
        double standardDeviation = Math.sqrt(deviationSum / bench.length);
        System.out.println("Standard Deviation: " + standardDeviation);
    }
}
