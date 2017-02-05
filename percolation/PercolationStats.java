import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by cphetlo on 1/29/17.
 */
public class PercolationStats {

    private double[] threshold;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        int openCount, row, column;

        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("Arguments out of bound");

        threshold = new double[trials];

        openCount = 0;
        for (int i = 0; i < trials; i++) {
            Percolation pl = new Percolation(n);
            do {
                row = StdRandom.uniform(1, n + 1);
                column = StdRandom.uniform(1, n + 1);
                if (pl.isOpen(row, column))
                    continue;
                pl.open(row, column);
                openCount++;
            } while (!pl.percolates());

            threshold[i] = (double) openCount / (n * n);
            openCount = 0;
            //System.out.printf("threshold[%03d] = %f\n", i, threshold[i]);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(threshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(threshold);
    }

    private double halfInterval() {
        return 1.96 * stddev() / Math.sqrt(threshold.length);
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        return mean() - halfInterval();
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        return mean() + halfInterval();
    }

    // test client (described below)
    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        int trial = Integer.parseInt(args[1]);

        Stopwatch stopwatch = new Stopwatch();
        PercolationStats pls = new PercolationStats(n, trial);

        StdOut.printf("mean                     = %f\n", pls.mean());
        StdOut.printf("stddev                   = %f\n", pls.stddev());
        StdOut.printf("95%% confidence Interval  = %f, %f\n",
                pls.confidenceLo(), pls.confidenceHi());

        StdOut.printf("grid size:%d trial:%d time:%f", n, trial, stopwatch.elapsedTime());

    }
}
