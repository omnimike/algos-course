import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    
    /** The standard deviation of the test samples */
    private double stddev;
    /** The mean of the test samples */
    private double mean;
    /** The number of test samples */
    private int sampleCount;
    
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
        int[] samples = new int[trials];

        for (int i = 0; i < trials; i++) {
            samples[i] = samplePercolationThreshold(n);
        }

        mean = StdStats.mean(samples);
        stddev = StdStats.stddev(samples);
        sampleCount = trials;
    }
    
    /**
     * Perform a monte-carlo simulation on a grid
     * @param n The size of the grid
     * @return  The number of sites open at the time of percolation
     */
    private int samplePercolationThreshold(int n) {
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
        return mean;
    }

    /**
     * @return The standard deviation of the samples
     */
    public double stddev() {
        return stddev;
    }

    /**
     * @return The lower end of the 95% confidence interval
     */
    public double confidenceLo() {
        return mean - (1.96 * stddev) / Math.sqrt(sampleCount);
    }

    /**
     * @return The higher end of the 95% confidence interval
     */
    public double confidenceHi() {
        return mean() + (1.96 * stddev()) / Math.sqrt(sampleCount);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        
        PercolationStats stats = new PercolationStats(n, T);
        
        StdOut.format("mean                    = %f\n", stats.mean());
        StdOut.format("stddev                  = %f\n", stats.stddev());
        StdOut.format("95% confidence interval = [%f, %f]\n",
            stats.confidenceLo(),
            stats.confidenceHi()
        );
    }
}
