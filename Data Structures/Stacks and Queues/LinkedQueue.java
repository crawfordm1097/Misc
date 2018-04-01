import java.util.NoSuchElementException;

/**
 * Implementation of a linked queue.
 *
 * @author Mikayla Crawford
 */
public class LinkedQueue<T> implements QueueInterface<T> {
  private LinkedNode<T> head;
  private LinkedNode<T> tail;
  private int size;

  /**
   * Dequeue from the front of the queue.
   *
   * O(1) time.
   *
   * @return the data from the front of the queue
   * @throws java.util.NoSuchElementException if the queue is empty
   */
  public T dequeue() {
    if (isEmpty()) throw new NoSuchElementException("Cannot remove data from empty queue.");
    LinkedNode<T> oldHead = head; //Old head temp

    if (size == 1) { //If 1 element in queue
      tail = null; //Updates tail pointer
      head = null; //Updates head pointer
    } else { //If more than 1 element in queue
      head = oldHead.getNext(); //Updates head pointer
      oldHead.setNext(null); //Removes oldHeads next pointer
    }

    size--; //Decreases size

    return oldHead.getData();
  }

  /**
   * Add the given data the the queue.
   *
   * O(1) time.
   *
   * @param data the data to add
   * @throws IllegalArgumentException if data is null
   */
  public void enqueue(T data) {
    if (data == null) throw new IllegalArgumentException("Cannot add null data to queue.");
    LinkedNode<T> newNode = new LinkedNode<T>(data, null); //The node to add

    if (isEmpty()) { //If queue is empty
      head = newNode; //Updates head pointer
    } else { //If queue is not empty
      tail.setNext(newNode); //Updates current tails next pointer
    }

    tail = newNode; //Updates tail pointer
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
   * Returns the head of this queue. Used for testing.
   *
   * @return the head node
   */
  public LinkedNode<T> getHead() {
      return head;
  }

  /**
   * Returns the tail of this queue. Used for testing.
   *
   * @return the tail node
   */
  public LinkedNode<T> getTail() {
      return tail;
  }
}
