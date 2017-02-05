import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by cphetlo on 2/5/17.
 */

// How to run this
// end input with Ctrl+D
public class Permutation {
    public static void main(String args[]) {
        int k = Integer.parseInt(args[0]);
        int count = 0;
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            queue.enqueue(StdIn.readString());
            count++;
        }
        while (count - k > 0) {
            queue.dequeue();
            count--;
        }

        for (int i = 0; i < k; i++)
            StdOut.println(queue.dequeue());
    }
}
