import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    
    /** The number of test samples */
    private double[] samples;
    
    /**
     * Create a randomized set of percolation trials
     * @param n      The width and height of the grid
     * @param trials The number of trials to perform
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        }
        if (trials <= 0) {
            throw new IllegalArgumentException("trials must be greater than 0");
        }
        samples = new double[trials];

        for (int i = 0; i < trials; i++) {
            samples[i] = samplePercolationThreshold(n) / (n * n);
        }
    }
    
    /**
     * Perform a monte-carlo simulation on a grid
     * @param n The size of the grid
     * @return  The number of sites open at the time of percolation
     */
    private double samplePercolationThreshold(int n) {
        Percolation perc = new Percolation(n);
        while (!perc.percolates()) {
            // This has O(infinity) worst case running time
            // But should do okay on the average case
            int row = StdRandom.uniform(1, n + 1);
            int col = StdRandom.uniform(1, n + 1);
            perc.open(row, col);
        }

        return perc.numberOfOpenSites();
    }

    /**
     * @return The mean of the samples
     */
    public double mean() {
        return StdStats.mean(samples);
    }

    /**
     * @return The standard deviation of the samples
     */
    public double stddev() {
        return StdStats.stddev(samples);
    }

    /**
     * @return The lower end of the 95% confidence interval
     */
    public double confidenceLo() {
        return mean() - (1.96 * stddev()) / Math.sqrt(samples.length);
    }

    /**
     * @return The higher end of the 95% confidence interval
     */
    public double confidenceHi() {
        return mean() + (1.96 * stddev()) / Math.sqrt(samples.length);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        
        PercolationStats stats = new PercolationStats(n, t);

        StdOut.printf("mean                     = %f%n", stats.mean());
        StdOut.printf("stddev                   = %f%n", stats.stddev());
        StdOut.printf("95%% confidence interval = [%f, %f]%n",
            stats.confidenceLo(),
            stats.confidenceHi()
        );
    }
}
