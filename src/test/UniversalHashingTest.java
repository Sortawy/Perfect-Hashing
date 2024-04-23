package src.test;

import org.junit.Test;
import src.main.UniversalHashing;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UniversalHashingTest extends TestSupport{


    /**
     * This test case is designed to verify the consistency of the hash function
     * in the UniversalHashing class.The hash function should always return
     * the same hash value for the same input.
     *
     */
    @Test
    public void testSameValueSameHash() {
         UniversalHashing<Integer> universalHashing = new UniversalHashing<>(10);
         ArrayList<Integer> list = generateRandomList(100);
         for (Integer i : list) {
                assertEquals(universalHashing.hash(i), universalHashing.hash(i));
         }
    }

    /**
     * This test case is designed to verify the collision probability of the hash function
     * The hash function should have a collision probability
     * that is less than or equal to 1.0 / table size.
     *
     */
    @Test
    public void testCollisionProbability() {
         UniversalHashing<Integer> universalHashing = new UniversalHashing<>(500);
         ArrayList<Integer> list = generateRandomList(10_000);
         int numberOfCollisions = 0 , trails = 10_000;
         for (int i = 0; i < trails; i++) {
             if (universalHashing.hash(list.get(i)) == universalHashing.hash(list.get(i) + 1)) {
                    numberOfCollisions++;
             }
         }
         double collisionProbability = 1.0 * numberOfCollisions / trails;
         double upperBound = 1.0 / universalHashing.getHashTableSize();
         assertEquals(collisionProbability, upperBound, 0.01);
    }

    /**
     * This test case is designed to verify the hash function
     * in the UniversalHashing class. The hash function should
     * return a value that is less than the hash table size.
     *
     */
    @Test
    public void testHashValueRange() {
         UniversalHashing<Integer> universalHashing = new UniversalHashing<>(100);
         ArrayList<Integer> list = generateRandomList(1000);
         for (Integer i : list) {
                assertTrue(universalHashing.hash(i) < universalHashing.getHashTableSize());
         }
    }

}
