package src.test;

import org.junit.Test;
import src.main.Dictionary;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DictionaryTest extends TestSupport {

    @Test
    public void testInsertThenSearch() {
        Dictionary dictionary = new Dictionary("N^2");
        ArrayList<String> list = getListOfStrings();
        for (String i : list) {
            dictionary.insert(i);
        }
        for (String i : list) {
            assertTrue(dictionary.search(i));
        }
    }

    @Test
    public void testSearchAfterDeleting() {
        Dictionary dictionary = new Dictionary("N^2");
        ArrayList<String> list = getListOfStrings();
        for (String i : list) {
            dictionary.insert(i);
        }
        for (String i : list) {
            dictionary.delete(i);
        }
        for (String i : list) {
            assertFalse(dictionary.search(i));
        }
    }

    @Test
    public void testNumberOfHitsWhenInsertSuccessful() {
        Dictionary dictionary = new Dictionary("N^2");
        ArrayList<String> list = getListOfStrings();
        for (String i : list) {
            dictionary.insert(i);
            assertEquals(1, dictionary.getChangeInSize());
        }
    }

    @Test
    public void testNumberOfMissesWhenInsertFailed() {
        Dictionary dictionary = new Dictionary("N^2");
        ArrayList<String> list = getListOfStrings();
        for (String i : list) {
            dictionary.insert(i);
        }
        for (String i : list) {
            dictionary.insert(i); // duplicates so misses increase
            assertEquals(0, dictionary.getChangeInSize());
        }
    }

    @Test
    public void testNumberOfHitsWhenDeleteSuccessful() {
        Dictionary dictionary = new Dictionary("N^2");
        ArrayList<String> list = getListOfStrings();
        for (String i : list) {
            dictionary.insert(i);
        }
        for (String i : list) {
            dictionary.delete(i);
            assertEquals(-1, dictionary.getChangeInSize());
        }
    }

    @Test
    public void testNumberOfMissesWhenDeleteFails() {
        Dictionary dictionary = new Dictionary("N^2");
        ArrayList<String> list = getListOfStrings();
        for (String i : list) {
            dictionary.insert(i);
        }
        ArrayList<String> list2 = new ArrayList<>();
        list2.add("hhhh");
        list2.add("iiii");
        list2.add("jjjj");
        for (String i : list2) {
            dictionary.delete(i); // not in the dictionary so misses increase
            assertEquals(0, dictionary.getChangeInSize());
        }
    }
}
