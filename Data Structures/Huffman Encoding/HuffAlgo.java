import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
  * This program implements huffman encoding, which is a compression technique
  * used for files.
  *
  * @author Mikayla Crawford
  */
public class HuffAlgo implements HuffmanCoding {
  private static final int ALPHASIZE = 256;

  /**
    * This function takes a file as input and creates a table with the
    * characters and their frequencies in the file. It also outputs the
    * characters and frequencies. The outputted string is in the format:
    *
    * character space frequency newline
    *
    * @param inputFile the file to read from
    * @return the string of the characters and frequencies
    */
  public String getFrequencies(File inputFile) throws FileNotFoundException {
    int[] freqTable = findFrequencies(inputFile); //Gets the frequency table
    String freqString = new String();

    //Create the string of characters and frequencies
    for (int i = 0; i < freqTable.length; i++) {
      if (freqTable[i] != 0) freqString += (char)(i) + " " + freqTable[i] + "\n";
    }

    return freqString;
  }

  /**
    * This method reads a file and outputs a table of each character and their
    * relative frequency in the file.
    *
    * @param inputFile the file to read
    * @return the table of all ASCII character (128 total) frequencies. If a
    * character wasn't contained in the file, its frequency is 0
    */
  private int[] findFrequencies(File inputFile) throws FileNotFoundException {
    int[] freqTable = new int[ALPHASIZE]; //Table of all frequencies

    //Gets the frequencies of each character
    try {
      BufferedReader reader = new BufferedReader(
        new FileReader(inputFile));
      int val;

      //Reads each character
      while ((val = reader.read()) != -1) {
        char c = (char) val; //Gets the character
        freqTable[c] += 1; //Increases relative frequency by 1
      }
    } catch (IOException ioe) {
      System.out.println("IOException findFrequencies");
    }

    return freqTable;
  }

  /**
    * This function takes a file as input and creates a {@code HuffTree} from
    * it.
    *
    * @param inputFile the file to create the tree from
    * @return the huffman tree created
    */
  public HuffTree buildTree(File inputFile) throws FileNotFoundException, Exception {
    int[] freqTable = findFrequencies(inputFile); //Finds frequencies
    PriorityQueue<HuffTree> minHeap = new PriorityQueue<HuffTree>();
    HuffTree tree = new HuffTree();

    //Create a minHeap of huffman trees with only leaf nodes
    for (int i = 0; i < freqTable.length; i++) {
      if (freqTable[i] != 0) {
        HuffNode newNode = new HuffLeaf((char)i, freqTable[i]);
        minHeap.add(new HuffTree(newNode));
      }
    }

    //Constructs the huffman tree
    while (minHeap.size() > 1) {
      //Get the least 2 trees
      HuffTree temp1 = minHeap.poll();
      HuffTree temp2 = minHeap.poll();

      //Combine to create a new tree
      HuffInternal newRoot = new HuffInternal(temp1.getWeight() + temp2.getWeight());
      newRoot.setLeft(temp1.getRoot());
      newRoot.setRight(temp2.getRoot());
      tree = new HuffTree(newRoot);

      //Add new tree
      minHeap.add(tree);
    }

    return tree;
  }

  /**
    * This function encodes the given file from the given {@ code HuffTree}. It
    * outputs a binary string (1's and 0's) representing the file.
    *
    * @param inputFile the file to encode
    * @param huffTree the huffman tree to use
    * @return the binary string representation
    */
  public String encodeFile(File inputFile, HuffTree huffTree) throws FileNotFoundException {
    String[] table = new String[ALPHASIZE];
    String encode = "";

    //Populates the lookup table
    makeTable(table, huffTree.getRoot(), "");

    try {
      BufferedReader reader = new BufferedReader(new FileReader(inputFile));
      int val;

      //Reads each character
      while ((val = reader.read()) != -1) {
        char c = (char) val; //Gets the character
        encode += table[c]; //Adds to string
      }
    } catch(IOException e) {
      e.printStackTrace();
    }

    return encode;
  }

  /**
    * Recursive helper method for creating a lookup table for a given {@code
    * HuffTree}.
    *
    * @param table the table to add the encoded value to
    * @param node the node to look at
    * @param code the current encoded string
    */
  private void makeTable(String[] table, HuffNode node, String code) {
    if (node != null) { //If node exists
      if (node.isLeaf()) { //Leaf node
        HuffLeaf leaf = (HuffLeaf)node;
        table[leaf.getChar()] = code; //Add to map
      } else { //Internal node
        HuffInternal internal = (HuffInternal) node;
        makeTable(table, internal.getLeft(), code + "0"); //Go down left
        makeTable(table, internal.getRight(), code + "1"); //Go down right
      }
    }
  }

  /**
    * This function takes a string and decodes the words based on the given
    * {@code HuffTree}.
    *
    * @param code the string to decode
    * @param huffTree the Huffman tree to use
    * @return the decoded string
    */
  public String decodeFile(String code, HuffTree huffTree) throws Exception {
    HuffNode curr = huffTree.getRoot();
    String decode = "";

    //Loop through the code and decode it
    for (int i = 0; i < code.length(); i++) {
      HuffInternal internal = (HuffInternal) curr;
      curr = (code.charAt(i) == '0') ? internal.getLeft() : internal.getRight();

      if (curr.isLeaf()) { //Leaf node
        HuffLeaf leaf = (HuffLeaf) curr;
        decode += leaf.getChar(); //Add character
        curr = huffTree.getRoot(); //Reset curr
      }
    }

    return decode;
  }

  /**
    * This function traverses the given {@code HuffTree} and creates a string
    * of the characters and their codes. The outputted string is the format:
    *
    * character space code newLine
    *
    * @param huffTree the Huffman tree to traverse
    * @return a string of the traversed data
    */
  public String traverseHuffmanTree(HuffTree huffTree) throws Exception{
    return transverseTree(huffTree.getRoot(), "");
  }

  /**
    * Recursive helper method for traversing the huffman tree and adding the
    * traversal to a string.
    *
    * @param node the node to look at
    * @param code the decoded string for the leaf node
    */
  private String transverseTree(HuffNode node, String code) throws Exception {
    String output = "";

    if (node != null) { //If node exists
      if (node.isLeaf()) { //Leaf node
        HuffLeaf leaf = (HuffLeaf) node;
        output += leaf.getChar() + " " + code + "\n"; //Add to output
      } else { //Internal node
        HuffInternal internal = (HuffInternal) node;
        output += transverseTree(internal.getLeft(), code + "1"); //Go down left
        output += transverseTree(internal.getRight(), code + "0"); //Go down right
      }
    }

    return output;
  }

}
