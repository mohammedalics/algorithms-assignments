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

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] openList;
    private int numberOfOpen;
    private WeightedQuickUnionUF uf;
    private int n;

    /**
     * create n-by-n grid, with all sites blocked
     * @param n
     */
    public Percolation(int n) { 
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.n = n;
        this.openList = new boolean[n][n];
        uf = new WeightedQuickUnionUF((n * n) + 1);
        numberOfOpen = 0;
    }

    /**
     * open site (row, col) if it is not open already
     * @param row
     * @param col
     */
    public void open(int row, int col) { 
        if (!isOpen(row, col)) {
            openList[row - 1][col - 1] = true;
            numberOfOpen++;
            if (row == 1) {
                uf.union(0, getOneDimIndex(row, col));
            }
            if (row - 1 > 0 && isOpen(row - 1, col)) {
                uf.union(getOneDimIndex(row, col), getOneDimIndex(row - 1, col));
            }
            if (col + 1 <= n && isOpen(row, col + 1)) {
                uf.union(getOneDimIndex(row, col), getOneDimIndex(row, col + 1));
            }
            if (row + 1 <= n && isOpen(row + 1, col)) {
                uf.union(getOneDimIndex(row, col), getOneDimIndex(row + 1, col));
            }
            if (col - 1 > 0 && isOpen(row, col - 1)) {
                uf.union(getOneDimIndex(row, col), getOneDimIndex(row, col - 1));
            }

        }
    }

    /**
     * is site (row, col) open?
     * @param row
     * @param col
     * @return
     */
    public boolean isOpen(int row, int col) { 
        return openList[row - 1][col - 1];
    }
    
    /**
     * is site (row, col) full?
     * @param row
     * @param col
     * @return
     */
    public boolean isFull(int row, int col) { 
        if (row <= 0 || row > n || col <= 0 || col > n) {
            throw new java.lang.IndexOutOfBoundsException();

        }
        return uf.connected(0, getOneDimIndex(row, col));

    }

    /**
     * number of open sites
     * @return
     */
    public int numberOfOpenSites() { 
        return numberOfOpen;

    }

    /**
     * does the system percolate?
     * @return
     */
    public boolean percolates() { 
        for (int i = getOneDimIndex(n, 1); i <= n * n; i++) {
            if (isOpen(n, ((i - 1) % n) + 1) && uf.connected(0, i)) {
                return true;
            }
        }
        return false;

    }

    /**
     * test client (optional)
     * @param args
     */
    public static void main(String[] args) { 
        In in = new In(args[0]);
        int n = in.readInt();
        Percolation perlocation = new Percolation(n);
        int[] whitelist = in.readAllInts();
        for (int i = 0; i < whitelist.length; i = i + 2) {
            perlocation.open(whitelist[i], whitelist[i + 1]);
        }
        System.out.println(perlocation.percolates());

    }

    private int getOneDimIndex(int row, int col) {
        int r = row - 1;
        int c = col - 1;
        return (r * n) + c + 1;
  //      return (row - 1) * n + (col - 1) + 1;
    }
}
