package src.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import src.main.LinearSpacePerfectHashing;
import src.main.QuadraticSpaceHashTable;

public class TestSupport {

    private final static int DEFAULT_UPPER_BOUND = 10_000_000;

    /**
     * Generates a list of random integers.
     * This method generates a list of random integers with a specified size.
     * Each integer in the list is a random number between 0 and default upper bound.
     */
    protected static ArrayList<Integer> generateRandomList(int size) {
        // return generateRandomList(size, DEFAULT_UPPER_BOUND);
        return generateRandomList(size,1000*size);
    }

    /**
     * Generates a list of random integers.
     * This method generates a list of random integers with a specified size.
     * Each integer in the list is a random number between 0 and the specified bound.
     *
     * @param size the size of the list
     * @param bound the upper bound of the random number
     */
    protected static ArrayList<Integer> generateRandomList(int size, int bound) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(new Random().nextInt(bound));
        }
        return list;
    }

     /**
     * Generates a list of unique random integers.
     * This method generates a list of random integers with a specified size.
     * Each integer in the list is a random number between 0 and default upper bound.
     */
    protected ArrayList<Integer> generateUniqueRandomList(int size) {
        return generateUniqueRandomList(size, DEFAULT_UPPER_BOUND);
    }


    /**
     * Generates a list unique of random integers.
     * This method generates a list of random integers with a specified size.
     * Each integer in the list is a random number between 0 and the specified bound.
     *
     * @param size the size of the list
     * @param bound the upper bound of the random number
     */
    protected ArrayList<Integer> generateUniqueRandomList(int size, int bound) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int random = new Random().nextInt(bound);
            while (list.contains(random)) {
                random = new Random().nextInt(bound);
            }
            list.add(random);
        }
        return list;
    }

    protected ArrayList<String> getListOfStrings() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("World");
        list.add("Java");
        list.add("Programming");
        list.add("Language");
        list.add("Data");
        list.add("Structure");
        list.add("Algorithm");
        list.add("Computer");
        list.add("Engineering");
        return list;
    }
    
        public static List<Double> calculateMeanInsertionTime(String hashingType, int[] sizes, int iterations) {
            //  Random random = new Random();
             System.out.println("Testing " + hashingType );
             List<Double> meanTimes = new ArrayList<>();

             for (int size : sizes) {
                 System.out.println("Testing array size " + size + "...");
                 long totalTime = 0;
                 for (int i = 0; i < iterations; i++) {
                     System.out.println("Iteration " + (i + 1) + "...");
                    ArrayList<Integer> randoms = generateRandomList(size);
                     Integer[] array = randoms.toArray(new Integer[randoms.size()]);
                    //  if(size==500)
                     System.out.println(randoms);

                     long startTime = System.nanoTime();
                     switch (hashingType) {
                        case "LinearSpacePerfectHashing":
                             LinearSpacePerfectHashing<Integer> perfectHashing = new LinearSpacePerfectHashing<Integer>();
                             for (Integer key : array) {
                                 perfectHashing.insert(key);
                             }
                        break;
                        case "QuadraticSpaceHashTable":
                             QuadraticSpaceHashTable<Integer> hashTable = new QuadraticSpaceHashTable<>(size+1);
                             for (Integer key : array) {
                                 hashTable.insert(key);
                             }
                        break;

                         
                     }
                     long endTime = System.nanoTime();
                     totalTime += (endTime - startTime);
                 }
                 double meanTime = (double) totalTime / iterations / 1_000_000; // Convert nanoseconds to milliseconds
                 meanTimes.add(meanTime);
             }
             return meanTimes;
         }
    public static void main(String[] args) {
        // int[] sizes = { 10, 50, 100, 500, 1000, 5000, 10000};
        int[] sizes = { 10, 50, 100};
        int iterations = 30;
        System.out.println("Calculating mean insertion times for linear/quadradic hashing...");
        List<Double> linearSpaceMeanTimes = calculateMeanInsertionTime("LinearSpacePerfectHashing", sizes, iterations);
        // List<Double> quadraticSpaceMeanTimes = calculateMeanInsertionTime("QuadraticSpaceHashTable", sizes, iterations);
        System.out.println("Array Size\tLinear Hashing\tQuadratic Hashing");
        for (int i = 0; i < sizes.length; i++) {
            // System.out.printf("%d\t\t%.3f\t\t%.3f\t\t\n", sizes[i], linearSpaceMeanTimes.get(i), quadraticSpaceMeanTimes.get(i));
            System.out.printf("%d\t\t\t%.3f\t\t\n", sizes[i], linearSpaceMeanTimes.get(i));
            // System.out.printf("%d\t\t\t%.3f\t\t\n", sizes[i],quadraticSpaceMeanTimes.get(i));
        }
      
    }
}
