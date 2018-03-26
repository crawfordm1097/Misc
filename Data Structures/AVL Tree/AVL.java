import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Mikayla Crawford
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> implements AVLInterface<T> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty AVL tree.
     * DO NOT IMPLEMENT THIS CONSTRUCTOR!
     */
    public AVL() {
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException(
                    "init error: Collection is null");
        }
        for (T entry : data) {
            if (entry == null) {
                throw new IllegalArgumentException(
                        "init error: data in collection is null");
            }
            add(entry);
        }
    }

    /**
     * Add the data as a leaf to the AVL. Should traverse the tree to find the
     * appropriate location. If the data is already in the tree, then nothing
     * should be done (the duplicate shouldn't get added, and size should not be
     * incremented).
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
      if (data == null) throw new IllegalArgumentException("Cannot add null data to AVL tree.");

      add(root, data);
    }

    /**
     * Recursive helper method for adding a node to the AVL tree.
     *
     * @param parent the node to start searching from
     * @param data the data to add
     * @return whether a node has been added or not
     */
    private boolean add(AVLNode<T> parent, T data)  {
      boolean added = false;
      boolean balanced = false;

      //Adding node (going down tree)
      if (size == 0) { //Base case: add root
         root = new AVLNode<T>(data);
         root.setHeight(0);
         added = true;
         size++;
      } else {
        AVLNode<T> left = parent.getLeft();
        AVLNode<T> right = parent.getRight();

        if (data.compareTo(parent.getData()) < 0) { //Go down left tree
          if (left == null) { //Base case: add node in left
            AVLNode<T> newNode = new AVLNode<T>(data);
            newNode.setHeight(0); //Updates newNodes height
            parent.setLeft(newNode);
            added = true;
            size++;
          } else { //Continue down left
            added = addNode(left, data);
          }
        } else if (data.compareTo(parent.getData()) > 0) { //Go down right tree
          if (right == null) { //Base case: add node in right
            AVLNode<T> newNode = new AVLNode<T>(data);
            newNode.setHeight(0); //Updates newNodes height
            parent.setRight(newNode);
            added = true;
            size++;
          } else { //Continue down right
            added = addNode(right, data);
          }
        }
      }

      //Rebalancing (going back up tree)
      if (added && !balanced) {
        parent.setHeight(determineHeight(parent)); //Updates parents height
        parent.setBalanceFactor(determineBF(parent)); //Updates BF

        //Checks balance
        if (parent.getBalanceFactor() > 1) { //Left height greater
          if (data < parent.getLeft().getData()) { //Single rotation (LL)
            rotateLeft(parent);
          } else { //Double rotation (LR)
            rotateLeft(parent.getLeft()); //Rotate around left child first
            rotateRight(parent);
          }

          balanced = true;
        } else if (parent.getBalanceFactor() < 1) { //Right height greater -> either RR or RL
          if (data < parent.getRight().getData()) { //Single rotation (RR)
            rotateRight(parent);
          } else { //Double rotation (RL)
            rotateLeft(parent.getRight()); //Rotate around right child first
            rotateRight(parent);
          }

          balanced = true;
        }
      }

      return added;
    }

    /**
     * Helper method for rebalancing tree. Handles case for rotating around a right node.
     *
     * @param parent the parent of the node to rotate
     */
    private void rotateRight(AVLNode<T> parent) {
      AVLNode<T> left = parent.getLeft(); //Left child

      parent.setLeft(left.getRight()); //Updates parent's left child to left's right
      left.setRight(parent); //Updates left's right to parent

      parent.setHeight(left.getHeight() - 1); //Updates parent's height
      parent.setBalanceFactor(determineBF(parent)); //Updates parent's BF
    }


    /**
     * Helper method for rebalancing tree. Handles case for rotating around left node.
     *
     * @param parent the parent of the node to rotate
     */
    private void rotateLeft(AVLNode<T> parent) {
      AVLNode<T> right = parent.getRight(); //Right child

      parent.setRight(right.getLeft()); //Updates parent's right child to right's Left
      right.setLeft(parent); //Updates right's left to parent

      parent.setHeight(right.getHeight() - 1); //Updates parent's height
      parent.setBalanceFactor(determineBF(parent)); //Updates parent's BF
    }

    /**
     * Helper method for determing height of specific node.
     *
     * @param node the node to recalculate the height for
     * @return the new height of the node
     */
    private int determineHeight(AVLNode<T> node) {
      int height = 0; //Base case leaf node
      AVLNode<T> left = node.getLeft();
      AVLNode<T> right = node.getRight();

      if (left != null && right != null) { //Both children
        height = Math.max(left.getHeight(), right.getHeight()) + 1;
      } else if (left == null && right != null) { //Right only
        height = right.getHeight() + 1;
      } else if (left != null && right == null){ //Left only
        height = left.getHeight() + 1;
      }

      return height;
    }

    /**
     * Helper method for determining the balance factor of a node.
     *
     * @param node the node to determine the BF of
     * @return the BF of the node
     */
    private int determineBF(AVLNode<T> node) {
      int bf = 0;
      AVLNode<T> left = parent.getLeft();
      AVLNode<T> right = parent.getRight();

      if (left != null && right != null) { //Both children
        bf = left.getHeight() - right.getHeight();
      } else if (left == null) { //Right child
        bf = 0 - right.getHeight();
      } else if (right == null) { //Left child
        bf = left.getHeight();
      }

      return bf;
    }

    /** TODO
     * Removes the data from the tree.  There are 3 cases to consider:
     * 1: the data is a leaf.  In this case, simply remove it.
     * 2: the data has one child.  In this case, simply replace the node with
     * the child node.
     * 3: the data has 2 children.  There are generally two approaches:
     * replacing the data with either the largest element in the left subtree
     * (commonly called the predecessor), or replacing it with the smallest
     * element in the right subtree (commonly called the successor). For this
     * assignment, use the predecessor.
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not in the tree
     * @param data data to remove from the tree
     * @return the data removed from the tree.  Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
      if (data == null) throw new IllegalArgumentException("Cannot remove null data from AVL tree.");

      T foundData = remove(root, data);

      if (foundData == null) throw new NoSuchElementException("Cannot remove data that doesn't exist in AVL tree.");

      return foundData;
    }

    /** TODO
     * Recursive helper method for removing data from AVL tree.
     *
     * @param node the parent node to compare to
     * @param data the data to look for
     * @return the data if it was found, null otherwise
     */
    private T remove(ALVNode<T> node, T data) {
      T foundData = null;

      if (data.compareTo(node.getData()) < 0) { //Go down left
        if (node.getLeft() != null) { //Left exists
          foundData = remove(node.getLeft(), data);
        }
      } else if (data.compareTo(node.getData()) > 0) { //Go down right
        if (node.getRight() != null) { //Right exists
          foundData = remove(node.getRight(), data);
        }
      } else { //Match -> remove

      }

      //Check for rebalancing
      if (foundData != null) {

      }

      return foundData;
    }

    /**
     * Returns the data in the tree matching the parameter passed in.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data data to get in the AVL tree
     * @return the data in the tree equal to the parameter.  Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
      if (data == null) throw new IllegalArgumentException("Cannot retrieve null data from AVL tree.");

      T foundData = subtreeContains(root, data);

      if (foundData == null) throw new NoSuchElementException("Cannot retrieve data that doesn't exist in AVL tree.");

      return foundData;
    }

    /**
     * Returns whether or not the parameter is contained within the tree.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data data to find in the AVL tree
     * @return whether or not the parameter is contained within the tree
     */
    public boolean contains(T data) {
      if (data == null) throw new IllegalArgumentException("Cannot find null data in AVL tree.");

      return (subtreeContains(root, data) != null);
    }

    /**
     * Recursive helper method for finding if a value is contained within the tree.
     *
     * @param subtree the subtree to traverse
     * @param data the data to find in the AVL tree
     * @return the data that was found in the tree. Null if the data is not contained
     */
    private T subtreeContains(AVLNode<T> node, T data) {
      T found = null; //Base case: false

      if (data.equals(node.getData())) { //Base case: match
        found = node.getData();
      } else if (node.getLeft() != null && data.compareTo(node.getData()) < 0) { //Left subtree
        found = subtreeContains(node.getLeft(), data);
      } else if (node.getRight() != null && data.compareTo(node.getData()) > 0) { //Right subtree
        found subtreeContains(node.getRight(), data);
      }

      return found;
    }

    /**
     * Get the number of elements in the tree.
     *
     * @return the number of elements in the tree
     */
    public int size() {
        return size;
    }

    /**
     * Get the preorder traversal of the tree.
     *
     * @return a preorder traversal of the tree, or an empty list
     */
    public List<T> preorder() {
      List<T> list = new LinkedList<T>(); //List to add traversal to
      return preorderHelper(root, list);
    }

    /**
      * Recursive helper method for preorder traversal of tree.
      *
      * @param node the node to preorder
      * @param list the list to add the traversal to
      */
    private List<T> preorderHelper(AVLNode<T> node, List<T> list) {
      if (node != null) { //If node is not null
        list.add(node.getData()); //Base case: add root

        //Left child: recursive case
        if (node.getLeft() != null) {
          preorderHelper(node.getLeft(), list);
        }

        //Right child: recursive case
        if (node.getRight() != null) {
          preorderHelper(node.getRight(), list)
        }
      }

      return list; //return preordered list
    }

    /**
     * Get the postorder traversal of the tree.
     *
     * @return a postorder traversal of the tree, or an empty list
     */
    public List<T> postorder() {
      List<T> list = new LinkedList<T>(); //List to add traversal to
      return postorderHelper(root, list);
    }

    /**
      * Recursive helpther method for postoder traversal.
      *
      * @param node the node to postorder
      * @param list the list to add the traversal to
      */
    private List<T> postorderHelper(AVLNode<T> node, List<T> list) {
      if (node != null) { //If node is not null

        //Left child: recursive case
        if (node.getLeft() != null) {
          postorderHelper(node.getLeft(), list);
        }

        //Right child: recursive case
        if (node.getRight() != null) {
          postorderHelper(node.getRight(), list);
        }

        list.add(node.getData()); //Base case: add root
      }

      return list; //Return postordered list
    }

    /**
     * Get the inorder traversal of the tree.
     *
     * @return an inorder traversal of the tree, or an empty list
     */
    public List<T> inorder() {
      List<T> list = new LinkedList<T>(); //List to add traversal to
      return inorderHelper(root, list);
    }

    /**
      * Recursive helpther method for inorder traversal.
      *
      * @param node the node to inorder
      * @param list the list to add the traversal to
      */
    private List<T> inorderHelper(AVLNode<T> node, List<T> list) {
      if (node != null) { //If node is not null

        //Left child: recursive case
        if (node.getLeft() != null) {
          inorderHelper(node.getLeft(), list);
        }

        list.add(node.getData()); //Base case: add root

        //Right child: recursive case
        if (node.getRight() != null) {
          inorderHelper(node.getRight(), list);
        }
      }

      return list; //Return postordered list
    }

    /**
     * Get the level order traversal of the tree.
     *
     * @return a level order traversal of the tree, or an empty list
     */
    public List<T> levelorder() {
      Queue<AVLNode<T>> queue = new new LinkedList<AVLNode<T>>();
      List<T> list = new LinkedList<T>();

      if (root != null) queue.add(root); //Add root if it isn't null

      while (!queue.isEmpty()) {
        AVLNode<T> node = queue.poll(); //The node just removed

        //If the node wasn't null
        if (node != null) {
          list.add(node.getData()); //Add node
          queue.add(node.getLeft()); //Add left to queue
          queue.add(node.getRight()); //Add right to queue
        }
      }

      return list; //Return levelordered list
    }

    /**
     * Clear the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Return the height of the root of the tree.
     *
     * This method does not need to traverse the entire tree.
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
      return (size == 0 ? -1 : root.getHeight());
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     * DO NOT USE IT IN YOUR CODE
     * DO NOT CHANGE THIS METHOD
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT EDIT THIS METHOD!
        return root;
    }
}
