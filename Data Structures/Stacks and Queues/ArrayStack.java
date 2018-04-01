import java.util.NoSuchElementException;

/**
 * Implementation of an array-backed stack.
 *
 * @author Mikayla Crawford
 */
public class ArrayStack<T> implements StackInterface<T> {
  private T[] backingArray;
  private int size;

  /**
   * Constructs a new ArrayStack.
   */
  public ArrayStack() {
      backingArray = (T[]) new Object[INITIAL_CAPACITY];
      size = 0;
  }

  /**
   * Return true if this stack contains no elements, false otherwise.
   *
   * O(1) time.
   *
   * @return true if the stack is empty; false otherwise
   */
  public boolean isEmpty() {
    return (size == 0);
  }

  /**
   * Pop from the stack. Does not shrink the backing array.
   *
   * Amortized O(1) time.
   *
   * @see StackInterface#pop():
   * @return the data from the front of the stack
   * @throws java.util.NoSuchElementException if the stack is empty
   */
  public T pop() {
    if (isEmpty()) throw new NoSuchElementException("Cannot remove data from an empty stack.");

    T oldEle = backingArray[size- 1]; //Temp holder for element
    backingArray[size - 1] = null; //Removes the element
    size--; //Decreases size

    return oldEle;
  }

  /**
   * Push the given data onto the stack. If the backing array is full, it is
   * regrown to 1.5 its size.
   *
   * @see StackInterface#push(T data):
   * @param data the data to add
   * @throws IllegalArgumentException if data is null
   */
  public void push(T data) {
    if (data == null) throw new IllegalArgumentException("Cannot add null data to stack.");

    if (size == backingArray.length) { //Need to regrow array
      T[] newBack = (T[]) new Object[(int)(1.5*size)]; //Create new back

      //Loops through list
      for (int i = 0; i < size; i++) {
        newBack[i] = backingArray[i]; //Copies data over
      }

      backingArray = newBack;
    }

    backingArray[size] = data; //Push the data
    size++; //Increases size
  }

  /**
   * Return the size of the stack.
   *
   * O(1) time.
   *
   * @return number of items in the stack
   */
  public int size() {
    return size;
  }

  /**
   * Returns the backing array of this stack. Used for testing.
   *
   * @return the backing array
   */
  public Object[] getBackingArray() {
      return backingArray;
  }
}
