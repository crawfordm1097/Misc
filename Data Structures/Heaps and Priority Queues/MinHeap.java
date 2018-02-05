import java.util.NoSuchElementException;

/**
 * Implementation of a min heap.
 *
 * @author Mikayla Crawford
 */
public class MinHeap<T extends Comparable<? super T>> implements HeapInterface<T> {
  private T[] backingArray;
  private int size;
  // Do not add any more instance variables. Do not change the declaration
  // of the instance variables above.

  /**
   * Creates a Heap with an initial size of {@code STARTING_SIZE} for the
   * backing array.
   *
   * Use the constant field in the interface. Do not use magic numbers!
   */
  public MinHeap() {
      backingArray = (T[]) new Comparable[STARTING_SIZE];
      size = 0;
  }

  /**
   * Adds an item to the heap. If the backing array is full and you're trying
   * to add a new item, then increase its size by 1.5 times, rounding down
   * if necessary. No duplicates will be added.
   *
   * @throws IllegalArgumentException if the item is null
   * @param item the item to be added to the heap
   */
  public void add(T item) {
    if (item == null) throw new IllegalArgumentException("Cannot add null data to heap.");

    //Regrow array
    if (size == backingArray.length - 1) {
      T[] newBack = (T[]) new Comparable[(int)(backingArray.length*1.5)];

      for (int i = 1; i <= size; i++) {
        newBack[i] = backingArray[i]; //Copy elements over
      }

      backingArray = newBack;
    }

    boolean siftUp = true;
    size++; //Increases size
    backingArray[size] = item; //Add new item
    int childLoc = size; //Index of the child
    int parLoc = size / 2; //Index of the parent

    //Sifts new element up
    while (siftUp && parLoc != 0) {
      if (backingArray[parLoc].compareTo(backingArray[childLoc]) > 0) { //Parent is larger -> heap property broken
        T temp = backingArray[parLoc]; //Holder for parents value
        backingArray[parLoc] = backingArray[childLoc]; //Updates parent to smaller value
        backingArray[childLoc] = temp; //Updates child to larger value

        //Recalculate trackers
        childLoc = parLoc;
        parLoc = childLoc / 2;
      } else { //Parent is smaller -> heap property satisfied
        siftUp = false;
      }
    }
  }

  /**
   * Removes and returns the first item of the heap. Do not decrease the size
   * of the backing array.
   *
   * @throws java.util.NoSuchElementException if the heap is empty
   * @return the item removed
   */
  public T remove() {
    if (isEmpty()) throw new NoSuchElementException("Cannot remove data from an empty heap.");
    T data = backingArray[1]; //Holder for root value
    boolean siftDown = true;
    int parLoc = 1;
    int child1Loc = 2*parLoc;
    int child2Loc = 2*parLoc + 1;
    backingArray[1] = backingArray[size]; //Moves last element to root
    backingArray[size] = null; //Resets last element
    size--; //Decreases size

    while (siftDown) {
      if (child1Loc > size) { //No children
        siftDown = false;
      } else { //Children
        int smallChildLoc = child1Loc; //Tracks the index of the smaller child
        if (child2Loc <= size && backingArray[child1Loc].compareTo(backingArray[child2Loc]) > 0) smallChildLoc = child2Loc;

        if (backingArray[parLoc].compareTo(backingArray[smallChildLoc]) > 0) { //Heap property broken -> sift down
          T temp = backingArray[parLoc]; //Holder for parents value
          backingArray[parLoc] = backingArray[smallChildLoc]; //Update parent to smaller value
          backingArray[smallChildLoc] = temp; //Update child to larger value

          //Recalculate trackers
          parLoc = smallChildLoc;
          child1Loc = 2*parLoc;
          child2Loc = 2*parLoc + 1;
        } else { //Heap property satisfied
          siftDown = false;
        }
      }
    }

    return data;
  }

  /**
   * Returns if the heap is empty or not.
   * @return a boolean representing if the heap is empty
   */
  public boolean isEmpty() {
      return (size == 0);
  }

  /**
   * Returns the size of the heap.
   * @return the size of the heap
   */
  public int size() {
      return size;
  }

  /**
   * Clears the heap and returns array to starting size.
   */
  public void clear() {
    backingArray = (T[]) new Comparable[STARTING_SIZE];
    size = 0;
  }

  /**
   * Used for grading purposes only.
   *
   * DO NOT USE OR EDIT THIS METHOD!
   *
   * @return the backing array
   */
  public Comparable[] getBackingArray() {
      // DO NOT CHANGE THIS METHOD!
      return backingArray;
  }
}
