import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by cphetlo on 2/5/17.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] array; // array of items in Deque
    private int N; // count of items in Deque


    // construct an empty deque
    public RandomizedQueue() {

        N = 0;
        array = (Item[]) new Object[2];

    }


    // is the deque empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the deque
    public int size() {
        return N;
    }


    // resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= N;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            temp[i] = array[i];
        }
        array = temp;
    }


    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (N == array.length) {
            resize(array.length * 2);
        }
        array[N++] = item;
    }

    // delete and return a random item
    // we do not need to remove randomIndex from array only replace value with the last array
    // this is more like unordered array, iteration method doesn't return in sequence either
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int randomIndex = StdRandom.uniform(N);
        Item item = array[randomIndex];

        array[randomIndex] = array[N - 1];
        array[N - 1] = null;
        N--;
        if (N > 0 && N == array.length / 4) {
            resize(array.length / 2);
        }
        return item;
    }


    // return (but do not delete) a random item
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int randomIndex = StdRandom.uniform(N);
        Item item = array[randomIndex];
        return item;
    }


    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this)
            s.append(item + " ");
        return s.toString();
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedArrayIterator();
    }

    private class RandomizedArrayIterator implements Iterator<Item> {

        private int copiedN;
        private Item[] copiedArray;
        private int nextIndex = 0;

        public RandomizedArrayIterator() {
            copiedArray = (Item[]) new Object[N];
            for (int i = 0; i < N; i++) {
                copiedArray[i] = array[i];
            }
            copiedN = N;
        }


        public boolean hasNext() {
            return copiedN > 0;
        }

        //straight has next
//        public boolean hasNext()     {
//            return nextIndex < N;
//        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            int randomIndex = StdRandom.uniform(copiedN);
            Item item = copiedArray[randomIndex];
            copiedArray[randomIndex] = copiedArray[copiedN - 1];
            copiedArray[copiedN - 1] = null;
            copiedN--;
            return item;
        }


        //straight
//        public Item next() {
//            if (!hasNext()) throw new NoSuchElementException();
//
//            return copiedArray[nextIndex++];
//        }

    }

    // unit testing (optional)
    public static void main(String[] args) {

        RandomizedQueue<String> randQue = new RandomizedQueue<String>();
        randQue.enqueue("1");
        randQue.enqueue("2");
        randQue.enqueue("3");
        randQue.enqueue("4");


        randQue.enqueue("5");
        randQue.enqueue("6");
        randQue.enqueue("7");
        randQue.enqueue("8");
        StdOut.println(randQue.toString());

        StdOut.println("size:" + randQue.size());
        for (int i = 0; i < 10; i++) {
            StdOut.print(randQue.sample() + " ");
        }

        StdOut.println();
        StdOut.println("deq item:" + randQue.dequeue());
        StdOut.println(randQue.toString());
        StdOut.println("deq item:" + randQue.dequeue());

        StdOut.println(randQue.toString());
    }
}
