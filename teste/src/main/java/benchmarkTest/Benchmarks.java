package benchmarkTest;

import java.io.IOException;
import java.util.Arrays;

import teste.AstFindAllConditionalModifier;

public class Benchmarks {
    public static void main(String[] args) {
        try {
            double[] benchStats = runBenchmark10();
			double[] benchXisnoveStats = runXisnove();

            System.out.println("\nBench10 (ms):");
            printStatsArray(benchStats);
        
            System.out.println("Xisnove (ms):");
            printStatsArray(benchXisnoveStats);
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private static double[] runXisnove() throws IOException {
        String[] xisnoveArgs = new String[0];
        long[] resultsBenchXisnove = new long[100];

        for (int i =0; i<100; i++) {
            long startTime = System.nanoTime(); 
            AstFindAllConditionalModifier.main(xisnoveArgs);
            long estimatedTime = System.nanoTime() - startTime;
            resultsBenchXisnove[i] = estimatedTime;
        }

        double[] benchStats = calculateBoxPlotValues(resultsBenchXisnove);
        return benchStats;

    }

    private static double[] runBenchmark10() {
        long[] resultsBench10 = new long[100];
        for (int i =0; i<100; i++) {
            long bench10 = Benchmark10.main();
            resultsBench10[i] = bench10;
        }
        double[] benchStats = calculateBoxPlotValues(resultsBench10);
        return benchStats;
    }

    private static double[] runBenchmark100() {
        long[] resultsBench100 = new long[100];
        for (int i =0; i<100; i++) {
            long bench100 = Benchmark100.main();
            resultsBench100[i] = bench100;
        }
        double[] benchStats = calculateBoxPlotValues(resultsBench100);
        return benchStats;
    }

    private static double[] runBenchmark1000() {
        long[] resultsBench1000 = new long[100];
        for (int i =0; i<100; i++) {
            long bench1000 = Benchmark1000.main();
            resultsBench1000[i] = bench1000;
        }
        double[] benchStats = calculateBoxPlotValues(resultsBench1000);
        return benchStats;
    }

    private static double[] getAvgStddev(long[] bench) {
        // Calculate the average
        double sum = 0;
        for (long time : bench) {
            sum += time;
        }
        double average = sum / bench.length;
        // System.out.println("Average: " + average);

        // Calculate the standard deviation
        double deviationSum = 0;
        for (long time : bench) {
            double deviation = time - average;
            deviationSum += deviation * deviation;
        }
        double standardDeviation = Math.sqrt(deviationSum / bench.length);
        // System.out.println("Standard Deviation: " + standardDeviation);

        double[] result = {average, standardDeviation};
        return result;
    }

    public static double[] calculateBoxPlotValues(long[] values) {
        // Sort the values in ascending order
        long[] sortedValues = Arrays.copyOf(values, values.length);
        Arrays.sort(sortedValues);

        int n = sortedValues.length;
        double median = calculateMedian(sortedValues);
        double lowerQuartile = calculateMedian(Arrays.copyOfRange(sortedValues, 0, n / 2));
        double upperQuartile = calculateMedian(Arrays.copyOfRange(sortedValues, (n + 1) / 2, n));

        // Calculate the interquartile range (IQR)
        double iqr = upperQuartile - lowerQuartile;

        // Calculate the lower and upper bounds for outliers
        double lowerBound = lowerQuartile - 1.5 * iqr;
        double upperBound = upperQuartile + 1.5 * iqr;

        // Find the minimum and maximum within the bounds
        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;
        for (double value : sortedValues) {
            if (value >= lowerBound && value <= upperBound) {
                min = Math.min(min, value);
                max = Math.max(max, value);
            }
        }

        return new double[]{min, lowerQuartile, median, upperQuartile, max};
    }

    private static double calculateMedian(long[] values) {
        int n = values.length;
        if (n % 2 == 0) {
            return (values[n / 2 - 1] + values[n / 2]) / 2.0;
        } else {
            return values[n / 2];
        }
    }

    public static void printStatsArray(double[] array) {
        for (double value : array) {
            System.out.println(value/1000);
        }
    }
}