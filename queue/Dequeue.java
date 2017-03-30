import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Dequeue<Item> implements Iterable<Item> {
    
    private Node<Item> head;
    private Node<Item> tail;
    private int size;
    
    public Dequeue() {
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
        if (tail == null) {
            throw new NoSuchElementException();
        } else {
            Node<Item> oldHead = head;
            head = oldHead.next;
            head.previous = null;
            size--;
            return oldHead.item;
        }
    }
    
    public Item removeLast() {
        if (tail == null) {
            throw new NoSuchElementException();
        } else {
            Node<Item> oldTail = tail;
            tail = oldTail.previous;
            tail.next = null;
            size--;
            return oldTail.item;
        }
    }
    
    public Iterator<Item> iterator() {
        return new DequeueIterator<Item>(head);
    }
    
    public static void main(String[] args) {
        Dequeue<String> dequeue = new Dequeue<String>();
        dequeue.addLast("first");
        dequeue.addLast("second");
        dequeue.addLast("third");
        dequeue.addLast("fourth");
        dequeue.addLast("fifth");
        printDequeue(dequeue);
        dequeue.removeFirst();
        dequeue.removeFirst();
        dequeue.removeFirst();
        printDequeue(dequeue);
    }
    
    private static void printDequeue(Dequeue<String> dequeue) {
        StdOut.print("[");
        for (String item : dequeue) {
            StdOut.print("\"");
            StdOut.print(item);
            StdOut.print("\", ");
        }
        StdOut.println("]");
    }
    
    private static class Node<Item> {
        Item item;
        Node<Item> previous;
        Node<Item> next;
        
        public Node(Item item) {
            this.item = item;
        }
    }
    
    private static class DequeueIterator<Item> implements Iterator<Item> {
        
        private Node<Item> node;
        
        public DequeueIterator(Node<Item> head) {
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
