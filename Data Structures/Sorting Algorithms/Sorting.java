import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Implementation of various sorting algorithms (cocktail, selection, insertion, quicksort, mergesort, and LSD radix sort).
 *
 * @author Mikayla Crawford
 * @version 1.0
 */
public class Sorting {

    /**
     * Implementation of cocktail sort. It is in-place and stable.
     *
     * Has a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
      if (arr == null) throw new IllegalArgumentException("Cannot cocktail sort null array.");
      if (comparator == null) throw new IllegalArgumentException("Cannot cocktail sort using a null comparator.");

      boolean swapped = false; //Tracks if any swaps were made for optimizations
      int i = 0;

      //Loop while swaps are being made
      do {
        //Left to right bubble sort
        for (int j = 0; j < arr.length - i - 1; j++) {
          //Move larger elements up
          if (comparator.compare(arr[j], arr[j + 1]) > 0) {
            swap(arr, j, j + 1);
            swapped = true;
          }
        }

        //If a swap was made
        if (swapped) {
          swapped = false; //Update swap for this pass

          //Right to left bubble sort
          for (int j = arr.length - i - 2; j > i; j--) {
            //Move smaller elements down
            if (comparator.compare(arr[j], arr[j - 1]) < 0) {
              swap(arr, j, j - 1);
              swapped = true;
            }
          }
        }

        i++;
      } while (swapped && i < arr.length/2);
    }

    /**
     * Implementation of insertion sort. It is in-place and stable.
     *
     * Has a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
      if (arr == null) throw new IllegalArgumentException("Cannot insertion sort null array.");
      if (comparator == null) throw new IllegalArgumentException("Cannot insertion sort using a null comparator.");

      //Loops through the unsorted part of the array
      for (int i = 1; i < arr.length; i++) {
        int j = i; //Tracks the index to be sorted into

        //Loops through the sorted part of the array, moving elements up
        while (j > 0 && comparator.compare(arr[j], arr[j - 1]) < 0) {
          //Swap the values
          swap(arr, j, j - 1);

          //Decrease curr index
          j--;
        }
      }
    }

    /**
     * Implementation of selection sort. It is in-place.
     *
     * Has a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n^2)
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
      if (arr == null) throw new IllegalArgumentException("Cannot selection sort null array.");
      if (comparator == null) throw new IllegalArgumentException("Cannot selection sort using a null comparator.");

      //Loops through the unsorted part of the array
      for (int i = 0; i < arr.length; i++) {
        int minInd = i; //Tracks the index of the minimum element

        //Finds the index of the smallest element
        for (int j = i + 1; j < arr.length; j++) {
          //If current element is smaller than previous smallest
          if (comparator.compare(arr[j], arr[minInd]) < 0) minInd = j;
        }

        //Swaps the smallest element and the last element of the unsorted array
        swap(arr, minInd, i);
      }
    }

    /**
     * Implementation of quick sort using a random pivot. It is in-place.
     *
     * Has a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * @throws IllegalArgumentException if the array or comparator or rand is
     * null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator, Random rand) {
      if (arr == null) throw new IllegalArgumentException("Cannot quicksort null array.");
      if (comparator == null) throw new IllegalArgumentException("Cannot quicksort using a null comparator.");
      if (rand == null) throw new IllegalArgumentException("Cannot quicksort using a null random.");

      quicksortHelper(arr, 0, arr.length - 1, comparator, rand);
    }

    /**
      * Recursive helper method for quicksort.
      *
      * @param <T> data type to sort
      * @param arr the whole array to be sorted
      * @param startIndex the first index to look at
      * @param endIndex the last index to look at
      * @param comparator the Comparator used to compare the data in arr
      * @param rand the Random object used to select pivots
      */
    private static <T> void quicksortHelper(T[] arr, int startIndex, int endIndex, Comparator<T> comparator, Random rand) {
      int pivot = rand.nextInt(endIndex - startIndex) + startIndex; //A random pivot
      int left = startIndex; //Tracks the left (beginning) index
      int right = endIndex; //Tracks the right (end) index

      //Swaps pivot and end (needed since you don't know how many values are less than the pivot)
      T temp = arr[pivot]; //Temp value
      arr[pivot] = arr[endIndex]; //Updates middle
      arr[endIndex] = temp; //Updates end -> now holds pivot
      right--; //Move inward

      //Partitions array around the pivot (partitioning is complete when both indeces meet)
      while (left <= right) {
        //Finds index of out of place left values
        while (comparator.compare(arr[left], arr[endIndex]) < 0) left++;

        //Finds index of out of place right values
        while (left <= right && comparator.compare(arr[right], arr[endIndex]) >= 0) right--;

        //Swap elements
        if (right > left) swap(arr, left, right);
      }

      //Moves the pivot to the beginning of the right partition --> left holds this index
      swap(arr, left, endIndex);

      //Recursive call
      if (left - startIndex > 1) quicksortHelper(arr, startIndex, left - 1, comparator, rand); //Sorts left partition
      if (endIndex - left > 1) quicksortHelper(arr, left + 1, endIndex, comparator, rand); //Sorts right partition
    }

    /**
     * Implementation of merge sort. It is stable.
     *
     * Has a worst case running time of:
     *  O(n log n)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
      if (arr == null) throw new IllegalArgumentException("Cannot merge sort null array.");
      if (comparator == null) throw new IllegalArgumentException("Cannot merge sort using a null comparator.");

      //If the list contains more than 1 item
      if (arr.length > 1) {
        int end = arr.length; //The last index
        int center = end/2; //The center index
        T[] leftList = (T[]) new Object[center]; //The left sublist
        T[] rightList = (T[]) new Object[end - center]; //The right sublist

        //Copies elements over to left sublist
        for (int i = 0; i < center; i++) {
          leftList[i] = arr[i];
        }

        //Copies elements over to right sublist
        for (int i = center; i < end; i++) {
          rightList[i - center] = arr[i];
        }

        //Sort sublists
        mergeSort(leftList, comparator);
        mergeSort(rightList, comparator);

        int left = 0; //Right sublist index tracker
        int right = 0; //Left sublist index tracker
        int ind = 0; //List index tracker

        //While theres still elements in both sublists
        while (left < center && right < (end - center)) {
          if (comparator.compare(leftList[left], rightList[right]) < 0) { //Left list is smaller
            arr[ind] = leftList[left]; //Add element
            ind++;
            left++;
          } else { //Right list is smaller
            arr[ind] = rightList[right]; //Add element
            ind++;
            right++;
          }
        }

        //If theres remaining elements in left sublist
        while (left < center) {
          arr[ind] = leftList[left]; //Add element
          ind++;
          left++;
        }

        //If theres remaining elements in right sublist
        while (right < (end - center)) {
          arr[ind] = rightList[right]; //Add element
          ind++;
          right++;
        }
      }
    }

    /**
     * Implementation of LSD (least significant digit) radix sort. It is stable.
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     * @return the sorted array
     */
    public static int[] lsdRadixSort(int[] arr) {
      if (arr == null) throw new IllegalArgumentException("Cannot LSD radix sort null array.");

      int[] sortedArr = arr; //The sorted array
      LinkedList<Integer>[] bucket = (LinkedList<Integer>[]) new LinkedList[10];
      boolean sorted = false; //Tracks if the array is sorted
      int digitsPlace = 1; //Tracks which digit is currently being sorted

      //Initializes bucket
      for (int i = 0; i < bucket.length; i++) {
        bucket[i] = new LinkedList<Integer>();
      }

      while (!sorted) {
        sorted = true; //Base case, sorted

        //Sorts by each successive LSD
        for (int i = 0; i < sortedArr.length; i++) {
          int digit = (sortedArr[i] / digitsPlace) % 10; //Each specific LSD
          bucket[digit].add(sortedArr[i]);
        }


        int j = 0; //Tracks position in array

        //Moves sorted/partially sorted elements back
        for (int i = 0; i < bucket.length; i++) {
          //Move all contents of current bucket
          while (!bucket[i].isEmpty()) {
            sortedArr[j] = bucket[i].remove();
            j++;
          }
        }

        //Checks for unsorted elements
        for (int i = 0; i < sortedArr.length - 1 && sorted; i++) {
          //If first element is greater than next
          if (sortedArr[i] > sortedArr[i + 1]) sorted = false;
        }

        digitsPlace++; //Increases digits place
      }

      return sortedArr;
    }


    /**
      * This function swaps two values in an array.
      *
      * @param <T> data type to swap
      * @param arr the array to swap values in
      * @param i, j the indeces to swap
      */
    private static <T> void swap(T[] arr, int i, int j) {
      T temp = arr[i]; //Temp for arr[i]
      arr[i] = arr[j]; //Update arr[i]
      arr[j] = temp; //Update arr[j]
    }
}
