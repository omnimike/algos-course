import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[] openings;
    private WeightedQuickUnionUF quickFinder;
    private int openCount;
    private int rows;
    private int cols;

    public Percolation(int n) {
        rows = n;
        cols = n;
        openCount = 0;
        
        // The +2 is for the top and bottom nodes
        int totalSize = rows * cols + 2;

        openings = new boolean[totalSize];
        for (int i = 0; i < totalSize; i++) {
            openings[i] = false;
        }
       
        quickFinder = new WeightedQuickUnionUF(totalSize);

        // Join the top and bottom nodes to the top and bottom rows
        openings[topNode()] = true;
        openings[bottomNode()] = true;
        for (int i = 0; i < cols; i++) {
            quickFinder.union(index(0, i), topNode());
            quickFinder.union(index(rows-1, i), bottomNode());
        }
    }

    public void open(int row, int col) {
        int i = index(row, col);
        if (!openings[i]) {
            openings[i] = true;
            openCount++;
            
            int rowAbove = row - 1;
            int rowBelow = row + 1;
            int colLeft = col - 1;
            int colRight = col + 1;

            if (rowAbove >= 0) {
                if (isOpen(rowAbove, col)) {
                    quickFinder.union(index(row, col), index(rowAbove, col));
                }
            } else if (rowBelow < rows) {
                if (isOpen(rowBelow, col)) {
                    quickFinder.union(index(row, col), index(rowBelow, col));
                }
            }
            if (colLeft >= 0) {
                if (isOpen(row, colLeft)) {
                    quickFinder.union(index(row, col), index(row, colLeft));
                }
            } else if (colRight < cols) {
                if (isOpen(row, colRight)) {
                    quickFinder.union(index(row, col), index(row, colRight));
                }
            }
        }
    }

    public boolean isOpen(int row, int col) {
        return openings[index(row, col)];
    }

    public boolean isFull(int row, int col) {
        return openings[index(row, col)];
    }

    public int numberOfOpenSites() {
        return openCount;
    }

    public boolean percolates() {
        return quickFinder.find(topNode()) == quickFinder.find(bottomNode());
    }
    
    private int index(int row, int col) {
        return cols * row + col;
    }
    
    private int topNode() {
        return rows * cols;
    }

    private int bottomNode() {
        return rows * cols + 1;
    }

    public static void main(String[] args) {
        Percolation per = new Percolation(10);
        StdOut.println("This is a test");
    }
}