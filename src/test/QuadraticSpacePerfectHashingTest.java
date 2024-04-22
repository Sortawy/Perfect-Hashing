package src.test;

import org.junit.Test;
import src.main.QuadraticSpaceHashTable;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class QuadraticSpacePerfectHashingTest extends TestSupport {


    /**
     * This test case is designed to verify the size of the hash table.
     * The size of the hash table should be the square of the input size.
     *
     */
    @Test
    public void testTableSize() {
        for (int i = 1; i < 100; i ++) {
            QuadraticSpaceHashTable<Integer> hashTable = new QuadraticSpaceHashTable<>(i);
            Object[] table = hashTable.getHashTable();
            assertEquals(i * i, table.length);
        }
    }

    @Test
    public void testInsertWithIntegers() {
        QuadraticSpaceHashTable<Integer> hashTable = new QuadraticSpaceHashTable<>(1000);
        ArrayList<Integer> list = generateUniqueRandomList(500);
        for (Integer i : list) {
            hashTable.insert(i);
        }
        assertEquals(500, hashTable.getCount());
    }

    @Test
    public void testInsertWithStrings() {
        QuadraticSpaceHashTable<String> hashTable = new QuadraticSpaceHashTable<>(10);
        ArrayList<String> list = getListOfStrings();
        for (String i : list) {
            hashTable.insert(i);
        }
        assertEquals(list.size(), hashTable.getCount());
    }

    @Test
    public void testInsertWhenHashTableIsFull() {
        QuadraticSpaceHashTable<Integer> hashTable = new QuadraticSpaceHashTable<>(10);
        ArrayList<Integer> list = generateUniqueRandomList(10,100);
        for (Integer i : list) {
            hashTable.insert(i);
        }
        assertThrows(IllegalStateException.class, () -> hashTable.insert(101));
    }

    @Test
    public void testIgnoreDuplicatesWithIntegers() {
        QuadraticSpaceHashTable<Integer> hashTable = new QuadraticSpaceHashTable<>(1000);
        ArrayList<Integer> list = generateUniqueRandomList(500);
        for (Integer i : list) {
            hashTable.insert(i);
            hashTable.insert(i);
            hashTable.insert(i);
        }
        assertEquals(500, hashTable.getCount());
    }

    @Test
    public void testIgnoreDuplicatesWithStrings() {
        QuadraticSpaceHashTable<String> hashTable = new QuadraticSpaceHashTable<>(1000);
        ArrayList<String> list = getListOfStrings();
        for (String i : list) {
            hashTable.insert(i);
            hashTable.insert(i);
            hashTable.insert(i);
        }
        assertEquals(list.size(), hashTable.getCount());
    }

    @Test
    public void testLookupWhenKeyExistsWithIntegers() {
        QuadraticSpaceHashTable<Integer> hashTable = new QuadraticSpaceHashTable<>(1000);
        ArrayList<Integer> list = generateRandomList(500);
        for (Integer i : list) {
            hashTable.insert(i);
        }
        for (Integer i : list) {
            assertTrue(hashTable.contains(i));
        }
    }

    @Test
    public void testLookupWhenKeyExistsWithStrings() {
        QuadraticSpaceHashTable<String> hashTable = new QuadraticSpaceHashTable<>(1000);
        ArrayList<String> list = getListOfStrings();
        for (String i : list) {
            hashTable.insert(i);
        }
        for (String i : list) {
            assertTrue(hashTable.contains(i));
        }
    }

    @Test
    public void testLookupWhenKeyDoesNotExistWithIntegers() {
        QuadraticSpaceHashTable<Integer> hashTable = new QuadraticSpaceHashTable<>(1000);
        ArrayList<Integer> list = generateRandomList(500,500);
        for (Integer i : list) {
            hashTable.insert(i);
        }
        for (int i = 501; i < 10_000; i++) {
            assertFalse(hashTable.contains(i));
        }
    }

    @Test
    public void testLookupWhenKeyDoesNotExistStrings() {
        QuadraticSpaceHashTable<String> hashTable = new QuadraticSpaceHashTable<>(1000);
        ArrayList<String> list = getListOfStrings();
        for (String i : list) {
            hashTable.insert(i);
        }
        assertFalse(hashTable.contains("This is a test"));
        assertFalse(hashTable.contains("hi"));
        assertFalse(hashTable.contains("TTest"));
    }

    @Test
    public void testDeleteWithIntegers() {
        QuadraticSpaceHashTable<Integer> hashTable = new QuadraticSpaceHashTable<>(1000);
        ArrayList<Integer> list = generateRandomList(500);
        for (Integer i : list) {
            hashTable.insert(i);
        }
        for (Integer i : list) {
            hashTable.delete(i);
        }
        assertEquals(0, hashTable.getCount());
    }

    @Test
    public void testDeleteWithStrings() {
        QuadraticSpaceHashTable<String> hashTable = new QuadraticSpaceHashTable<>(1000);
        ArrayList<String> list = getListOfStrings();
        for (String i : list) {
            hashTable.insert(i);
        }
        for (String i : list) {
            hashTable.delete(i);
        }
        assertEquals(0, hashTable.getCount());
    }

    @Test
    public void testDeleteWhenKeyDoesNotExistsWithIntegers() {
        QuadraticSpaceHashTable<Integer> hashTable = new QuadraticSpaceHashTable<>(1000);
        ArrayList<Integer> list = generateUniqueRandomList(500,500);
        for (Integer i : list) {
            hashTable.insert(i);
        }
        hashTable.delete(501);
        hashTable.delete(502);
        hashTable.delete(503);
        assertEquals(list.size(), hashTable.getCount());
    }

    @Test
    public void testDeleteWhenKeyDoesNotExistsWithStrings() {
        QuadraticSpaceHashTable<String> hashTable = new QuadraticSpaceHashTable<>(1000);
        ArrayList<String> list = getListOfStrings();
        for (String i : list) {
            hashTable.insert(i);
        }
        hashTable.delete("This is a test");
        hashTable.delete("hi");
        hashTable.delete("TTest");
        assertEquals(list.size(), hashTable.getCount());
    }

    @Test
    public void testNumberOfTimesNeededToRebuildHashtable() {
        int totalNumberOfCollisions = 0;
        int trails = 1000;
        for (int i = 0; i < trails; i++) {
            QuadraticSpaceHashTable<Integer> hashTable = new QuadraticSpaceHashTable<>(1000);
            ArrayList<Integer> list = generateRandomList(500);
            for (Integer j : list) {
                hashTable.insert(j);
            }
            totalNumberOfCollisions += hashTable.getNumberOfCollisions();
        }
        double averageNumberOfCollisions = 1.0 * totalNumberOfCollisions / trails;
        assertTrue(averageNumberOfCollisions <= 2);
    }

}
