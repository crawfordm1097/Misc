/**
  * This class describes a huffman tree.
  *
  * @author Mikayla Crawford
  */
public class HuffTree implements Comparable {
  private HuffNode root;

  /**
    * Default constructor. Uses constructor chaining.
    */
  HuffTree() {
    this(null);
  }

  /**
    * Constructor for a tree with the given root.
    *
    * @param root the root of the tree
    */
  HuffTree(HuffNode root) {
    this.root = root;
  }

  /**
    * Getter for the tree's root.
    *
    * @return the trees root
    */
  public HuffNode getRoot() {
    return root;
  }

  /**
    * Returns a boolean for whether the tree is empty or not.
    *
    * @return whether the tree is empty
    */
  public boolean isEmpty() {
    return (root == null);
  }

  /**
    * Returns the weight of the tree (the weight of the root).
    *
    * @return the weight of the tree
    */
  public int getWeight() {
    return this.root.getWeight();
  }

  @Override
  public int compareTo(Object o) {
    HuffTree tree = (HuffTree) o;

    return root.getWeight() - tree.getWeight();
  }

}
