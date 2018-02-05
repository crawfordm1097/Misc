import java.util.NoSuchElementException;

/**
 * Implementation of a min priority queue.
 *
 * @author Mikayla Crawford
 */
public class MinPriorityQueue<T extends Comparable<? super T>> implements PriorityQueueInterface<T> {
  private HeapInterface<T> backingHeap;
  // Do not add any more instance variables. Do not change the declaration
  // of the instance variables above.

  /**
   * Creates a priority queue.
   */
  public MinPriorityQueue() {
      backingHeap = new MinHeap<T>();
  }

  /**
   * Adds an item to the priority queue.
   *
   * @param item the item to be added
   * @throws IllegalArgumentException if the item is null
   */
  public void enqueue(T item) {
    if (item == null) throw new IllegalArgumentException("Cannot add null data to priority queue.");

    backingHeap.add(item);
  }

  /**
   * Removes and returns the first item in the priority queue.
   *
   * @throws java.util.NoSuchElementException if the priority queue is empty
   * @return the item to be dequeued
   */
  public T dequeue() {
    if (isEmpty()) throw new NoSuchElementException("Cannot remove data from an empty priority queue.");

    return backingHeap.remove(); //temp
  }

  /**
   * Returns if the priority queue is empty.
   * @return a boolean representing if the priority queue is empty
   */
  public boolean isEmpty() {
    return (backingHeap.isEmpty());
  }

  /**
   * Returns the size of the priority queue.
   * @return the size of the priority queue
   */
  public int size() {
    return (backingHeap.size());
  }

  /**
   * Clears the priority queue.
   */
  public void clear() {
    backingHeap = new MinHeap<T>();
  }

  /**
   * Used for grading purposes only.
   *
   * DO NOT USE OR EDIT THIS METHOD!
   *
   * @return the backing heap
   */
  public HeapInterface<T> getBackingHeap() {
      // DO NOT CHANGE THIS METHOD!
      return backingHeap;
  }
}
