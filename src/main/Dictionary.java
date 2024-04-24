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

    Dictionary(){}

    /**
     * Takes the name of the type of the backend perfect hashing as an
     * input and creates a new empty dictionary based on it.
     * @param hashing_type : Type of the backend perfect hashing (N/N^2) space solutions.
     */
    Dictionary(String hashing_type){
        int INITIAL_SIZE = 10;
        if (hashing_type.equals("N^2")) { // N^2-Space Solution
            this.hashTable=new QuadraticSpaceHashTable<>(INITIAL_SIZE);
        }
        else { // N-Space Solution
            this.hashTable= new LinearSpacePerfectHashing<>(INITIAL_SIZE);
        }
    }

    @Override
    public void insert(String key) {
        // if (this.hashTable instanceof QuadraticSpaceHashTable) {
        //     if (((QuadraticSpaceHashTable<String>) this.hashTable).isFull()) {
        //         ((QuadraticSpaceHashTable<String>) this.hashTable).rehash(this.hashTable.getNumberOfItems() * 2); // increase capacity and rehash
        //     }
        // }
        this.hashTable.insert(key);
    }

    @Override
    public void delete(String key) {
        this.hashTable.delete(key);
    }

    @Override
    public boolean search(String key) {
        return this.hashTable.contains(key);
    }

    @Override
    public void batchInsert(String file_path) {
        File file=new File(file_path);
        if (!file.exists() || !file.canRead()) {
            System.out.println("ERROR! Cannot open the file.");
            return;
        }
        String[]toBeAdded=WordReader.readFromFile(file);
        // if (this.hashTable instanceof QuadraticSpaceHashTable)
        //     ((QuadraticSpaceHashTable<String>) this.hashTable).rehash(toBeAdded.length+this.hashTable.getNumberOfItems());
        this.hashTable.batchInsert(toBeAdded);
    }

    @Override
    public void batchDelete(String file_path) {
        File file=new File(file_path);
        if (!file.exists() || !file.canRead()) {
            System.out.println("ERROR! Cannot open the file.");
            return;
        }
        String [] toBeDeleted=WordReader.readFromFile(file);
        this.hashTable.batchDelete(toBeDeleted);
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
        static String[] readFromFile(File input_file) {
            if (input_file == null){
                return new String[0];
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

                return lines.toArray(new String[0]);
            }
            catch (IOException e){
                System.out.println("ERROR reading the file!");
                throw new RuntimeException(e);
            }
        }
    }
}
