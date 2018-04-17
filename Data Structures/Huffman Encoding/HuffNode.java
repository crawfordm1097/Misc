/**
  * Simple interface for Huffman tree nodes.
  *
  * @author Mikayla Crawford
  */
public interface HuffNode {

  /**
    * Getter for if the node is a leaf.
    *
    * @return whether the node is a leaf
    */
  public boolean isLeaf();

  /**
    * Getter for the nodes weight. In an internal node this is the sum of the
    * weights of the children. For a leaf node, this is the frequency of the
    * character at that node.
    *
    * @return the weight of the node
    */
  public int getWeight();
}
