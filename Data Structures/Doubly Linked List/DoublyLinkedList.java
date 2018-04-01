/** This class implements a generic doubly linked list.
  @author Mikayla Crawford
  */

public class DoublyLinkedList<T> implements LinkedListInterface<T> {
  private LinkedListNode<T> head;
  private LinkedListNode<T> tail;
  private int size;

  /**
   * Adds the element to the index specified.
   *
   * Adding to indices 0 and {@code size} should be O(1), all other cases are
   * O(n).
   *
   * @param index The requested index for the new element.
   * @param data The data for the new element.
   * @throws java.lang.IndexOutOfBoundsException if index is negative or
   * index > size.
   * @throws java.lang.IllegalArgumentException if data is null.
   */
  public void addAtIndex(int index, T data) {
    if (index < 0 || index > size) throw new IndexOutOfBoundsException("Cannot add data at invalid index.");
    if (data == null) throw new IllegalArgumentException("Cannot add null data to data structure.");

    if (index == 0) { //Adding head
      addToFront(data); //Size is increased in function
    } else if (index == size) { //Adding tail
      addToBack(data); //Size is increased in function
    } else { //Adding in middle
      LinkedListNode<T> curr = head; //Tracks current node

      for (int i = 0; i < index; i++) {
        if (i == index - 1) { //Hit the index
          LinkedListNode<T> newNode = new LinkedListNode<T>(data, curr, curr.getNext()); //Creates new node with previous curr and next curr's next
          curr.getNext().setPrevious(newNode); //Updates curr's next's previous
          curr.setNext(newNode); //Updates curr's next
        }

        curr = curr.getNext(); //Go to next node
      }

      size++; //Increases size
    }
  }

  /**
   * Adds the element to the front of the list.
   *
   * O(1) for all cases.
   *
   * @param data The data for the new element.
   * @throws java.lang.IllegalArgumentException if data is null.
   */
  public void addToFront(T data) {
    if (data == null) throw new IllegalArgumentException("Cannot add null data to data structure.");
    LinkedListNode<T> newNode;

    if (isEmpty()) { //Adding head and tail
      newNode = new LinkedListNode<T>(data, null, null); //Creates new node with null previous and null next
      tail = newNode; //Updates tail variable
    } else { //Updating head
      newNode = new LinkedListNode<T>(data, null, head); //Creates new node with null previous and head next
      head.setPrevious(newNode); //Sets heads previous to the new node
    }

    head = newNode; //Updates head variable
    size++; //Increases size
  }

  /**
   * Adds the element to the back of the list.
   *
   * O(1) for all cases.
   *
   * @param data The data for the new element.
   * @throws java.lang.IllegalArgumentException if data is null.
   */
  public void addToBack(T data) {
    if (data == null) throw new IllegalArgumentException("Cannot add null data to data structure.");
    LinkedListNode<T> newNode;

    if (isEmpty()) { //Adding tail and head
      newNode = new LinkedListNode<T>(data, null, null); //Creates new node with null previous and null next
      head = newNode; //Updates head variable
    } else { //Updating tail
      newNode = new LinkedListNode<T>(data, tail, null); //Creates new node with tail previous and null next
      tail.setNext(newNode); //Sets tails next node to the new node
    }

    tail = newNode; //Updates tail variable
    size++; //Increases size
  }

  /**
   * Removes and returns the element from the index specified.
   *
   * Removing from indices 0 and size - 1 should be O(1), all other cases are
   * O(n).
   *
   * @param index The requested index to be removed.
   * @return The object formerly located at index.
   * @throws java.lang.IndexOutOfBoundsException if index is negative or
   * index >= size.
   */
  public T removeAtIndex(int index) {
    if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Cannot remove data at invalid index.");
    LinkedListNode<T> oldEle = head;

    if (index == 0) { //Remove head
      return removeFromFront(); //Size is decreased in function
    } else if (index == size - 1) { //Remove tail
      return removeFromBack(); //Size is decreased in function
    } else { //Remove middle
      LinkedListNode<T> curr = head; //Tracks current position

      //Loops through list
      for (int i = 0; i < index; i++) {
        if (i == index - 1) {
          oldEle = curr.getNext();
          curr.setNext(oldEle.getNext()); //Updates curr's next pointer
          curr.getNext().setPrevious(curr); //Updates curr next node's previous pointer
          oldEle.setNext(null); //Resets next
          oldEle.setPrevious(null); //Resets previous
        }

        curr = curr.getNext(); //Go to next node
      }
    }

    size--; //Decreases size

    return oldEle.getData(); //temp
  }

  /**
   * Removes and returns the element at the front of the list. Returns null if
   * the list is empty.
   *
   * O(1) for all cases.
   *
   * @return The object formerly located at the front.
   */
  public T removeFromFront() {
    if (isEmpty()) return null; //If list is empty
    LinkedListNode<T> oldHead = head;

    if (size == 1) { //If 1 element list
      tail = null; //Remove tail
    } else {
      oldHead.getNext().setPrevious(null); //Removes head's next node's previous pointer
    }

    head = oldHead.getNext(); //Updates head variable
    oldHead.setNext(null); //Removes oldHead's next pointer

    size--; //Decreases size

    return oldHead.getData();
  }

  /**
   * Removes and returns the element at the back of the list. Returns null if
   * the list is empty.
   *
   * O(1) for all cases.
   *
   * @return The object formerly located at the back.
   */
  public T removeFromBack() {
    if (isEmpty()) return null; //If list is empty
    LinkedListNode<T> oldTail = tail;

    if (size == 1) { //If 1 element list
      head = null; //Remove head
    } else {
      oldTail.getPrevious().setNext(null); //Removes tail's previous node's next pointer
    }

    tail = oldTail.getPrevious(); //Updates tail variable
    oldTail.setPrevious(null); //Removes oldTail's previous pointer

    size--; //Decreases size

    return oldTail.getData();
  }

  /**
   * Removes every copy of the given data from the list.
   *
   * O(n) for all cases.
   *
   * @param data The data to be removed from the list.
   * @return true if something was removed from the list; false otherwise.
   * @throws java.lang.IllegalArgumentException if data is null.
   */
  public boolean removeAllOccurrences(T data) {
    if (data == null) throw new IllegalArgumentException("Cannot remove null data from data structure.");
    LinkedListNode<T> curr = head; //Keeps track of current node
    LinkedListNode<T> next = head; //Keeps track of original next node
    boolean removed = false; //Keeps track off if an element was removed
    int index = 0; //Keeps track of current index

    while (curr != null) {
      next = curr.getNext(); //Go to next

      if (curr.getData() == data) { //If node matches
        if (index == 0) { //Removing head
          removeFromFront(); //Size is decreased in function
        } else if (index == size - 1) { //Removing tail
          removeFromBack(); //Size is decreased in function
        } else { //Removing middle
          curr.getPrevious().setNext(curr.getNext()); //Updates curr's previous node's next pointer
          curr.getNext().setPrevious(curr.getPrevious()); //Updates curr's next node's previous pointer
          curr.setNext(null); //Removes curr's next pointer
          curr.setPrevious(null); //Removes curr's previous pointer

          size--; //Decreases size
        }

        removed = true;
      } else {
        index++; //Increases index
      }

      curr = next; //Go to next
    }

    return removed; //temp
  }

  /**
   * Returns the element at the specified index.
   *
   * Getting indices 0 and size - 1 should be O(1), all other cases are O(n).
   *
   * @param index The index of the requested element.
   * @return The object stored at index.
   * @throws java.lang.IndexOutOfBoundsException if index < 0 or
   * index >= size.
   */
  public T get(int index) {
    if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Cannot access data at invalid index.");

    if (index == 0) return head.getData(); //If looking for head node
    if (index == size - 1) return tail.getData(); //If looking for tail node

    LinkedListNode<T> temp = head;
    //Loops through list
    for (int i = 0; i < index; i++) {
      temp = temp.getNext(); //Go to next node
    }

    return temp.getData();
  }

  /**
   * Returns an array representation of the linked list.
   *
   *  O(n) for all cases.
   *
   * @return An array of length {@code size} holding all of the objects in
   * this list in the same order.
   */
  public Object[] toArray() {
    Object[] arr = new Object[size]; //Initialize array
    LinkedListNode<T> temp = head; //Tracks node

    //Loops through list
    for (int i = 0; i < size; i++) {
      arr[i] = temp.getData(); //Insert value
      temp = temp.getNext(); //Go to next node
    }

    return arr;
  }

  /**
   * Returns a boolean value indicating if the list is empty.
   *
   * O(1) for all cases.
   *
   * @return true if empty; false otherwise.
   */
  public boolean isEmpty() {
    return (size == 0);
  }

  /**
   * Returns the number of elements in the list.
   *
   * O(1) for all cases.
   *
   * @return The size of the list.
   */
  public int size() {
    return size;
  }

  /**
   * Clears the list of all data.
   *
   * O(1) for all cases.
   */
  public void clear() {
    head = null; //Resets head
    tail = null; //Resets tail
    size = 0; //Resets size
  }

  /**
   * Returns the head node of the linked list. Used for testing.
   *
   * @return Node at the head of the linked list.
   */
  public LinkedListNode<T> getHead() {
      return head;
  }

  /**
   * Returns the tail node of the linked list. Used for testing.
   *
   * @return Node at the tail of the linked list.
   */
  public LinkedListNode<T> getTail() {
      return tail;
  }
}
