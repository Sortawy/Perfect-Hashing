package src.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import src.main.LinearSpacePerfectHashing;
import src.main.QuadraticSpaceHashTable;

public class TestSupport {

    private final static int DEFAULT_UPPER_BOUND = 10_000_000;

  
    protected static ArrayList<Integer> generateRandomList(int size) {
        // return generateRandomList(size, DEFAULT_UPPER_BOUND);
        return generateRandomList(size,1000*size);
    }

    protected static ArrayList<Integer> generateRandomList(int size, int bound) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(new Random().nextInt(bound));
        }
        return list;
    }

    protected ArrayList<Integer> generateUniqueRandomList(int size) {
        return generateUniqueRandomList(size, DEFAULT_UPPER_BOUND);
    }

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
    
        
    public static List<Double> calculateMeanSearchAndInsertionTime(String hashingType, int[] sizes, int iterations,String comparison) {
            System.out.println("Testing "+ comparison + " " + hashingType);

            List<Double> meanTimes = new ArrayList<>();

            for (int size : sizes) {
                System.out.println("Testing array size " + size + "...");
                long totalTime = 0;
                for (int i = 0; i < iterations; i++) {
                    System.out.println("Iteration " + (i + 1) + "...");
                   ArrayList<Integer> randoms = generateRandomList(size);
                    Integer[] array = randoms.toArray(new Integer[randoms.size()]);
                   //  if(size==500)
                   //  System.out.println(randoms);

                    long startTime = System.nanoTime();
                    switch (hashingType) {
                       case "LinearSpacePerfectHashing":
                            LinearSpacePerfectHashing<Integer> perfectHashing = new LinearSpacePerfectHashing<Integer>(array);
                            // for (Integer key : array) {
                            //     perfectHashing.insert(key);
                            // }
                            if(comparison.equals("search")){
                                startTime = System.nanoTime();
                             for (Integer key : array) {
                                perfectHashing.contains(key);
                                }
                            }
                       break;
                       case "QuadraticSpaceHashTable":
                            QuadraticSpaceHashTable<Integer> hashTable = new QuadraticSpaceHashTable<>(size+1);
                            for (Integer key : array) {
                                hashTable.insert(key);
                            }
                            if(comparison.equals("search")){
                                startTime = System.nanoTime();
                             for (Integer key : array) {
                                hashTable.contains(key);
                             }
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
    
    public static List<Double> calculateCollisions(String hashingType, int[] sizes, int iterations) {
        System.out.println("Testing collisions for " + hashingType);

        List<Double> meanCollisions = new ArrayList<>();

        for (int size : sizes) {
            System.out.println("Testing array size " + size + "...");
            long totalCollisions = 0;
            for (int i = 0; i < iterations; i++) {
                System.out.println("Iteration " + (i + 1) + "...");
                ArrayList<Integer> randoms = generateRandomList(size);
                Integer[] array = randoms.toArray(new Integer[randoms.size()]);
                long collisions = 0;
                switch (hashingType) {
                    case "LinearSpacePerfectHashing":
                        LinearSpacePerfectHashing<Integer> perfectHashing = new LinearSpacePerfectHashing<Integer>();
                        for (Integer key : array) {
                            perfectHashing.insert(key);
                        }
                        collisions = perfectHashing.getNumberOfCollisions();
                        break;
                    case "QuadraticSpaceHashTable":
                        QuadraticSpaceHashTable<Integer> hashTable = new QuadraticSpaceHashTable<>(size+1);
                        for (Integer key : array) {
                            hashTable.insert(key);
                        }
                        collisions = hashTable.getNumberOfCollisions();
                        break;
                }
                totalCollisions += collisions;
            }
            double meanCollision = (double) totalCollisions / iterations;
            meanCollisions.add(meanCollision);
        }
        return meanCollisions;
    }
     public static void main(String[] args) {
        int[] sizes = { 10, 50, 100, 500, 1000, 5000, 10000};
        // int[] sizes = {10, 50, 100,250};
        int iterations = 10;

        System.out.println("Calculating mean insertion times for linear/quadradic hashing...");
        List<Double> linearSpaceMeanTimes = calculateMeanSearchAndInsertionTime("LinearSpacePerfectHashing", sizes, iterations,"x");
        List<Double> quadraticSpaceMeanTimes = calculateMeanSearchAndInsertionTime("QuadraticSpaceHashTable", sizes, iterations,"x");
        System.out.println("Array Size\tLinear Hashing\tQuadratic Hashing");
        for (int i = 0; i < sizes.length; i++) {
            System.out.printf("%d\t\t%.3f\t\t%.3f\t\t\n", sizes[i], linearSpaceMeanTimes.get(i), quadraticSpaceMeanTimes.get(i));
            // System.out.printf("%d\t\t\t%.3f\t\t\n", sizes[i], linearSpaceMeanTimes.get(i));
            // System.out.printf("%d\t\t\t%.3f\t\t\n", sizes[i],quadraticSpaceMeanTimes.get(i));
        }




    //    System.out.println("Calculating Collisions for linear/quadradic hashing...");
    //     List<Double> linearSpaceCollisions = calculateCollisions("LinearSpacePerfectHashing", sizes, iterations);
    //     List<Double> quadraticSpaceCollisions = calculateCollisions("QuadraticSpaceHashTable", sizes, iterations);
    //     System.out.println("Array Size\tLinear Hashing\tQuadratic Hashing");
    //     for (int i = 0; i < sizes.length; i++) {
    //         System.out.printf("%d\t\t%.3f\t\t%.3f\t\t\n", sizes[i], linearSpaceCollisions.get(i), quadraticSpaceCollisions.get(i));
    //     }
    }
}
