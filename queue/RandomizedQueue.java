import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private final int STARTING_SIZE = 2;
    private final int COMPACT_RATIO = 3/4;
    private Object[] items = new Object[STARTING_SIZE];
    private int itemCount = 0;
    
    public RandomizedQueue() {
        items = new Object[STARTING_SIZE];
    }
    
    public boolean isEmpty() {
        return itemCount == 0;
    }
    
    public int size() {
        return itemCount;
    }
    
    public void enqueue(Item item) {
        if (itemCount == items.length) {
            expand();
        }
    }
    
    public Item dequeue() {
        return null;
    }
    
    public Item sample() {
        return null;
    }
    
    public Iterator<Item> iterator() {
        return null;
    }
    
    private void expand() {
        Object[] newItems = new Object[items.length * 2];
        for (int i = 0; i < items.length; i++) {
            newItems[i] = items[i];
        }
        items = newItems;
    }
    
    private void contract() {
        
    }
    
    public static void main(String[] args) {
        
    }
}
