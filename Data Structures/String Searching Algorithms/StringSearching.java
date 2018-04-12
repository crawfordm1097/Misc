import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Implementations of various string searching algorithms.
 *
 * @author Mikayla Crawford
 * @version 1.0
 */
public class StringSearching {

    /**
     * Knuth-Morris-Pratt (KMP) algorithm that relies on the failure table (also
     * called failure function). Works better with small alphabets.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text is null
     * @param pattern the pattern you are searching for in a body of text
     * @param text the body of text where you search for pattern
     * @return list containing the starting index for each match found
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text) {
      if (pattern == null || pattern.length() == 0) throw new IllegalArgumentException("Cannot search with null pattern or empty text.");
      if (text == null) throw new IllegalArgumentException("Cannot search null text.");

      List<Integer> list = new LinkedList<Integer>();

      if (text.length() >= pattern.length()) {
        int[] failureTable = buildFailureTable(pattern);
        int pIndex = 0; //Tracks index in pattern
        boolean endEarly = false;

        //Loop through text to find matching patterns
        int i = 0;
        while (i < text.length() && !endEarly) {
          if (text.charAt(i) == pattern.charAt(pIndex)) { //Match
            if (pIndex == pattern.length() - 1) { //Pattern found
              list.add(i - pIndex); //Add starting index for match
              pIndex = failureTable[pIndex]; //Go back to check for relaps
            } else { //Pattern not found yet
              pIndex++; //Increment pattern
            }
          } else if (pIndex != 0) { //Mismatch w previous char
            pIndex = failureTable[pIndex - 1];
            i--; //Used to stay at the same text char
          }

          i++;
          endEarly = (text.length() - i < pattern.length() - pIndex);
        }
      }

      return list;
    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     *
     * Note that a given index i will be the largest prefix of the pattern
     * indices [0..i] that is also a suffix of the pattern indices [1..i].
     * This means that index 0 of the returned table will always be equal to 0
     *
     * Ex. ababac
     *
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     *
     * If the pattern is empty, returns an empty array.
     *
     * @throws IllegalArgumentException if the pattern is null
     * @param pattern a {@code CharSequence} you're building a failure table for
     * @return integer array holding your failure table
     */
    public static int[] buildFailureTable(CharSequence pattern) {
      if (pattern == null) throw new IllegalArgumentException("Cannot build table based on null pattern.");
      int[] table = new int[pattern.length()];

      if (pattern.length() > 1) {
        int i = 0;
        int j = 1;
        char ic = pattern.charAt(i);
        char jc = pattern.charAt(j);

        //Loop through pattern while j is still valid
        while (j < pattern.length()) {
          if (ic == jc) { //Match
            table[j] = i + 1;
            i++;
            j++;

            ic = pattern.charAt(i);
            if (j < pattern.length()) jc = pattern.charAt(j);
          } else if (i != 0) { //Mismatch -> i is 0
            i = table[i - 1];
            ic = pattern.charAt(i);
          } else { //Mismatch -> i is not 0
            table[j] = i;
            j++;
            if (j < pattern.length()) jc = pattern.charAt(j);
          }
        }
      }


      return table;
    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text is null
     * @param pattern the pattern you are searching for in a body of text
     * @param text the body of text where you search for the pattern
     * @return list containing the starting index for each match found
     */
    public static List<Integer> boyerMoore(CharSequence pattern, CharSequence text) {
      if (pattern == null || pattern.length() == 0) throw new IllegalArgumentException("Cannot search with null pattern or empty text.");
      if (text == null) throw new IllegalArgumentException("Cannot search null text.");

      List<Integer> list = new LinkedList<Integer>();

      if (text.length() >= pattern.length()) {
        Map<Character, Integer> lastOcc = buildLastTable(pattern);
        int shift;

        //Loop through the text with increments from lastOcc
        for (int i = 0; i <= text.length() - pattern.length(); i+= shift) {
          int j = pattern.length() - 1; //The last index in the pattern
          boolean unequal = false;

          shift = 1; //Reset shift

          //Look for match
          while (!unequal) {
            char jc = pattern.charAt(j);
            char ic = text.charAt(i + j);

            if (ic != jc) { //Mismatch -> shift up and reset
              //If ic in pattern, shift j - lastOcc
              //If ic not in pattern, shift j - 1
              //AVOID NEGATIVE SKIPS
              shift = Math.max(1, j - (lastOcc.get(ic) == null ? -1 : lastOcc.get(ic)));

              unequal = true;
            } else if (j == 0) { //Full match -> add and reset
              list.add(i);
              unequal = true;
            }

            j--;
          }
        }
      }

      return list;
    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     *
     * Ex. octocat
     *
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which is interpreted in Boyer-Moore
     * as -1
     *
     * If the pattern is empty, returns an empty map.
     *
     * @throws IllegalArgumentException if the pattern is null
     * @param pattern a {@code CharSequence} you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     *         to their last occurrence in the pattern
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
      if (pattern == null) throw new IllegalArgumentException("Cannot search with null pattern.");

      Map<Character, Integer> map = new HashMap<Character, Integer>();

      for (int i = 0; i < pattern.length(); i++) {
        map.put(pattern.charAt(i), i);
      }

      return map;
    }

    /**
     * Prime base used for Rabin-Karp hashing.
     */
    private static final int BASE = 433;

    /**
     * Runs Rabin-Karp algorithm. Generate the pattern hash, and compare it with
     * the hash from a substring of text that's the same length as the pattern.
     * If the two hashes match, compare their individual characters, else update
     * the text hash and continue.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text is null
     * @param pattern a string you're searching for in a body of text
     * @param text the body of text where you search for pattern
     * @return list containing the starting index for each match found
     */
    public static List<Integer> rabinKarp(CharSequence pattern, CharSequence text) {
      if (pattern == null || pattern.length() == 0) throw new IllegalArgumentException("Cannot search with null pattern or empty text.");
      if (text == null) throw new IllegalArgumentException("Cannot search null text.");

      List<Integer> list = new LinkedList<Integer>();

      if (text.length() >= pattern.length()) {
        int patternHash = generateHash(pattern, pattern.length());
        int textHash = generateHash(text, pattern.length());
        int i = 0;
        while (i <= text.length() - pattern.length()) {
          if (textHash == patternHash) { //Hash match -> check characters
            int j = 0; //Pattern index

            //Checks characters
            while ((i + j) < text.length() && j < pattern.length() && text.charAt(i + j) == pattern.charAt(j)) {
              j++;
            }

            if (j == pattern.length()) {
              list.add(i);
            }
          }

          i++;
          if (i <= text.length() - pattern.length()) {
            textHash = updateHash(textHash, pattern.length(), text.charAt(i - 1), text.charAt(i + pattern.length() - 1));
          }
        }
      }

      return list;
    }

    /**
     * Hash function used for Rabin-Karp. The formula for hashing a string is:
     *
     * sum of: c * BASE ^ (pattern.length - 1 - i), where c is the integer
     * value of the current character, and i is the index of the character
     *
     * For example: Hashing "bunn" as a substring of "bunny" with base 433 hash
     * = b * 433 ^ 3 + u * 433 ^ 2 + n * 433 ^ 1 + n * 433 ^ 0 = 98 * 433 ^ 3 +
     * 117 * 433 ^ 2 + 110 * 433 ^ 1 + 110 * 433 ^ 0 = 7977892179
     *
     * However, note that that will roll over to -612042413, because the largest
     * number that can be represented by an int is 2147483647.
     *
     * @throws IllegalArgumentException if current is null
     * @throws IllegalArgumentException if length is negative or greater
     *     than the length of current
     * @param current substring you are generating hash function for
     * @param length the length of the string you want to generate the hash for,
     * starting from index 0. For example, if length is 4 but current's length
     * is 6, then you include indices 0-3 in your hash (and pretend the actual
     * length is 4)
     * @return hash of the substring
     */
    public static int generateHash(CharSequence current, int length) {
      if (current == null) throw new IllegalArgumentException("Cannot generate hash with null CharSqeuence");
      if (length < 0 || length > current.length()) throw new IllegalArgumentException("Cannot generate hash with invalid length.");

      int sum = 0;

      //Sums hashes
      for (int i = 0; i < length; i++) {
        sum += current.charAt(i) * pow(BASE, length - 1 - i);
      }

      return sum; //Return hash total
    }

    /**
     * Updates a hash in constant time to avoid constantly recalculating
     * entire hash. To update the hash:
     *
     *  remove the oldChar times BASE raised to the length - 1, multiply by
     *  BASE, and add the newChar.
     *
     * For example: Shifting from "bunn" to "unny" in "bunny" with base 433
     * hash("unny") = (hash("bunn") - b * 433 ^ 3) * 433 + y * 433 ^ 0 =
     * (3764054547 - 98 * 433 ^ 3) * 433 + 121 * 433 ^ 0 = 9519051770
     *
     * However, the number will roll over to 929117178.
     *
     * The computation of BASE raised to length - 1 may require O(log n) time,
     * but the method should otherwise run in O(1).
     *
     * @throws IllegalArgumentException if length is negative
     * @param oldHash hash generated by generateHash
     * @param length length of pattern/substring of text
     * @param oldChar character we want to remove from hashed substring
     * @param newChar character we want to add to hashed substring
     * @return updated hash of this substring
     */
    public static int updateHash(int oldHash, int length, char oldChar, char newChar) {
      if (length < 0) throw new IllegalArgumentException("Cannot update hash with negative length.");

      return (oldHash - oldChar * pow(BASE, length - 1)) * BASE + newChar * pow(BASE, 0);
    }

    /**
     * Calculate the result of a number raised to a power.
     *
     * @throws IllegalArgumentException if both {@code base} and {@code exp} are
     * 0
     * @throws IllegalArgumentException if {@code exp} is negative
     * @param base base of the number
     * @param exp power to raise the base to. Must be 0 or greater.
     * @return result of the base raised to that power
     */
    private static int pow(int base, int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("Exponent cannot be negative.");
        } else if (base == 0 && exp == 0) {
            throw new IllegalArgumentException(
                    "Both base and exponent cannot be 0.");
        } else if (exp == 0) {
            return 1;
        } else if (exp == 1) {
            return base;
        }
        int halfPow = pow(base, exp / 2);
        if (exp % 2 == 0) {
            return halfPow * halfPow;
        } else {
            return halfPow * pow(base, (exp / 2) + 1);
        }
    }
}
