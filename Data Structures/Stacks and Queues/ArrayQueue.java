import java.util.NoSuchElementException;

/**
 * Implementation of an circular array-backed queue.
 *
 * @author Mikayla Crawford
 */
public class ArrayQueue<T> implements QueueInterface<T> {
  private T[] backingArray;
  private int front;
  private int back;
  private int size;

  /**
   * Constructs a new ArrayQueue.
   */
  public ArrayQueue() {
      backingArray = (T[]) new Object[INITIAL_CAPACITY];
      front = 0;
      back = 0;
      size = 0;
  }

  /**
   * Dequeue from the front of the queue. The backing array is not shrunk.
   *
   * @see QueueInterface#dequeue():
   * This method should be implemented in O(1) time.
   *
   * @return the data from the front of the queue
   * @throws java.util.NoSuchElementException if the queue is empty
   */
  public T dequeue() {
    if (isEmpty()) throw new NoSuchElementException("Cannot remove data from empty queue.");
    T oldFront = backingArray[front]; //The original front

    backingArray[front] = null; //Resets the value
    size--; //Decreases size
    front = (front == size ? 0 : (size == 0 ? front : front + 1)); //Updates front

    return oldFront;
  }

  /**
   * Add the given data to the queue. If the queue is full, the backing array
   * is regrown to 1.5 its size. If this occurs, the front is reset to 0.
   *
   * @see QueueInterface#enqueue(T):
   * This method should be implemented in amortized O(1) time.
   *
   * @param data the data to add
   * @throws IllegalArgumentException if data is null
   */
  public void enqueue(T data) {
    if (data == null) throw new IllegalArgumentException("Cannot add null data to queue.");

    if (size == backingArray.length) { //If need to regrow array
      T[] newBack = (T[]) new Object[(int)(size*1.5)];
      int offset; //Keeps track of offset created by circular array

      //Loops through the array
      for (int i = 0; i < size; i++) {
        //Copies elements over and realigns
        offset = front + i;
        newBack[i] = (offset < size) ? backingArray[offset] : backingArray[offset - size];
      }

      newBack[size] = data; //Adds newest data
      backingArray = newBack; //Updates backing array
      front = 0; //Resets front
      back = size; //Updates back
    } else { //Add element normally
      if (back == backingArray.length - 1) back = -1; //Move back around
      if (size != 0) back++; //Increases back
      backingArray[back] = data; //Adds newest data
    }

    size++; //Increases size
  }

  /**
   * Return true if this queue contains no elements, false otherwise.
   *
   * O(1) time.
   *
   * @return true if the queue is empty; false otherwise
   */
  public boolean isEmpty() {
    return (size == 0);
  }

  /**
   * Return the size of the queue.
   *
   * O(1) time.
   *
   * @return number of items in the queue
   */
  public int size() {
    return size;
  }

  /**
   * Returns the backing array of this queue. Used for testing.
   *
   * @return the backing array
   */
  public Object[] getBackingArray() {
      return backingArray;
  }
}
