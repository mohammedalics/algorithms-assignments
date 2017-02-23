package deque;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/******************************************************************************
 * Name: Mohammed Ali NetID: N/A Precept: N/A
 *
 * Partner Name: N/A Partner NetID: N/A Partner Precept: N/A
 * 
 * Description: Permutation.
 ******************************************************************************/
public class Permutation {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> deque = new RandomizedQueue<String>();      
        String s;
        while (!StdIn.isEmpty()) {
            s = StdIn.readString();
            deque.enqueue(s);
        }
        

        
        for (int i = 0; i < k; i++) {
            StdOut.println(deque.dequeue());
        }
    }
}
