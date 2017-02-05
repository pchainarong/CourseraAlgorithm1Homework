import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by cphetlo on 1/28/17.
 */
public class Percolation {
    private WeightedQuickUnionUF grid, auxGrid;
    private boolean[] state;
    private int gridSize;

    ///  percolation represents connectivity between sites. connected, open sites percolate to one another.
    private WeightedQuickUnionUF percolation;
    // quick union structure for tracking fullness without backwash.
    // similar to percolation above, but without bottom virtual site
    private WeightedQuickUnionUF fullness;

    /// index of virtual site that is connected to entire top row, initializes to 0.
    private int virtualTopIndex;
    /// index of virtual site that is connected to entire bottom row, initializes to (N^2)+1
    private int virtualBottomIndex;

    public Percolation(int n) {
        assert (n < 0) : "row or column size out of bound";

        if (n < 1) {
            throw new IllegalArgumentException();
        }
        gridSize = n;
        int arraySize = n * n + 2;
        state = new boolean[arraySize];
        virtualTopIndex = 0;
        virtualBottomIndex = (n * n) + 1;


        percolation = new WeightedQuickUnionUF(arraySize);
        fullness = new WeightedQuickUnionUF(arraySize);
        for (int j = 1; j <= n; j++) {
            /// connect all top row sites to virtual top site
            int i = 1;
            int topSiteIndex = siteIndex(i, j);
            percolation.union(virtualTopIndex, topSiteIndex);
            fullness.union(virtualTopIndex, topSiteIndex);

            /// connect all bottom row sites to virtual bottom site
            i = n;
            int bottomSiteIndex = siteIndex(i, j);
            percolation.union(virtualBottomIndex, bottomSiteIndex);

        }
    }


    // open site (row, col) if it is not open already
    public void open(int i, int j) {
        int siteIndex = siteIndex(i, j);
        if (!state[siteIndex]) {
            /// to open a site, change boolean value, and union with any adjacent open sites
            state[siteIndex] = true;

            // before connecting to a neighbor, first check that site is not on an edge, and is open
            if (j > 1 && isOpen(i, j - 1)) {
                int indexToLeft = siteIndex(i, j - 1);
                percolation.union(siteIndex, indexToLeft);
                fullness.union(siteIndex, indexToLeft);
            }

            if (j < gridSize && isOpen(i, j + 1)) {
                int indexToRight = siteIndex(i, j + 1);
                percolation.union(siteIndex, indexToRight);
                fullness.union(siteIndex, indexToRight);
            }

            if (i > 1 && isOpen(i - 1, j)) // site is not top edge
            {
                int indexToTop = siteIndex(i - 1, j);
                percolation.union(siteIndex, indexToTop);
                fullness.union(siteIndex, indexToTop);
            }

            if (i < gridSize && isOpen(i + 1, j)) /// site is not on bottom edge
            {
                int indexToBottom = siteIndex(i + 1, j);
                percolation.union(siteIndex, indexToBottom);
                fullness.union(siteIndex, indexToBottom);
            }
        }
    }

    ;

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {

        int siteIndex = siteIndex(row, col);
        return state[siteIndex];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {

        int siteIndex = siteIndex(row, col);
        //return (percolation.connected(virtualTopIndex,siteIndex) && isOpen[siteIndex]);
        return (fullness.connected(virtualTopIndex, siteIndex) && state[siteIndex]);
    }

    // number of open sites
    public int numberOfOpenSites() {

        int s = state.length;
        int result = 0;
        for (int i = 0; i < s; i++) {
            if (state[i]) result++;
        }
        return result;
    }

    // does the system percolate?
    public boolean percolates() {
        if (gridSize > 1) {
            return percolation.connected(virtualTopIndex, virtualBottomIndex);
        } else {
            return state[siteIndex(1, 1)];
        }
    }


    /// converts between two dimensional coordinate system and site array index.
    /// throws exceptions on invalid bounds. valid indices are 1 : N^2
    /// i is the row; j is the column
    private int siteIndex(int i, int j) {
        validateBound(i, j);
        int x = j;
        int y = i;
        return (y - 1) * gridSize + (x);
    }

    /*
 * By convention, the indices i and j are integers between 1 and N, where (1, 1) is the upper-left site:
 * Throw a java.lang.IndexOutOfBoundsException if either i or j is outside this range.
 */
    private void validateBound(int i, int j) {
        if (i > gridSize || i < 1) {
            throw new IndexOutOfBoundsException("row index i out of bounds");
        }
        if (j > gridSize || j < 1) {
            throw new IndexOutOfBoundsException("column index j out of bounds");
        }
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(1);
        StdOut.println(percolation.percolates());
        percolation.open(1, 1);
        StdOut.println(percolation.percolates());

        Percolation percolation2 = new Percolation(2);
        StdOut.println(percolation2.percolates());
        percolation2.open(1, 1);
        StdOut.println(percolation2.percolates());
        percolation2.open(2, 1);
        StdOut.println(percolation2.percolates());

        StdOut.println(percolation2.numberOfOpenSites());
    }

}
