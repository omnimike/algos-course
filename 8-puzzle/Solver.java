import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

public class Solver {

    private ArrayList<Board> solution;

    public Solver(Board initial) {
        MinPQ<SearchNode> boardPQ = new MinPQ<SearchNode>(new SearchNodeComparator());
        MinPQ<SearchNode> twinPQ = new MinPQ<SearchNode>(new SearchNodeComparator());

        boardPQ.insert(new SearchNode(initial, null, 0));
        twinPQ.insert(new SearchNode(initial.twin(), null, 0));

        boolean solved = false;
        while (!solved) {
            SearchNode boardNode = boardPQ.delMin();
            SearchNode twinNode = twinPQ.delMin();
            if (boardNode.board().isGoal()) {
                solution = buildSolution(boardNode);
                solved = true;
            } else if (twinNode.board().isGoal()) {
                solution = null;
                solved = true;
            } else {
                for (Board n : boardNode.board().neighbors()) {
                    if (boardNode.previous() == null || !boardNode.previous().board().equals(n)) {
                        boardPQ.insert(new SearchNode(n, boardNode, boardNode.moves() + 1));
                    }
                }
                for (Board n : twinNode.board().neighbors()) {
                    if (twinNode.previous() == null || !twinNode.previous().board().equals(n)) {
                        twinPQ.insert(new SearchNode(n, twinNode, twinNode.moves() + 1));
                    }
                }
            }
        }
    }

    private static class SearchNode {
        private Board board;
        private SearchNode previous;
        private int moves;

        public SearchNode(Board board, SearchNode previous, int moves) {
            this.board = board;
            this.previous = previous;
            this.moves = moves;
        }

        public Board board() {
            return board;
        }

        public SearchNode previous() {
            return previous;
        }

        public int moves() {
            return moves;
        }

        public int cost() {
            return moves + board.manhattan();
        }
    }

    private static class SearchNodeComparator implements Comparator<SearchNode> {
        public int compare(SearchNode lhs, SearchNode rhs) {
            return lhs.cost() - rhs.cost();
        }
    }

    private ArrayList<Board> buildSolution(SearchNode node) {
        ArrayList<Board> soln = new ArrayList<Board>();

        soln.add(node.board());
        while (node.previous() != null) {
            node = node.previous();
            soln.add(node.board());
        }

        Collections.reverse(soln);

        return soln;
    }

    public boolean isSolvable() {
        return solution != null;
    }

    public int moves() {
        return solution != null ? solution.size() - 1 : -1;
    }

    public Iterable<Board> solution() {
        return solution;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
