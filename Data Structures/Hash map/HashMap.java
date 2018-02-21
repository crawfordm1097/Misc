import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import static java.lang.StrictMath.abs;

/**
 * Implementation of HashMap.
 *
 * @author Mikayla Crawford
 */
public class HashMap<K, V> implements HashMapInterface<K, V> {
  // Do not make any new instance variables.
  private MapEntry<K, V>[] table;
  private int size;

  /**
   * Create a hash map with no entries. The backing array has an initial
   * capacity of {@code INITIAL_CAPACITY}.
   *
   * Do not use magic numbers!
   *
   * Use constructor chaining.
   */
  public HashMap() {
      this(INITIAL_CAPACITY);
  }

  /**
   * Create a hash map with no entries. The backing array has an initial
   * capacity of {@code initialCapacity}.
   *
   * You may assume {@code initialCapacity} will always be positive.
   *
   * @param initialCapacity initial capacity of the backing array
   */
  public HashMap(int initialCapacity) {
      table = (MapEntry<K, V>[]) new MapEntry[initialCapacity];
      size = 0;
  }

  /**
   * Adds the given key-value pair to the HashMap.
   * If an entry in the HashMap already has this key, replace the entry's
   * value with the new one passed in.
   *
   * In the case of a collision, use external chaining as your resolution
   * strategy. Add new entries to the front of an existing chain, but don't
   * forget to check the entire chain for duplicates first.
   *
   * Check to see if the backing array needs to be regrown BEFORE adding. For
   * example, if my HashMap has a backing array of length 5, and 3 elements in
   * it, I should regrow at the start of the next add operation (even if it
   * is a key that is already in the hash map). This means you must account
   * for the data pending insertion when calculating the load factor.
   *
   * When regrowing, increase the length of the backing table by
   * 2 * old length + 1. Use the resizeBackingTable method.
   *
   * Return null if the key was not already in the map. If it was in the map,
   * return the old value associated with it.
   *
   * @param key key to add into the HashMap
   * @param value value to add into the HashMap
   * @throws IllegalArgumentException if key or value is null
   * @return null if the key was not already in the map.  If it was in the
   * map, return the old value associated with it
   */
  public V put(K key, V value) {
    if (key == null) throw new IllegalArgumentException("Cannot add data with null key to hashmap.");
    if (value == null) throw new IllegalArgumentException("Cannot add data with null value to hashmap.");

    //If next element will exceed load factor -> regrow table
    if (table.length * MAX_LOAD_FACTOR < size + 1) {
      resizeBackingTable(2*table.length + 1);
    }

    int index = Math.abs(key.hashCode()) % table.length; //The index to add to
    MapEntry<K, V> newEntry = new MapEntry<>(key, value, null);
    MapEntry<K, V> curr = table[index];

    //Checks for existing key
    while (curr != null) {
      if (curr.getKey().equals(key)) { //If curr's key matches
        V oldVal = curr.getValue(); //Gets old value
        curr.setValue(value); //Updates value
        return oldVal; //Returns old value
      }

      curr = curr.getNext(); //Go to next
    }

    newEntry.setNext(table[index]); //Update next
    table[index] = newEntry; //Add to front
    size++; //Increase size
    return null;
  }

  /**
   * Removes the entry with a matching key from the HashMap.
   *
   * @param key the key to remove
   * @throws IllegalArgumentException if key is null
   * @throws java.util.NoSuchElementException if the key does not exist
   * @return the value previously associated with the key
   */
  public V remove(K key) {
    if (key == null) throw new IllegalArgumentException("Cannot remove data with null key from hashmap.");

    int index = Math.abs(key.hashCode()) % table.length; //The index the key would be located at
    V val = null;
    MapEntry<K, V> curr = table[index];

    while (curr != null) {
      if (curr.getKey().equals(key)) { //If curr matches (only happens for front)
        val = curr.getValue(); //Get value
        table[index] = curr.getNext(); //Update front
        curr = null; //End loop
      } else if (curr.getNext() == null) { //No next (key not contained)
        curr = null;
      } else if (curr.getNext().getKey().equals(key)) { //If next matches
        MapEntry<K, V> temp = curr.getNext();
        val = temp.getValue(); //Get value
        curr.setNext(temp.getNext()); //Update curr's next
        temp.setNext(null); //Remove old curr's next next
        curr = null; //End loop
      } else { //No match
          curr = curr.getNext(); //Go to next
      }
    }

    if (val == null) throw new NoSuchElementException("Cannot remove data that doesn't exist in hashmap.");

    size--;

    return val;
  }

  /**
   * Gets the value associated with the given key.
   *
   * @param key the key to search for
   * @throws IllegalArgumentException if key is null
   * @throws java.util.NoSuchElementException if the key is not in the map
   * @return the value associated with the given key
   */
  public V get(K key) {
    if (key == null) throw new IllegalArgumentException("Cannot retrieve data with null key from hasmap.");

    int index = Math.abs(key.hashCode()) % table.length; //The index the key would be located at
    V val = null;

    if (table[index] == null) throw new NoSuchElementException("Cannot retrieve data that doesn't exist in hashmap.");

    MapEntry<K, V> curr = table[index];

    //Loop through chain to find value
    while (curr != null) {
      if (curr.getKey().equals(key)) { //Match
        val = curr.getValue(); //Get value
        curr = null; //End loop
      } else { //No match
        curr = curr.getNext(); //Go to next
      }
    }

    if (val == null) throw new NoSuchElementException("Cannot retrieve data that doesn't exist in hashmap.");

    return val;
  }

  /**
   * Returns whether or not the key is in the map.
   *
   * @param key the key to search for
   * @throws IllegalArgumentException if key is null
   * @return whether or not the key is in the map
   */
  public boolean containsKey(K key) {
    if (key == null) throw new IllegalArgumentException("Cannot determine if data with null key is in hasmap.");

    int index = Math.abs(key.hashCode()) % table.length; //Index entry would be at
    boolean contains = false;
    MapEntry<K, V> curr = table[index];

    while (curr != null) {
      if (curr.getKey().equals(key)) { //Match
        contains = true;
        curr = null; //End loop
      } else { //No match
        curr = curr.getNext(); //Go to next
      }
    }


    return contains;
  }

  /**
   * Clears the table and resets it to the default length.
   */
  public void clear() {
    table = (MapEntry<K, V>[]) new MapEntry[INITIAL_CAPACITY];
    size = 0;
  }

  /**
   * Returns the number of elements in the map.
   *
   * @return number of elements in the HashMap
   */
  public int size() {
      return size;
  }

  /**
   * Returns a Set view of the keys contained in this map.
   * Use {@code java.util.HashSet}.
   *
   * @return set of keys in this map
   */
  public Set<K> keySet() {
    Set<K> set = new HashSet<K>();

    //Loops through hashmap
    for (int i = 0; i < table.length; i++) {
      if (table[i] != null) { //Entry exists
        MapEntry<K, V> curr = table[i]; //Front of chain

        //Loops through chain
        while (curr != null) {
            set.add(curr.getKey()); //Adds key
            curr = curr.getNext(); //Go to next
        }
      }
    }

    return set;
  }

  /**
   * Returns a List view of the values contained in this map.
   * Use any class that implements the List interface
   * This includes {@code java.util.ArrayList} and
   * {@code java.util.LinkedList}. You should iterate over the table in
   * order of increasing index, and iterate over each chain from front to
   * back. Add entries to the List in the order in which they are traversed.
   *
   * @return list of values in this map
   */
  public List<V> values() {
    List<V> list = new LinkedList<V>();

    //Loops through hashmap
    for (int i = 0; i < table.length; i++) {
      if (table[i] != null) { //Entry exists
        MapEntry<K, V> curr = table[i]; //Front of chain

        //Loops through chain
        while (curr != null) {
            list.add(curr.getValue()); //Adds value
            curr = curr.getNext(); //Go to next
        }
      }
    }

    return list;
  }

  /**
   * Resize the backing table to {@code length}.
   *
   * After resizing, the table's load factor is permitted to exceed
   * MAX_LOAD_FACTOR. No adjustment to the backing table's length is necessary
   * should this occurr.
   *
   * Remember that you cannot just simply copy the entries over to the new
   * array.
   *
   * @param length new length of the backing table
   * @throws IllegalArgumentException if length is non-positive or less than
   * the number of items in the hash map.
   */
  public void resizeBackingTable(int length) {
    if (length <= 0) throw new IllegalArgumentException("Cannot resize backing table to negative value.");
    if (length < size) throw new IllegalArgumentException("Cannot shrink backing table.");

    MapEntry<K, V>[] newTable = (MapEntry<K, V>[]) new MapEntry[length];

    //Loops through the table
    for (int i = 0; i < table.length; i++) {
      if (table[i] != null) { //If en entry exists
        MapEntry<K, V> curr = table[i];

        //Loops through chain
        while (curr != null) {
          int index = Math.abs(curr.getKey().hashCode()) % length; //The new index
          MapEntry<K, V> newEntry = new MapEntry<>(curr.getKey(), curr.getValue());

          if (newTable[index] == null) { //Start of chain
            newTable[index] = newEntry;
          } else { //Add to beginning of chain
            newEntry.setNext(newTable[index]);
            newTable[index] = newEntry;
          }

          curr = curr.getNext();
        }
      }
    }

    table = newTable;
  }

  /**
   * DO NOT USE THIS METHOD IN YOUR CODE.  IT IS FOR TESTING ONLY.
   *
   * @return the backing array of the data structure, not a copy.  INCLUDE
   * EMPTY SPACES
   */
  public MapEntry<K, V>[] getTable() {
      // DO NOT EDIT THIS METHOD!
      return table;
  }
}
