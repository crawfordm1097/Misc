/**
  * This class implements {@code HuffNode} to create an internal node for a
  * Huffman tree.
  *
  * @author Mikayla Crawford
  */
public class HuffInternal implements HuffNode {
  private HuffNode left;
  private HuffNode right;
  private int weight;

  /**
    * Create an internal node with this weight.
    *
    * @param weight the weight of the node
    */
  HuffInternal(int weight) {
    this.weight = weight;
  }

  /**
    * Updates the left child of the node.
    *
    * @param left the new left child
    */
  public void setLeft(HuffNode left) {
    this.left = left;
  }

  /**
    * Updates the right child of the node.
    *
    * @param right the new left child
    */
  public void setRight(HuffNode right) {
    this.right = right;
  }

  @Override
  public boolean isLeaf() {
    return false;
  }

  @Override
  public int getWeight() {
    return weight;
  }

  /**
    * Getter for the left child.
    *
    * @return the left child
    */
  public HuffNode getLeft() {
    return left;
  }

  /**
    * Getter for the right child.
    *
    * @return the right child
    */
  public HuffNode getRight() {
    return right;
  }
}
