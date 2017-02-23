package percolation;

/******************************************************************************
 *  Name:    Mohammed Ali
 *  NetID:   N/A
 *  Precept: N/A
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description:  Modeling Percolation using an N-by-N grid and Union-Find data 
 *                structures to determine the threshold. 
 ******************************************************************************/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int trials;
    private double[] result;

   /**
    * Perform trials independent experiments on an n-by-n grid
    * @param n
    * @param trials
    */
    public PercolationStats(int n, int trials) { 
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.trials = trials;
        result = new double[this.trials];
        for (int i = 0; i < this.trials; i++) {
            Percolation p = new Percolation(n);
            do {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                p.open(row, col);
            } while (!p.percolates());
            result[i] = p.numberOfOpenSites() * 1.0d / (n * n * 1.0d);
        }
    }

    /**
     * Sample mean of percolation threshold
     * @return
     */
    public double mean() { 
        return StdStats.mean(result);

    }

    /**
     * Sample standard deviation of percolation threshold
     * @return
     */
    public double stddev() { 
        return StdStats.stddev(result);
    }

    /**
     * low endpoint of 95% confidence interval
     * @return
     */
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(trials));
    }

    /**
     * high endpoint of 95% confidence interval
     * @return
     */
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(trials));
    }

    
    /**
     * test client
     * @param args
     */
    public static void main(String[] args) { 
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats pStat = new PercolationStats(n, trials);
        System.out.println("mean                    = " + pStat.mean());
        System.out.println("stddev                  = " + pStat.stddev());
        System.out.println("95% confidence interval = [" + pStat.confidenceLo() + ", " + pStat.confidenceHi() + "]");
    }

}
