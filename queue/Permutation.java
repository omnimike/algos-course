import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class Permutation {
   public static void main(String[] args) {
        if (args.length < 1) {
            StdOut.println("Usage: \"Permutation k\" where k is the number of strings to sample");
        }
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String next = StdIn.readString();
            queue.enqueue(next);
        }
        for (int i = 0; i < k; i++) {
            if (queue.isEmpty()) {
                StdOut.println("k too large!");
            }
            StdOut.println(queue.dequeue());
        }
   }
}
