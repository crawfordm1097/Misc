/**
  * This class implements {@code HuffNode} to create a leaf node for a Huffman
  * tree.
  *
  * @author Mikayla Crawford
  */
public class HuffLeaf implements HuffNode {
  private int freq;
  private char ch;

  /**
    * Constructor for a leaf node for the specific character and frequency.
    *
    * @param ch the characer at the node
    * @param freq the frequency of the character
    */
  HuffLeaf(char ch, int freq) {
    this.ch = ch;
    this.freq = freq;
  }

  @Override
  public boolean isLeaf() {
    return true;
  }

  @Override
  public int getWeight() {
    return freq;
  }

  /**
    * Getter for the character at the node.
    *
    * @return the character
    */
  public char getChar() {
    return ch;
  }

}
