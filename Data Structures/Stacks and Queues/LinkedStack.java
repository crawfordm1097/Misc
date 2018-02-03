import java.util.NoSuchElementException;

/**
 * Implementation of a linked stack.
 *
 * @author Mikayla Crawford
 */
public class LinkedStack<T> implements StackInterface<T> {
  // Do not add new instance variables.
  private LinkedNode<T> head;
  private int size;

  /**
   * Return true if this stack contains no elements, false otherwise.
   *
   * This method should be implemented in O(1) time.
   *
   * @return true if the stack is empty; false otherwise
   */
  public boolean isEmpty() {
    return (size == 0);
  }

  /**
   * Pop from the stack.
   *
   * Removes and returns the top-most element on the stack.
   * This method should be implemented in O(1) time.
   *
   * @return the data from the front of the stack
   * @throws java.util.NoSuchElementException if the stack is empty
   */
  public T pop() {
    if (isEmpty()) throw new NoSuchElementException("Cannot remove data from an empty stack.");
    LinkedNode<T> oldHead = head; //Old head node

    if (size == 1) { //If stack has 1 element
      head = null; //Removes head
    } else { //If stack has more than 1 element
      head = oldHead.getNext(); //Updates head
      oldHead.setNext(null); //Removes oldHeads pointer
    }

    size--; //Decreases size
    return oldHead.getData();
  }

  /**
   * Push the given data onto the stack.
   *
   * The given element becomes the top-most element of the stack.
   * This method should be implemented in (if array-backed, amortized) O(1)
   * time.
   *
   * @param data the data to add
   * @throws IllegalArgumentException if data is null
   */
  public void push(T data) {
    if (data == null) throw new IllegalArgumentException("Cannot add null data to stack.");

    if (isEmpty()) { //If stack is empty
      head = new LinkedNode<T>(data, null); //Adds head
    } else { //If stack is not empty
      LinkedNode<T> newHead = new LinkedNode<T>(data, head); //Creates new head node
      head = newHead; //Updates head variable
    }

    size++; //Increases size
  }

  /**
   * Return the size of the stack.
   *
   * This method should be implemented in O(1) time.
   *
   * @return number of items in the stack
   */
  public int size() {
    return size;
  }

  /**
   * Returns the head of this stack.
   * Normally, you would not do this, but included for testing.
   *
   * @return the head node
   */
  public LinkedNode<T> getHead() {
      // DO NOT MODIFY!
      return head;
  }
}
