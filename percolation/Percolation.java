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

        // Join the top and bottom nodes to the top and bottom rows
        for (int i = 1; i <= cols; i++) {
            percolationSet.union(index(1, i), topNode());
            percolationSet.union(index(rows, i), bottomNode());
            fullnessSet.union(index(1, i), topNode());
        }
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
                percolationSet.union(index(row, col), index(rowAbove, col));
                fullnessSet.union(index(row, col), index(rowAbove, col));
            }
            if (rowBelow <= rows && isOpen(rowBelow, col)) {
                percolationSet.union(index(row, col), index(rowBelow, col));
                fullnessSet.union(index(row, col), index(rowBelow, col));
            }
            if (colLeft >= 1 && isOpen(row, colLeft)) {
                percolationSet.union(index(row, col), index(row, colLeft));
                fullnessSet.union(index(row, col), index(row, colLeft));
            }
            if (colRight <= cols && isOpen(row, colRight)) {
                percolationSet.union(index(row, col), index(row, colRight));
                fullnessSet.union(index(row, col), index(row, colRight));
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
