import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by cphetlo on 2/5/17.
 */
public class Deque<Item> implements Iterable<Item> {

    private int N; // number of elements in Deque
    private Node<Item> first; // first element in Deque
    private Node<Item> last; // last element in Deque

    // a helper double linked list data type
    private class Node<Item> {
        Item item;
        Node<Item> previous;
        Node<Item> next;
    }

    // construct an empty deque
    public Deque() {

        N = 0;
        first = null;
        last = null;

    }

    // is the deque empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the deque
    public int size() {
        return N;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        Node<Item> newFirst = new Node<Item>();
        newFirst.item = item;
        newFirst.previous = null;

        if (isEmpty()) {
            newFirst.next = null;
            last = newFirst;
        } else {
            newFirst.next = first;
            first.previous = newFirst;
        }

        first = newFirst;
        N++;
    }


    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        Node<Item> newLast = new Node<Item>();
        newLast.item = item;
        newLast.next = null;

        if (isEmpty()) {
            newLast.previous = null;
            first = newLast;
        } else {
            newLast.previous = last;
            last.next = newLast;
        }
        last = newLast;
        N++;
    }


    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = first.item;
        if (N==1){
            first =null;
            last = null;
            N--;
             return item;
        }

        first = first.next;
        first.previous = null;
        N--;
        if (isEmpty()) {
            last = null;
        } else {
            first.previous = null;
        }

        return item;
    }


    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
        Item item = last.item;
        last = last.previous;
        N--;
        if (isEmpty()) {
            first = null;
        } else {
            last.next = null;
        }
        return item;
    }

    // return an iterator over items in order from front to end

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator<Item>(first);
    }

    private class DequeIterator<Item> implements Iterator<Item> {
        Node<Item> current;

        DequeIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this)
            s.append(item + " ");
        return s.toString();
    }

    // unit testing (optional)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        deque.addFirst("2");
        StdOut.println(deque.toString());
        deque.removeFirst();
        StdOut.println(deque.toString());
        deque.addLast("3");
        deque.removeLast();
        StdOut.println(deque.toString());
        deque.addLast("4");
        StdOut.println(deque.toString());
        deque.addFirst("1");
        StdOut.println(deque.toString());
        deque.removeFirst();
        deque.removeLast();
        StdOut.println(deque.toString());
        deque.addFirst("1");
        StdOut.println(deque.toString());
        deque.removeLast();


    }

}
