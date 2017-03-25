import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[] openings;
    private WeightedQuickUnionUF percolationSet;
    private WeightedQuickUnionUF fullnessSet;
    private int openCount;
    private int rows;
    private int cols;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        }
        rows = n;
        cols = n;
        openCount = 0;
        
        // The +2 is for the top and bottom nodes
        int gridSize = rows * cols;

        openings = new boolean[gridSize];
        for (int i = 0; i < gridSize; i++) {
            openings[i] = false;
        }
       
        percolationSet = new WeightedQuickUnionUF(gridSize + 2);
        fullnessSet = new WeightedQuickUnionUF(gridSize + 1);
    }

    public void open(int row, int col) {
        validatePoint(row, col);
        int i = index(row, col);
        if (!openings[i]) {
            openings[i] = true;
            openCount++;
            
            int rowAbove = row - 1;
            int rowBelow = row + 1;
            int colLeft = col - 1;
            int colRight = col + 1;

            if (rowAbove >= 1 && isOpen(rowAbove, col)) {
                percolationSet.union(i, index(rowAbove, col));
                fullnessSet.union(i, index(rowAbove, col));
            }
            if (rowBelow <= rows && isOpen(rowBelow, col)) {
                percolationSet.union(i, index(rowBelow, col));
                fullnessSet.union(i, index(rowBelow, col));
            }
            if (colLeft >= 1 && isOpen(row, colLeft)) {
                percolationSet.union(i, index(row, colLeft));
                fullnessSet.union(i, index(row, colLeft));
            }
            if (colRight <= cols && isOpen(row, colRight)) {
                percolationSet.union(i, index(row, colRight));
                fullnessSet.union(i, index(row, colRight));
            }
            
            if (row == 1) {
                percolationSet.union(i, topNode());
                fullnessSet.union(i, topNode());
            }
            if (row == rows) {
                percolationSet.union(i, bottomNode());
            }
            
        }
    }

    public boolean isOpen(int row, int col) {
        validatePoint(row, col);
        return openings[index(row, col)];
    }

    public boolean isFull(int row, int col) {
        validatePoint(row, col);
        int i = index(row, col);
        return openings[i] && fullnessSet.find(i) == fullnessSet.find(topNode());
    }

    public int numberOfOpenSites() {
        return openCount;
    }

    public boolean percolates() {
        return percolationSet.find(topNode()) == percolationSet.find(bottomNode());
    }
    
    private int index(int row, int col) {
        return cols * (row - 1) + (col - 1);
    }
    
    private int topNode() {
        return rows * cols;
    }

    private int bottomNode() {
        return rows * cols + 1;
    }
    
    private void validatePoint(int row, int col) {
        if (row <= 0 || row > rows) {
            throw new IndexOutOfBoundsException("row index out of bounds");
        }
        if (col <= 0 || col > cols) {
            throw new IndexOutOfBoundsException("col index out of bounds");
        }
    }

    public static void main(String[] args) {
        
    }
}
