package src.test;

import java.util.ArrayList;
import java.util.Random;

public class TestSupport {

    private final int DEFAULT_UPPER_BOUND = 10_000_000;

    /**
     * Generates a list of random integers.
     * This method generates a list of random integers with a specified size.
     * Each integer in the list is a random number between 0 and default upper bound.
     */
    protected ArrayList<Integer> generateRandomList(int size) {
        return generateRandomList(size, DEFAULT_UPPER_BOUND);
    }

    /**
     * Generates a list of random integers.
     * This method generates a list of random integers with a specified size.
     * Each integer in the list is a random number between 0 and the specified bound.
     *
     * @param size the size of the list
     * @param bound the upper bound of the random number
     */
    protected ArrayList<Integer> generateRandomList(int size, int bound) {
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

}
