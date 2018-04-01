
/** This class implements an array list.
  *
  * @author Mikayla Crawford
  */
public class ArrayList<T> implements ArrayListInterface<T> {
  private T[] backingArray;
  private int size;

  /**
   * Constructs a new ArrayList.
   */
  public ArrayList() {
      backingArray = (T[]) new Object[INITIAL_CAPACITY];
      size = 0;
  }

  /**
   * Adds the element to the index specified. It shifts elements up if need be.
   *
   * Adding to index {@code size} should be O(1), all other adds are O(n).
   *
   * @param index The index where you want the new element.
   * @param data Any object of type T.
   * @throws java.lang.IndexOutOfBoundsException if index is negative
   * or index > size.
   * @throws java.lang.IllegalArgumentException if data is null.
   */
  public void addAtIndex(int index, T data) {
    if (index < 0 || index > size) throw new IndexOutOfBoundsException("Cannot add data at an invalid index.");
    if (data == null) throw new IllegalArgumentException("Cannot add null data to data structure.");

    //If list needs to be regrown
    if (!isEmpty() && size % INITIAL_CAPACITY == 0) {
      T[] newBack = (T[]) new Object[2*size];

      //Loops through elements
      for (int i = size; i >= 0; i--) {
        if (i < index) {
          newBack[i] = backingArray[i]; //Copies elements
        } else if (i > index) {
            newBack[i] = backingArray[i - 1]; //Shifts elements
        } else if (i == index) {
          newBack[index] = data; //adds new element
        }
      }

      backingArray = newBack; //Updates backing array
    } else {

      //Shifts elements
      for (int i = size; i > index; i--) {
        backingArray[i] = backingArray[i - 1];
      }

      backingArray[index] = data; //Add new element
    }

    size++; //Increases size
  }

  /**
   * Add the given data to the front of your array list. Shifts elements if
   * needed.
   *
   * Is O(n).
   *
   * @param data The data to add to the list.
   * @throws java.lang.IllegalArgumentException if data is null.
   */
  public void addToFront(T data) {
    //If data is null throws exception
    if (data == null) throw new IllegalArgumentException("Cannot add null data to data structure.");

    //If list needsto be regrown
    if (!isEmpty() && size % INITIAL_CAPACITY == 0) {
      T[] newBack = (T[]) new Object[2*size];

      //Loops through the list and shifts if necessary
      for (int i = size; i > 0; i--) {
        newBack[i] = backingArray[i - 1];
      }

      newBack[0] = data; //Adds new element
      backingArray = newBack;
    } else {

      //Loops through the list and shifts if necessary
      for (int i = size; i > 0; i--) {
        backingArray[i] = backingArray[i - 1];
      }

      backingArray[0] = data;
    }

    size++; //Increases size
  }

  /**
   * Add the given data to the back of the array list.
   *
   * Is O(1).
   *
   * @param data The data to add to the list.
   * @throws java.lang.IllegalArgumentException if data is null.
   */
  public void addToBack(T data) {
    if (data == null) throw new IllegalArgumentException("Cannot add null data to data structure.");

    if (!isEmpty() && size % INITIAL_CAPACITY == 0) {
      T[] newBack = (T[]) new Object[2*size];

      //Updates new back
      for (int i = 0; i < size; i++) {
        newBack[i] = backingArray[i];
      }

      newBack[size] = data; //Adds element
      backingArray = newBack; //Resets backing array
    } else {
      backingArray[size] = data; //Adds element
    }

    size++; //Increases size
  }

  /**
   * Returns the element at the given index.
   *
   * Is O(1).
   *
   * @param index The index of the element
   * @return The data stored at that index.
   * @throws java.lang.IndexOutOfBoundsException if index < 0 or
   * index >= size.
   */
  public T get(int index) {
    if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Cannot access data at an invalid index.");

    return backingArray[index];
  }

  /**
   * Removes and returns the element at index. Elements are shifted if needed.
   *
   * This method should be O(1) for index {@code size}, and O(n) in all other
   * cases.
   *
   * @param index The index of the element
   * @return The object that was formerly at that index.
   * @throws java.lang.IndexOutOfBoundsException if index < 0 or
   * index >= size.
   */
  public T removeAtIndex(int index) {
    if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Cannot remove data at an invalid index.");
    T temp = backingArray[index]; //The old element

    if (index == (size - 1)) { //End of the list
      backingArray[index] = null; //Reset value
    } else { //Have to shift list
      //Loops through the list
      for (int i = index; i < size - 1; i++) {
        backingArray[i] = backingArray[i + 1]; //Shifts elements
      }

      backingArray[size - 1] = null; //Resets last element
    }

    size--; //Decreases the size

    return temp;
  }

  /**
   * Remove the first element in the list and return it. Returns null if the
   * list is empty. Shifts elements if needed.
   *
   * Is O(n).
   *
   * @return The data from the front of the list or null.
   */
  public T removeFromFront() {
    if (isEmpty()) return null; //Returns null if list is empty
    T temp = backingArray[0]; //The old element

    //Loops through the list
    for (int i = 0; i < size - 1; i++) {
      backingArray[i] = backingArray[i + 1]; //Shifts elements
    }

    backingArray[size - 1] = null; //Resets last element

    size--; //Decreases size

    return temp;
  }

  /**
   * Remove the last element in the list and return it. Returns null if the
   * list is empty.
   *
   * Is O(1).
   *
   * @return The data from the back of the list or null.
   */
  public T removeFromBack() {
    if (this.isEmpty()) return null;
    T temp = backingArray[0]; //The old element

    backingArray[size - 1] = null;

    size--; //Decreases size

    return temp;
  }

  /**
   * Return a boolean value representing whether or not the list is empty.
   *
   * Is O(1).
   *
   * @return true if empty; false otherwise
   */
  public boolean isEmpty() {
    return (size == 0);
  }

  /**
   * Return the size of the list as an integer.
   *
   * Is O(1).
   *
   * @return The size of the list.
   */
  public int size() {
    return size;
  }

  /**
   * Clear the list. Reset the backing array to a new array of the initial
   * capacity.
   *
   * Is O(1).
   */
  public void clear() {
    backingArray = (T[]) new Object[backingArray.length]; //Resets the backing array
    size = 0; //Resets the size
  }

  /**
   * Return the backing array for this list. Used for testing.
   *
   * @return the backing array for this list
   */
  public Object[] getBackingArray() {
      return backingArray;
  }
}
