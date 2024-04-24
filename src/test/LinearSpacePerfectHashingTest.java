package src.test;

import org.junit.Test;
import src.main.LinearSpacePerfectHashing;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class LinearSpacePerfectHashingTest extends TestSupport{
        /**
     * This test case is designed to verify the size of the hash table.
     * The size of the hash table should be the square of the input size.
     *
     */
    @Test
    public void testTableSize() {
        for (int i = 1; i < 100; i ++) {
            LinearSpacePerfectHashing<Integer> hashTable = new LinearSpacePerfectHashing<>(i);
            int[] table = hashTable.getFirstLevelHashTable();
            assertEquals(i, table.length);
        }

    }

    @Test
    public void testInsertWithIntegers() {
        LinearSpacePerfectHashing<Integer> hashTable = new LinearSpacePerfectHashing<>(1000);
        ArrayList<Integer> list = generateUniqueRandomList(500);
        for (Integer i : list) {
            hashTable.insert(i);
        }
        assertEquals(500, hashTable.getNumberOfItems());
    }

    @Test
    public void testInsertWithStrings() {
        LinearSpacePerfectHashing<String> hashTable = new LinearSpacePerfectHashing<>(10);
        ArrayList<String> list = getListOfStrings();
        for (String i : list) {
            hashTable.insert(i);
        }
        assertEquals(list.size(), hashTable.getNumberOfItems());
    }

    @Test
    public void testIgnoreDuplicatesWithIntegers() {
        LinearSpacePerfectHashing<Integer> hashTable = new LinearSpacePerfectHashing<>(1000);
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
        LinearSpacePerfectHashing<String> hashTable = new LinearSpacePerfectHashing<>(1000);
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
        LinearSpacePerfectHashing<Integer> hashTable = new LinearSpacePerfectHashing<>(1000);
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
        LinearSpacePerfectHashing<String> hashTable = new LinearSpacePerfectHashing<>(1000);
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
        LinearSpacePerfectHashing<Integer> hashTable = new LinearSpacePerfectHashing<>(1000);
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
        LinearSpacePerfectHashing<String> hashTable = new LinearSpacePerfectHashing<>(1000);
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
        LinearSpacePerfectHashing<Integer> hashTable = new LinearSpacePerfectHashing<>(1000);
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
        LinearSpacePerfectHashing<String> hashTable = new LinearSpacePerfectHashing<>(1000);
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
        LinearSpacePerfectHashing<Integer> hashTable = new LinearSpacePerfectHashing<>(1000);
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
        LinearSpacePerfectHashing<String> hashTable = new LinearSpacePerfectHashing<>(1000);
        ArrayList<String> list = getListOfStrings();
        for (String i : list) {
            hashTable.insert(i);
        }
        hashTable.delete("This is a test");
        hashTable.delete("hi");
        hashTable.delete("TTest");
        assertEquals(list.size(), hashTable.getNumberOfItems());
    }

//    @Test
//    public void testNumberOfTimesNeededToRebuildHashtable() {
//
//    }
}
