package src.test;

import org.junit.Test;
import src.main.Dictionary;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DictionaryTest extends TestSupport {

    @Test
    public void testInsertThenSearchQuadratic() {
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
    public void testSearchAfterDeletingQuadratic() {
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
    public void testNumberOfHitsWhenInsertSuccessfulQuadratic() {
        Dictionary dictionary = new Dictionary("N^2");
        ArrayList<String> list = getListOfStrings();
        for (String i : list) {
            dictionary.insert(i);
            assertEquals(1, dictionary.getChangeInSize());
        }
    }
    @Test
    public void testNumberOfHitsWhenInsertSuccessfulLinear() {
        Dictionary dictionary = new Dictionary("N^2");
        ArrayList<String> list = getListOfStrings();
        for (String i : list) {
            dictionary.insert(i);
            assertEquals(1, dictionary.getChangeInSize());
        }
    }

    @Test
    public void testNumberOfMissesWhenInsertFailedQuadratic() {
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
    public void testNumberOfHitsWhenDeleteSuccessfulQuadratic() {
        Dictionary dictionary = new Dictionary("N^2");
        ArrayList<String> list = getListOfStrings();
        for (String i : list) {
            dictionary.insert(i);
        }
        for (String i : list) {
            dictionary.delete(i);
            assertEquals(-1, dictionary.getChangeInSize());
        }
        assertEquals(0,dictionary.getCurrentNumberOfItems());
    }
    @Test
    public void testNumberOfHitsWhenDeleteSuccessfulLinear() {
        Dictionary dictionary = new Dictionary("N^2");
        ArrayList<String> list = getListOfStrings();
        for (String i : list) {
            dictionary.insert(i);
        }
        for (String i : list) {
            dictionary.delete(i);
            assertEquals(-1, dictionary.getChangeInSize());
        }
        assertEquals(0,dictionary.getCurrentNumberOfItems());
    }
    @Test
    public void testNumberOfMissesWhenDeleteFailsQuadratic() {
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
    @Test
    public void testNumberOfMissesWhenDeleteFailsLinear() {
        Dictionary dictionary = new Dictionary("N");
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
    @Test
    public void testBatchFileUnique2199NamesLinear(){
        Dictionary dict=new Dictionary("N");
        String filepath="D:\\CSE - Department\\Level 2\\Second Semester\\Data Structures and Algorithms\\Labs\\Lab 2\\Perfect-Hashing\\src\\input_files\\2199-names.txt";
        dict.insert("test1");
        dict.insert("test 2");
        dict.batchInsert(filepath);
        assertEquals(2199, dict.getChangeInSize());
        assertEquals(2199+2, dict.getCurrentNumberOfItems());
        dict.batchDelete(filepath);
        assertEquals(2,dict.getCurrentNumberOfItems());
        assertEquals(-2199,dict.getChangeInSize());
    }
    @Test
    public void testBatchFileUnique2199NamesQuadratic(){
        Dictionary dict=new Dictionary("N^2");
        String filepath="D:\\CSE - Department\\Level 2\\Second Semester\\Data Structures and Algorithms\\Labs\\Lab 2\\Perfect-Hashing\\src\\input_files\\2199-names.txt";
        dict.batchInsert(filepath);
        assertEquals(2199, dict.getChangeInSize());
        assertEquals(2199, dict.getCurrentNumberOfItems());
        dict.batchDelete(filepath);
        assertEquals(0,dict.getCurrentNumberOfItems());
        assertEquals(-2199,dict.getChangeInSize());
    }

    @Test
    public void testBatchFileUnique9492WordQuadratic(){
        Dictionary dict= new Dictionary("N^2");
        String filepath="D:\\CSE - Department\\Level 2\\Second Semester\\Data Structures and Algorithms\\Labs\\Lab 2\\Perfect-Hashing\\src\\input_files\\9492-english.txt";
        dict.batchInsert(filepath);
        assertEquals(9492, dict.getChangeInSize());
        assertEquals(9492, dict.getCurrentNumberOfItems());
        dict.batchDelete(filepath);
        assertEquals(0,dict.getCurrentNumberOfItems());
        assertEquals(-9492,dict.getChangeInSize());
    }
    @Test
    public void testBatchFileUnique9492WordWithOtherModificationsLinear(){
        Dictionary dict= new Dictionary("N");
        String filepath="D:\\CSE - Department\\Level 2\\Second Semester\\Data Structures and Algorithms\\Labs\\Lab 2\\Perfect-Hashing\\src\\input_files\\9492-english.txt";
        for (int i = 0;i < 10;i++){
            dict.insert("duplicateddd");
        }
        dict.batchInsert(filepath);
        assertEquals(9492, dict.getChangeInSize());
        assertEquals(9493, dict.getCurrentNumberOfItems());
        assertTrue(dict.search("duplicateddd"));
        dict.batchDelete(filepath);
        assertEquals(1,dict.getCurrentNumberOfItems());
        assertEquals(-9492,dict.getChangeInSize());
        for (int i = 0;i < 20;i++){
            dict.insert("duplicateddd");
        }
        assertTrue(dict.search("duplicateddd"));
        assertEquals(1, dict.getCurrentNumberOfItems());
        dict.delete("duplicateddd");
        assertFalse(dict.search("duplicateddd") && dict.getCurrentNumberOfItems()==0);
    }
    @Test
    public void testAllSameLinear(){
        Dictionary dict = new Dictionary("N");
        String filepath="D:\\CSE - Department\\Level 2\\Second Semester\\Data Structures and Algorithms\\Labs\\Lab 2\\Perfect-Hashing\\src\\input_files\\all-same.txt";
        dict.batchInsert(filepath);
        assertEquals(1,dict.getCurrentNumberOfItems());
        dict.insert("lksahlkghds");
        assertEquals(0,dict.getChangeInSize());
        dict.batchDelete(filepath);
        assertEquals(0,dict.getCurrentNumberOfItems());
    }
    @Test
    public void testAllSameQuadratic(){
        Dictionary dict = new Dictionary("N^2");
        String filepath="D:\\CSE - Department\\Level 2\\Second Semester\\Data Structures and Algorithms\\Labs\\Lab 2\\Perfect-Hashing\\src\\input_files\\all-same.txt";
        dict.batchInsert(filepath);
        assertEquals(1,dict.getCurrentNumberOfItems());
        dict.batchDelete(filepath);
        assertEquals(0,dict.getCurrentNumberOfItems());
    }
    @Test
    public void nonIntersectingQuadratic(){
        Dictionary dict=new Dictionary("N^2");
        String filepath1="D:\\CSE - Department\\Level 2\\Second Semester\\Data Structures and Algorithms\\Labs\\Lab 2\\Perfect-Hashing\\src\\input_files\\demo_ass2_test.txt";
        String filepath2="D:\\CSE - Department\\Level 2\\Second Semester\\Data Structures and Algorithms\\Labs\\Lab 2\\Perfect-Hashing\\src\\input_files\\easy-file1.txt";
        dict.batchInsert(filepath2);
        assertEquals(9,dict.getCurrentNumberOfItems());
        assertEquals(9,dict.getChangeInSize());
        dict.batchInsert(filepath1);
        assertEquals(9+5,dict.getCurrentNumberOfItems());
        assertEquals(5,dict.getChangeInSize());
        dict.batchDelete(filepath2);
        assertEquals(5,dict.getCurrentNumberOfItems());
        assertEquals(-9,dict.getChangeInSize());
        dict.batchDelete(filepath1);
        assertEquals(dict.getChangeInSize(),-5);
        assertEquals(0,dict.getCurrentNumberOfItems());
    }
    @Test
    public void nonIntersectingLinear(){
        Dictionary dict=new Dictionary("N");
        String filepath1="D:\\CSE - Department\\Level 2\\Second Semester\\Data Structures and Algorithms\\Labs\\Lab 2\\Perfect-Hashing\\src\\input_files\\demo_ass2_test.txt";
        String filepath2="D:\\CSE - Department\\Level 2\\Second Semester\\Data Structures and Algorithms\\Labs\\Lab 2\\Perfect-Hashing\\src\\input_files\\easy-file1.txt";
        dict.batchInsert(filepath2);
        assertEquals(9,dict.getCurrentNumberOfItems());
        assertEquals(9,dict.getChangeInSize());
        dict.batchInsert(filepath1);
        assertEquals(9+5,dict.getCurrentNumberOfItems());
        assertEquals(5,dict.getChangeInSize());
        dict.batchDelete(filepath2);
        assertEquals(5,dict.getCurrentNumberOfItems());
        assertEquals(-9,dict.getChangeInSize());
        dict.batchDelete(filepath1);
        assertEquals(dict.getChangeInSize(),-5);
        assertEquals(0,dict.getCurrentNumberOfItems());
    }
    @Test
    public void intersectingQuadratic(){
        int n=1000;
        Dictionary dict=new Dictionary("N^2");
        for (int i = 0; i < n; i++){//0-999
            dict.insert(String.valueOf(i));
            assertTrue(dict.search(String.valueOf(i)));
        }
        for (int i = 500;i < n+500;i++){ //500-1499
            dict.insert(String.valueOf(i));;
            assertTrue(dict.search(String.valueOf(i)));
        }
        assertEquals(1500,dict.getCurrentNumberOfItems());
    }

    @Test
    public void intersectingLinear(){
        Dictionary dict=new Dictionary("N");
        String filepath1="D:\\CSE - Department\\Level 2\\Second Semester\\Data Structures and Algorithms\\Labs\\Lab 2\\Perfect-Hashing\\src\\input_files\\sample-delete-1.txt";
        String filepath2="D:\\CSE - Department\\Level 2\\Second Semester\\Data Structures and Algorithms\\Labs\\Lab 2\\Perfect-Hashing\\src\\input_files\\easy-file1.txt";
        dict.batchInsert(filepath2);
        assertEquals(9,dict.getCurrentNumberOfItems());
        assertEquals(9,dict.getChangeInSize());
        dict.batchInsert(filepath1);
        assertEquals(9,dict.getCurrentNumberOfItems());
        dict.batchDelete(filepath1);
        assertEquals(6,dict.getCurrentNumberOfItems());
        assertEquals(-3,dict.getChangeInSize());
        dict.batchDelete(filepath2);
        assertEquals(dict.getChangeInSize(),-6);
        assertEquals(0,dict.getCurrentNumberOfItems());
    }
}
