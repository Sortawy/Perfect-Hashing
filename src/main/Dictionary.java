package src.main;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Dictionary implements IDictionary {
    private int hits; /* count of successful operations */
    private int misses; /* count of failed operations */
    private HashTable<String> hashTable;
    private boolean isBatch;

    Dictionary(){}

    /**
     * Takes the name of the type of the backend perfect hashing as an
     * input and creates a new empty dictionary based on it.
     * @param hashing_type : Type of the backend perfect hashing (N/N^2) space solutions.
     */
    Dictionary(String hashing_type){
        this.resetCounters();
        this.isBatch=false;
        int MAX_SIZE = 1000;
        if (hashing_type.equals("N^2")) { // N^2-Space Solution
            this.hashTable=new QuadraticSpaceHashTable<>(MAX_SIZE);
        }
        else { // N-Space Solution
//            this.hashTable=new LinearSpaceHashTable<>(10);
        }
    }

    @Override
    public void insert(String key) {
        if (!this.isBatch)
            this.resetCounters();
        if (this.hashTable.contains(key)) {
            this.misses++;
            return;
        }
        this.hashTable.insert(key);
        if (this.hashTable.contains(key))
            this.hits++;
    }

    @Override
    public void delete(String key) {
        if (!this.isBatch)
            this.resetCounters();
        if (!this.hashTable.contains(key)) {
            this.misses++;
            return;
        }
        this.hashTable.delete(key);
        if (!this.hashTable.contains(key))
            this.hits++;
    }

    @Override
    public boolean search(String key) {
        return this.hashTable.contains(key);
    }

    @Override
    public void batchInsert(String file_path) {
        this.resetCounters();
        this.isBatch=true;
        File file=new File(file_path);
        if (!file.exists() || !file.canRead()) {
            System.out.println("ERROR! Cannot open the file.");
            this.isBatch=false;
            return;
        }
        ArrayList<String>toBeAdded=WordReader.readFromFile(file);
        // uncomment if you want to rehash the table for each batch insert
//        this.hashTable=new QuadraticSpaceHashTable<>(toBeAdded.size());
        for (String word:toBeAdded){
            this.insert(word);
        }
        this.isBatch=false;
    }

    @Override
    public void batchDelete(String file_path) {
        this.resetCounters();
        this.isBatch=true;
        File file=new File(file_path);
        if (!file.exists() || !file.canRead()) {
            System.out.println("ERROR! Cannot open the file.");
            this.isBatch=false;
            return;
        }
        ArrayList<String>toBeDeleted=WordReader.readFromFile(file);
        for (String word:toBeDeleted){
            this.delete(word);
        }
        this.isBatch=false;
    }

    /**
     * Method resets the counters of hits and misses
     */
    public void resetCounters(){
        this.hits=0;
        this.misses=0;
    }

    public int getHits() {
        return hits;
    }

    public int getMisses() {
        return misses;
    }

    public int getRehashCount(){
        return this.hashTable.getNumberOfCollisions();
    }



    static class WordReader {
        /**
         * Method to read the words in the file line by line
         * @param input_file : the File object of the input file
         * @return : Array Of Strings of words in the input file
         */
        static ArrayList<String> readFromFile(File input_file) {
            if (input_file == null){
                return new ArrayList<>();
            }
            try {
                ArrayList<String> lines = new ArrayList<>();
                BufferedReader buffReader = new BufferedReader(new FileReader(input_file));
                String line = buffReader.readLine();
                while (line != null) {
                    if(line.trim().isEmpty()) // if it's whitespace
                    {
                        line = buffReader.readLine();
                        continue;
                    }
                    lines.add(line);
                    line = buffReader.readLine();
                }
                return lines;
            }
            catch (IOException e){
                System.out.println("ERROR reading the file!");
                throw new RuntimeException(e);
            }
        }
    }
}
