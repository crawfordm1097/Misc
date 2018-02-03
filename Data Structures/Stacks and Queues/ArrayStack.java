import java.util.NoSuchElementException;

/**
 * Implementation of an array-backed stack.
 *
 * @author Mikayla Crawford
 */
public class ArrayStack<T> implements StackInterface<T> {
  // Do not add new instance variables.
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
   * Do not shrink the backing array.
   * This method should be implemented in amortized O(1) time.
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
   * Push the given data onto the stack.
   *
   * If sufficient space is not available in the backing array, you should
   * regrow it to 1.5 times the current backing array length, rounding down
   * if necessary.
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
   * This method should be implemented in O(1) time.
   *
   * @return number of items in the stack
   */
  public int size() {
    return size;
  }

  /**
   * Returns the backing array of this stack.
   * Normally, you would not do this, but included for testing.
   *
   * @return the backing array
   */
  public Object[] getBackingArray() {
      // DO NOT MODIFY!
      return backingArray;
  }
}
