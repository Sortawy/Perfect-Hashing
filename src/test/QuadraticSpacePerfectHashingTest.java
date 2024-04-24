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
        assertEquals(500, hashTable.getNumberOfItems());
    }

    @Test
    public void testInsertWithStrings() {
        QuadraticSpaceHashTable<String> hashTable = new QuadraticSpaceHashTable<>(10);
        ArrayList<String> list = getListOfStrings();
        for (String i : list) {
            hashTable.insert(i);
        }
        assertEquals(list.size(), hashTable.getNumberOfItems());
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
        assertEquals(500, hashTable.getNumberOfItems());
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
        assertEquals(list.size(), hashTable.getNumberOfItems());
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
        assertEquals(0, hashTable.getNumberOfItems());
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
        assertEquals(0, hashTable.getNumberOfItems());
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
        assertEquals(list.size(), hashTable.getNumberOfItems());
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
        assertEquals(list.size(), hashTable.getNumberOfItems());
    }

    @Test
    public void testRehashWithIntegers() {
        QuadraticSpaceHashTable<Integer> hashTable = new QuadraticSpaceHashTable<>(10);
        ArrayList<Integer> list = generateUniqueRandomList(10);
        for (Integer i : list) {
            hashTable.insert(i);
        }
        hashTable.rehash(100);
        for (Integer i : list) {
            assertTrue(hashTable.contains(i));
        }
        assertEquals(hashTable.getNumberOfItems(), list.size());
    }

    @Test
    public void testRehashWithStrings() {
        QuadraticSpaceHashTable<String> hashTable = new QuadraticSpaceHashTable<>(10);
        ArrayList<String> list = getListOfStrings();
        for (String i : list) {
            hashTable.insert(i);
        }
        hashTable.rehash(100);
        for (String i : list) {
            assertTrue(hashTable.contains(i));
        }
        assertEquals(hashTable.getNumberOfItems(), list.size());
    }

    @Test
    public void testRehashAfterSomeDeleteThenInsert() {
        QuadraticSpaceHashTable<Integer> hashTable = new QuadraticSpaceHashTable<>(10);
        ArrayList<Integer> list = generateUniqueRandomList(10,10);
        for (Integer i : list) {
            hashTable.insert(i);
        }
        hashTable.delete(1);
        hashTable.delete(2);
        hashTable.delete(3);
        hashTable.rehash(100);
        hashTable.insert(1);
        for (Integer i : list) {
            if (i == 2 || i == 3) {
                assertFalse(hashTable.contains(i));
            } else {
                assertTrue(hashTable.contains(i));
            }
        }
        assertEquals(hashTable.getNumberOfItems(), 8);
    }

    @Test
    public void testInsertTillHashTableIsFullAfterRehash() {
        QuadraticSpaceHashTable<Integer> hashTable = new QuadraticSpaceHashTable<>(10);
        ArrayList<Integer> list = generateUniqueRandomList(10);
        for (Integer i : list) {
            hashTable.insert(i);
        }
        hashTable.rehash(100);
        ArrayList<Integer> list2 = generateUniqueRandomList(90);
        for (Integer i : list2) {
            hashTable.insert(i);
        }
        ArrayList<Integer> combinedLists = new ArrayList<>(list);
        combinedLists.addAll(list2);
        for (Integer i : combinedLists) {
            assertTrue(hashTable.contains(i));
        }
        assertEquals(hashTable.getNumberOfItems(), 100);
        assertTrue(hashTable.isFull());
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
