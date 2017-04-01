import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {

    private Node<Item> head;
    private Node<Item> tail;
    private int size;

    public Deque() {
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        Node<Item> node = new Node<Item>(item);

        if (head != null) {
            node.next = head;
            head.previous = node;
        }
        head = node;
        size++;

        if (tail == null) {
            tail = node;
        }
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        Node<Item> node = new Node<Item>(item);

        if (tail != null) {
            node.previous = tail;
            tail.next = node;
        }
        tail = node;
        size++;

        if (head == null) {
            head = node;
        }
    }

    public Item removeFirst() {
        if (head == null) {
            throw new NoSuchElementException();
        } else {
            Node<Item> oldHead = head;
            head = oldHead.next;
            if (head != null) {
                head.previous = null;
            }
            size--;
            if (tail == oldHead) {
                tail = null;
            }
            return oldHead.item;
        }
    }

    public Item removeLast() {
        if (tail == null) {
            throw new NoSuchElementException();
        } else {
            Node<Item> oldTail = tail;
            tail = oldTail.previous;
            if (tail != null) {
                tail.next = null;
            }
            size--;
            if (head == oldTail) {
                head = null;
            }
            return oldTail.item;
        }
    }

    public Iterator<Item> iterator() {
        return new DequeIterator<Item>(head);
    }

    public static void main(String[] args) {
        Deque<String> dequeue = new Deque<String>();
        dequeue.addLast("first");
        dequeue.addLast("second");
        dequeue.addLast("third");
        dequeue.addLast("fourth");
        dequeue.addLast("fifth");
        printDeque(dequeue);
        dequeue.removeFirst();
        dequeue.removeFirst();
        dequeue.removeFirst();
        printDeque(dequeue);
    }

    private static void printDeque(Deque<String> dequeue) {
        StdOut.print("[");
        for (String item : dequeue) {
            StdOut.print("\"");
            StdOut.print(item);
            StdOut.print("\", ");
        }
        StdOut.println("]");
    }

    private static class Node<Item> {
        private Item item;
        private Node<Item> previous;
        private Node<Item> next;

        public Node(Item item) {
            this.item = item;
        }

        public Item item() {
            return item;
        }

        public Node<Item> previous() {
            return previous;
        }

        public Node<Item> next() {
            return next;
        }
    }

    private static class DequeIterator<Item> implements Iterator<Item> {

        private Node<Item> node;

        public DequeIterator(Node<Item> head) {
            node = head;
        }

        public boolean hasNext() {
            return node != null;
        }

        public Item next() {
            if (node != null) {
                Item item = node.item;
                node = node.next;
                return item;
            } else {
                throw new NoSuchElementException();
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
