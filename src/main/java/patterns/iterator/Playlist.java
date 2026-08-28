package patterns.iterator;

import java.util.Iterator;

/**
 * An ordered collection of items you can walk over.
 *
 * <p>
 * Storage is already done: items live in a plain array that grows as needed.
 * Your job is the <em>traversal</em> — hand out {@link Iterator}s that let a
 * client walk the items without ever seeing this array.
 *
 * @param <T> the type of item held in the playlist
 */
public class Playlist<T> implements Iterable<T> {

    // ---- Storage (provided — do not change) --------------------------------

    private Object[] items = new Object[4];
    private int size = 0;

    /** Appends an item to the end of the playlist. */
    public void add(T item) {
        if (size == items.length) {
            Object[] bigger = new Object[items.length * 2];
            System.arraycopy(items, 0, bigger, 0, size);
            items = bigger;
        }
        items[size++] = item;
    }

    /** How many items the playlist currently holds. */
    public int size() {
        return size;
    }

    /** Returns the item at {@code index} (0-based). */
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index " + index + ", size " + size);
        }
        return (T) items[index];
    }

    // ---- Traversal (your job) ----------------------------------------------

    /**
     * Returns a cursor that yields the items in insertion order (index 0 first).
     *
     * <p>
     * Write the cursor yourself: an inner class implementing
     * {@code Iterator<T>} with its own position field. Each call must return a
     * fresh, independent cursor.
     */
    @Override
    public Iterator<T> iterator() {
        // TODO: return a new forward iterator over items[0..size).
        // - hasNext(): are there items left?
        // - next(): return the current item and advance; throw
        // NoSuchElementException when exhausted.

        return new Iterator<T>() {

            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new java.util.NoSuchElementException();
                }
                return get(i++);

            }

        };
    }

    /**
     * Returns an {@link Iterable} view that walks the same items from last to
     * first. Same storage, opposite order.
     */
    public Iterable<T> reversed() {
        return new Iterable<T>() {

            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {

                    private int i = size - 1;

                    @Override
                    public boolean hasNext() {
                        return i >= 0;
                    }

                    @Override
                    public T next() {
                        if (!hasNext()) {
                            throw new java.util.NoSuchElementException();
                        }
                        return get(i--);
                    }

                };
            }

        };
    }

    // Note: NoSuchElementException is imported and ready for next() to throw.
}
