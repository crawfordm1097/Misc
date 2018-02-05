import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Implementation of a binary search tree.
 *
 * @author Mikayla Crawford
 */
public class BST<T extends Comparable<? super T>> implements BSTInterface<T> {
  // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
  private BSTNode<T> root;
  private int size;

  /**
   * A no argument constructor that should initialize an empty BST.
   */
  public BST() {
  }

  /**
   * Initializes the BST with the data in the Collection. The data in the BST
   * should be added in the same order it is in the Collection.
   *
   * @param data the data to add to the tree
   * @throws IllegalArgumentException if data or any element in data is null
   */
  public BST(Collection<T> data) {
      if (data == null) {
          throw new IllegalArgumentException("Initialization error: Cannot"
                  + " be initialized with null");
      }
      for (T node : data) {
          if (node == null) {
              throw new IllegalArgumentException("Initialization error: "
                      + "Some element in the collection is null");
          }
          add(node);
      }
  }

  /**
   * Add the data as a leaf in the BST.  Should traverse the tree to find the
   * appropriate location. If the data is already in the tree, then nothing
   * should be done (the duplicate shouldn't get added, and size should not be
   * incremented).
   * Should have a running time of O(log n) for a balanced tree, and a worst
   * case of O(n).7
   *
   * @throws IllegalArgumentException if the data is null
   * @param data the data to be added
   */
  public void add(T data) {
    if (data == null) throw new IllegalArgumentException("Cannot add null data to tree.");

    if (size == 0) { //If tree is empty -> add root
      root = new BSTNode<T>(data);
      size++;
    } else { //Add normally
      boolean searchForSpot = true;
      BSTNode<T> curr = root;
      BSTNode<T> left = root.getLeft();
      BSTNode<T> right = root.getRight();

      //Traverses the tree
      while (searchForSpot) {
        if (data.compareTo(curr.getData()) < 0) { //Less than -> go down left
          if (left == null) { //There is no left node -> add data
            curr.setLeft(new BSTNode<T>(data));
            size++;
            searchForSpot = false;
          } else { //There is a left node -> move down left
            curr = left;
            left = curr.getLeft();
            right = curr.getRight();
          }
        } else if (data.compareTo(curr.getData()) > 0) { //Greater than -> go down right
          if (right == null) { //There is no right node -> add data
            curr.setRight(new BSTNode<T>(data));
            size++;
            searchForSpot = false;
          } else { //There is a right node -> move down right
            curr = right;
            left = curr.getLeft();
            right = curr.getRight();
          }
        } else { //Equal
          searchForSpot = false;
        }
      }
    }
  }

  /**
   * Removes the data from the tree.  There are 3 cases to consider:
   * 1: the data is a leaf.  In this case, simply remove it.
   * 2: the data has one child.  In this case, simply replace it with its
   * child.
   * 3: the data has 2 children.  There are generally two approaches:
   * replacing the data with either the largest element that is smaller than
   * the element being removed (commonly called the predecessor), or replacing
   * it with the smallest element that is larger than the element being
   * removed (commonly called the successor). For this assignment, use the
   * predecessor.
   * Should have a running time of O(log n) for a balanced tree, and a worst
   * case of O(n).
   *
   * @throws IllegalArgumentException if the data is null
   * @throws java.util.NoSuchElementException if the data is not found
   * @param data the data to remove from the tree.
   * @return the data removed from the tree.  Do not return the same data
   * that was passed in.  Return the data that was stored in the tree.
   */
  public T remove(T data) {
    if (data == null) throw new IllegalArgumentException("Cannot remove null data from tree.");
    boolean search = true;
    BSTNode<T> parent = null;
    BSTNode<T> curr = root;
    T nodeData = null;
    boolean leftSide = false;

    //Traverses tree
    while (search) {
      if (curr == null) { //Data DNE
        throw new NoSuchElementException("Cannot remove data that DNE in tree.");
      } else if (data.compareTo(curr.getData()) < 0) { //Less than -> go down left
        parent = curr;
        curr = curr.getLeft();
        leftSide = true;
      } else if (data.compareTo(curr.getData()) > 0) { //Greater than -> go down right
        parent = curr;
        curr = curr.getRight();
        leftSide = false;
      } else { //Matches
        nodeData = curr.getData();
        removeHelper(parent, curr, leftSide);
        search = false;
        size--;
      }
    }

    return nodeData;
  }

  /**
   * Helper method for node removal. It handles the specific removal cases.
   * @param parent the nodes parent
   * @param nodeToRemove the node to remove
   * @param leftSide true if the node is on the left of the parent, false otherwise
   */
  private void removeHelper(BSTNode<T> parent, BSTNode<T> nodeToRemove, boolean leftSide) {
    BSTNode<T> left = nodeToRemove.getLeft();
    BSTNode<T> right = nodeToRemove.getRight();
    BSTNode<T> replacement = null; //Set up for base case of no children

    if (left == null && right != null) { //Right child
      replacement = right;
    } else if (right == null && left != null) { //Left child
      replacement = left;
    } else if (right != null && left != null) { //Both children
      BSTNode<T> replacementParent = left;
      replacement = left.getRight();

      if (replacement != null) { //There is a right subtree
        //Loop until last predRight's parent is hit
        while (replacement.getRight() != null) {
          replacementParent = replacement;
          replacement = replacement.getRight();
        }

        replacement.setRight(right); //Updates predRight's right
        replacementParent.setRight(replacement.getLeft()); //Updates pred's left
        replacement.setLeft(left); //Update predRight's left
      } else { //No right subtree -> easier case
        replacementParent.setRight(right); //Updates predecessors right
        replacement = replacementParent;
      }
    }

    //Update parent
    if (parent == null) { //Root
      root = replacement;
    } else if (leftSide) { //Left child
      parent.setLeft(replacement);
    } else { //Right child
      parent.setRight(replacement);
    }
  }

  /**
   * Returns the data in the tree matching the parameter passed in (think
   * carefully: should you use .equals or == ?).
   * Should have a running time of O(log n) for a balanced tree, and a worst
   * case of O(n).
   *
   * @throws IllegalArgumentException if the data is null
   * @throws java.util.NoSuchElementException if the data is not found
   * @param data the data to search for in the tree.
   * @return the data in the tree equal to the parameter. Do not return the
   * same data that was passed in.  Return the data that was stored in the
   * tree.
   */
  public T get(T data) {
    if (data == null) throw new IllegalArgumentException("Cannot find null data in tree.");
    BSTNode<T> foundNode = findNode(data);

    if (foundNode == null) throw new NoSuchElementException("Cannot find data that DNE in tree.");

    return foundNode.getData();
  }

  /**
   * Helper method for finding a specific node in the tree.
   * Running time of O(log n) for balanced tree, and worst case of O(n).
   * @param data the data to search for
   * @return the node that matches or null if no node was found
   */
  private BSTNode<T> findNode(T data) {
    boolean search = true;
    BSTNode<T> curr = root;

    while (search) {
      if (curr == null) { //Data DNE
        return null;
      } else if (data.compareTo(curr.getData()) < 0) { //Less than -> go down left
        curr = curr.getLeft();
      } else if (data.compareTo(curr.getData()) > 0) { //Greater than -> go down right
        curr = curr.getRight();
      } else { //matches
        search = false;
      }
    }

    return curr;
  }

  /**
   * Returns whether or not the parameter is contained within the tree.
   * Should have a running time of O(log n) for a balanced tree, and a worst
   * case of O(n).
   *
   * @throws IllegalArgumentException if the data is null
   * @param data the data to search for in the tree.
   * @return whether or not the parameter is contained within the tree.
   */
  public boolean contains(T data) {
    if (data == null) throw new IllegalArgumentException("Cannot find null data in tree.");

    return (findNode(data) != null);
  }

  /**
   * Should run in O(1).
   *
   * @return the number of elements in the tree
   */
  public int size() {
      return size;
  }

  /**
   * Should run in O(n).
   *
   * @return a preorder traversal of the tree
   */
  public List<T> preorder() {
    List<T> preordered = new ArrayList<T>();
    return preorderHelper(root, preordered);
  }

  /**
   * Helper method for preorder traversal.
   * @param node the node to preorder
   * @param list the list to add the data to
   * @return the postordered list
   */
  private List<T> preorderHelper(BSTNode<T> node, List<T> list) {
    if (node != null) {
      list.add(node.getData()); //Base case: add data

      //Recursively call on left child
      if (node.getLeft() != null) {
        preorderHelper(node.getLeft(), list);
      }

      //Recursively call on right child
      if (node.getRight() != null) {
        preorderHelper(node.getRight(), list);
      }
    }

    return list;
  }

  /**
   * Should run in O(n).
   *
   * @return a postorder traversal of the tree
   */
  public List<T> postorder() {
    List<T> postordered = new ArrayList<T>();
    return postorderHelper(root, postordered);
  }

  /**
   * Helper method for postorder traversal.
   * @param node the node to postorder
   * @param list the list to add the data to
   * @return the postordered list
   */
  private List<T> postorderHelper(BSTNode<T> node, List<T> list) {
    if (node != null) {
      //Recursively call on left child
      if (node.getLeft() != null) {
        postorderHelper(node.getLeft(), list);
      }

      //Recursively call on right child
      if (node.getRight() != null) {
        postorderHelper(node.getRight(), list);
      }

      //Base case: add data
      list.add(node.getData());
    }

    return list;
  }

  /**
   * Should run in O(n).
   *
   * @return an inorder traversal of the tree
   */
  public List<T> inorder() {
    List<T> inordered = new ArrayList<T>();
    return inorderHelper(root, inordered);
  }

  /**
   * Helper method for inorder traversal.
   * @param node the node to inorder
   * @param list the list to add the data to
   * @return the inordered list
   */
  private List<T> inorderHelper(BSTNode<T> node, List<T> list) {
    if (node != null) {
      //Recursively call on left child
      if (node.getLeft() != null) {
        inorderHelper(node.getLeft(), list);
      }

      //Base case: add data
      list.add(node.getData());

      //Recursively call on right child
      if (node.getRight() != null) {
        inorderHelper(node.getRight(), list);
      }
    }

    return list;
  }

  /**
   * Generate a level-order traversal of the tree.
   *
   * To do this, add the root node to a queue. Then, while the queue isn't
   * empty, remove one node, add its data to the list being returned, and add
   * its left and right child nodes to the queue. If what you just removed is
   * {@code null}, ignore it and continue with the rest of the nodes.
   *
   * Should run in O(n).
   *
   * @return a level order traversal of the tree
   */
  public List<T> levelorder() {
    Queue<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
    List<T> list = new ArrayList<T>();

    if (root != null) queue.add(root);

    while (!queue.isEmpty()) {
      BSTNode<T> node = queue.poll(); //Remove node

      if (node != null) {
        list.add(node.getData()); //Add data to list
        queue.add(node.getLeft()); //Add left node
        queue.add(node.getRight()); //Add right node
      }
    }

    return list;
  }

  /**
   * Clear the tree.  Should be O(1).
   */
  public void clear() {
    root = null;
    size = 0;
  }

  /**
   * Calculate and return the height of the root of the tree.  A node's
   * height is defined as {@code max(left.height, right.height) + 1}. A leaf
   * node has a height of 0.
   * Should be calculated in O(n).
   *
   * @return the height of the root of the tree, -1 if the tree is empty
   */
  public int height() {
    return (size == 0 ? -1 : height(root));
  }

  /**
   * Helper method for recursively determining the height off a specific node
   * Calculated in O(n).
   * @param node the node to determine the height of
   */
  private int height(BSTNode<T> node) {
    if (node.getLeft() == null && node.getRight() == null) { //Base case: leaf node
      return 0;
    } else if (node.getLeft() == null) { //Left node DNE
      return height(node.getRight()) + 1;
    } else if (node.getRight() == null) { //Right node DNE
      return height(node.getLeft()) + 1;
    }

    return Math.max(height(node.getLeft()), height(node.getRight())) + 1;
  }

  /**
   * THIS METHOD IS ONLY FOR TESTING PURPOSES.
   *
   * @return the root of the tree
   */
  public BSTNode<T> getRoot() {
      // DO NOT EDIT THIS METHOD!
      return root;
  }


}
