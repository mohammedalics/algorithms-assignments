package deque;

import java.util.Iterator;
import java.util.NoSuchElementException;

/******************************************************************************
 * Name: Mohammed Ali NetID: N/A Precept: N/A
 *
 * Partner Name: N/A Partner NetID: N/A Partner Precept: N/A
 * 
 * Description: Deque.
 ******************************************************************************/

public class Deque<Item> implements Iterable<Item> {

    private Node<Item> first;
    private Node<Item> last;
    private int size;

    private static final class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> previous;

        public Node(Item item, Node<Item> next, Node<Item> previous) {
            this.item = item;
            this.next = next;
            this.previous = previous;
        }
    }

    private static final class NodeIterator<Item> implements Iterator<Item> {

        private Node<Item> next;

        public NodeIterator(final Deque<Item> deque) {
            if (deque.first != null) {
                this.next = deque.first;
            }
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            final Node<Item> oldNext = this.next;
            this.next = this.next.next;
            return oldNext.item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    /**
     * construct an empty deque.
     */
    public Deque() {
        this.first = null;
        this.last = null;
    }

    /**
     * Check if the deque is empty
     * 
     * @return True if empty, False otherwise.
     */
    public boolean isEmpty() {
        return size == 0;

    }

    /**
     * 
     * @return The number of items on the deque
     */
    public int size() {
        return size;

    }

    /**
     * Add the item to the front.
     * 
     * @param item
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (first == null) {
            Node<Item> n = new Node<Item>(item, null, null);
            first = n;
            last = n;
        }
        else {
            Node<Item> n = new Node<Item>(item, first, null);
            first.previous = n;
            first = n;
        }
        size++;
    }

    /**
     * Add the item to the end.
     * 
     * @param item
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (last == null) {
            Node<Item> n = new Node<Item>(item, null, null);
            first = n;
            last = n;
        } 
        else {
            Node<Item> n = new Node<Item>(item, null, last);
            last.next = n;
            last = n;
        }
        size++;
    }

    /**
     * remove and return the item from the front.
     * 
     * @return The item from the front
     */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Node<Item> oldFirst = first;
        if (size == 1) {
            first = null;
            last = null;
        } 
        else {
            first.next.previous = null;
            first = first.next;
        }
        size--;
        return oldFirst.item;
    }

    /**
     * Remove and return the item from the end
     * 
     * @return The item from the end
     */
    public Item removeLast() {

        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Node<Item> oldLast = last;
        if (size == 1) {
            first = null;
            last = null;
        } 
        else {
            last.previous.next = null;
            last = last.previous;
        }
        size--;
        return oldLast.item;
    }

    /**
     * Return an iterator over items in order from front to end
     */
    public Iterator<Item> iterator() {
        return new NodeIterator<Item>(this);

    }

    /**
     * Unit test (optional)
     * 
     * @param args
     */
    public static void main(String[] args) {

        Deque<String> d = new Deque<String>();
        d.addFirst("m");
        d.addLast("d");
        d.addFirst("o");
        d.addLast("s");
        d.removeFirst();
        d.removeLast();
        Iterator<String> it = d.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}
