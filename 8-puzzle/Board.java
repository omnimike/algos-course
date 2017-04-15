import java.util.Formatter;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class Board {

    private char[] blocks;
    private int dim;
    private int hamming;
    private int manhattan;
    private boolean isGoal;

    public Board(int[][] initialBlocks) {
        dim = initialBlocks.length;
        blocks = new char[dim * dim];

        int rowIndex = 0;
        for (int[] row : initialBlocks) {
            int colIndex = 0;
            for (int block : row) {
                blocks[blockIndex(rowIndex, colIndex)] = (char) block;
                colIndex++;
            }
            rowIndex++;
        }
        cacheDistances();
    }

    private Board(char[] initialBlocks, int dimension) {
        dim = dimension;
        blocks = initialBlocks;

        cacheDistances();
    }

    private void cacheDistances() {
        hamming = 0;
        manhattan = 0;
        for (int idx = 0; idx < blocks.length; idx++) {
            char block = blocks[idx];
            if (block != idx + 1 && block != 0) {
                hamming++;
                int properRow = (block - 1) / dim;
                int properCol = (block - 1) % dim;
                int actualRow = idx / dim;
                int actualCol = idx % dim;
                manhattan += Math.abs(actualRow - properRow) + Math.abs(actualCol - properCol);
            }
        }
        isGoal = hamming == 0;
    }

    private int blockIndex(int row, int col) {
        return row * dim + col;
    }

    public int dimension() {
        return dim;
    }

    public int hamming() {
        return hamming;
    }

    public int manhattan() {
        return manhattan;
    }

    public boolean isGoal() {
        return isGoal;
    }

    public Board twin() {
        // Find the first block
        int firstIdx = 0;
        while (blocks[firstIdx] == 0) {
            firstIdx++;
        }

        // Find the second block
        int secondIdx = firstIdx + 1;
        while (blocks[secondIdx] == 0) {
            secondIdx++;
        }

        return newBoardBySwappingBlocks(firstIdx, secondIdx);
    }

    private Board newBoardBySwappingBlocks(int firstIdx, int secondIdx) {
        // Copy the blocks into a new array
        char[] newBlocks = new char[blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            newBlocks[i] = blocks[i];
        }
        // Swap the blocks
        char tempBlock = newBlocks[firstIdx];
        newBlocks[firstIdx] = newBlocks[secondIdx];
        newBlocks[secondIdx] = tempBlock;

        return new Board(newBlocks, dim);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }

        Board that = (Board) other;
        if (dim != that.dim) {
            return false;
        }
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] != that.blocks[i]) {
                return false;
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<Board>(4);
        int spaceRow = -1;
        int spaceCol = -1;
        int spaceIdx = -1;

        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] == 0) {
                spaceIdx = i;
                spaceRow = i / dim;
                spaceCol = i % dim;
                break;
            }
        }

        if (spaceRow > 0) {
            neighbors.add(newBoardBySwappingBlocks(spaceIdx, blockIndex(spaceRow - 1, spaceCol)));
        }
        if (spaceRow < dim - 1) {
            neighbors.add(newBoardBySwappingBlocks(spaceIdx, blockIndex(spaceRow + 1, spaceCol)));
        }
        if (spaceCol > 0) {
            neighbors.add(newBoardBySwappingBlocks(spaceIdx, blockIndex(spaceRow, spaceCol - 1)));
        }
        if (spaceCol < dim - 1) {
            neighbors.add(newBoardBySwappingBlocks(spaceIdx, blockIndex(spaceRow, spaceCol + 1)));
        }

        return neighbors;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);
        sb.append(dim);
        for (int row = 0; row < dim; row++) {
            sb.append('\n');
            for (int col = 0; col < dim; col++) {
                if (col != 0) {
                    sb.append(' ');
                }
                formatter.format("%2d", (int) blocks[blockIndex(row, col)]);
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        boardStats(initial);
    }

    private static void boardStats(Board board) {
        StdOut.println("board:");
        StdOut.println(board);
        StdOut.print("hamming: ");
        StdOut.println(board.hamming());
        StdOut.print("manhattan: ");
        StdOut.println(board.manhattan());
        StdOut.print("isGoal: ");
        StdOut.println(board.isGoal());
        StdOut.println("twin:");
        StdOut.println(board.twin());
        StdOut.println("neighbors:");
        for (Board n : board.neighbors()) {
            StdOut.println(n);
        }
        StdOut.println('\n');
    }
}
