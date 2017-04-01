import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int STARTING_SIZE = 2;
    private Item[] items;
    private int count = 0;

    public RandomizedQueue() {
        items = makeItemArr(STARTING_SIZE);
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (items.length == count) {
            expand();
        }
        items[count++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int idx = randomIdx();
        Item item = items[idx];
        // Fill the hole with the last element in the array
        items[idx] = items[count - 1];
        items[count - 1] = null;
        count--;
        if (count < items.length / 4) {
            contract();
        }
        return item;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return items[randomIdx()];
    }

    private int randomIdx() {
        return StdRandom.uniform(0, count);
    }

    public Iterator<Item> iterator() {
        Item[] iterArr = makeItemArr(size());
        move(items, iterArr);
        StdRandom.shuffle(iterArr);
        return new RandomizedQueueIterator<Item>(iterArr);
    }

    private void expand() {
        Item[] newItems = makeItemArr(items.length * 2);
        move(items, newItems);
        items = newItems;
    }

    private void contract() {
        Item[] newItems = makeItemArr(items.length / 2);
        move(items, newItems);
        items = newItems;
    }

    private void move(Object[] srcArr, Object[] destArr) {
        int limit = Math.min(srcArr.length, destArr.length);
        for (int i = 0; i < limit; ++i) {
            destArr[i] = srcArr[i];
        }
    }

    private Item[] makeItemArr(int size) {
        return (Item[]) new Object[size];
    }

    private static class RandomizedQueueIterator<Item> implements Iterator<Item> {

        private Item[] items;
        private int idx = 0;

        public RandomizedQueueIterator(Item[] items) {
            this.items = items;
        }

        public boolean hasNext() {
            return idx < items.length;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return items[idx++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    public static void main(String[] args) {

    }
}
