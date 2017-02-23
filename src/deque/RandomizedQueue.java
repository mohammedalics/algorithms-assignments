package deque;


import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

/******************************************************************************
 * Name: Mohammed Ali NetID: N/A Precept: N/A
 *
 * Partner Name: N/A Partner NetID: N/A Partner Precept: N/A
 * 
 * Description: RandomizedQueue.
 ******************************************************************************/
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] elements;
    private int counter;

    private final class NodeIterator implements Iterator<Item> {
        private Item[] list;
        private int currentElement;

        public NodeIterator() {
            currentElement = -1;
            list = (Item[]) new Object[counter];
            for (int i = 0; i < counter; i++) {
                list[i] = elements[i];
            }
            StdRandom.shuffle(list);
        }

        @Override
        public boolean hasNext() {
            int nextElement = currentElement + 1;
            return nextElement < list.length;
        }

        @Override
        public Item next() {

            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            currentElement++;
            return list[currentElement];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    /**
     * construct an empty randomized queue
     */
    public RandomizedQueue() {
        this.counter = 0;
        elements = (Item[]) new Object[1];
    }

    /**
     * is the queue empty?
     * 
     * @return
     */
    public boolean isEmpty() {
        return counter == 0;

    }

    /**
     * return the number of items on the queue
     * 
     * @return
     */
    public int size() {
        return counter;

    }

    /**
     * add the item
     * 
     * @param item
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        elements[counter++] = item;
        if (counter == elements.length) {
            resize(2 * elements.length);
        }

    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < counter; i++) {
            copy[i] = elements[i];
        }
        elements = copy;
    }

    private void checkEmpty() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
    }

    /**
     * remove and return a random item
     * 
     * @return
     */
    public Item dequeue() {
        checkEmpty();
        int index = StdRandom.uniform(counter);
        Item value = elements[index];

        counter--;
        if (index < counter) {
            elements[index] = elements[counter];
        }
        elements[counter] = null;

        if (counter > 0 && counter == elements.length / 4) {
            resize(elements.length / 2);
        }

        return value;
    }

    /**
     * return (but do not remove) a random item
     * 
     * @return
     */
    public Item sample() {
        checkEmpty();
        int index = StdRandom.uniform(counter);
        Item value = elements[index];
        return value;

    }

    /**
     * return an independent iterator over items in random order
     */
    public Iterator<Item> iterator() {
        return new NodeIterator();

    }

    /**
     * unit testing (optional)
     * 
     * @param args
     */
    public static void main(String[] args) {
        RandomizedQueue<Integer> r = new RandomizedQueue<Integer>();
        r.enqueue(5);
        r.enqueue(30);
        System.out.println(r.dequeue());
        System.out.println(r.dequeue());
        System.out.println(r.dequeue());



    }
}