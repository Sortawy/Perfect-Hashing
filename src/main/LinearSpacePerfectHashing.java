package src.main;

public class LinearSpacePerfectHashing <T> {
    private UniversalHashing<T> firstLevelHashFunction;
    private UniversalHashing<T>[] secondLevelHashFunctions;
    private T[] keys;
    private int numberOfKeys;
    private int numberOfInsertions;
    private int numberOfDeletions;
    private int numberOfRehashing;
    private int[] firstLevelHashTable;
    private T[][] secondLevelHashTables;

    public LinearSpacePerfectHashing (T[] keys) {
        this.numberOfInsertions = 0;
        this.numberOfDeletions = 0;
        this.numberOfRehashing = 0;
        this.numberOfKeys = keys.length;
        this.keys = keys;
        this.buildHashTable();
    }

    private void buildHashTable () {
        this.firstLevelHashTable = new int[numberOfKeys];
        this.secondLevelHashTables = (T[][]) new Object[numberOfKeys][];
        this.firstLevelHashFunction = new UniversalHashing<T>(this.numberOfKeys);
        this.secondLevelHashFunctions = (UniversalHashing<T>[]) new UniversalHashing<?>[this.numberOfKeys];
        this.buildFirstLevelHashTable();
        this.buildSecondLevelHashTables();
    }

    private void buildFirstLevelHashTable () {
        for(int i=0; i<this.numberOfKeys; i++){
            T key = keys[i];
            int firstLevelIndex = this.firstLevelHashFunction.hash(key);
            this.firstLevelHashTable[firstLevelIndex]++;
        }
    }

    private void buildSecondLevelHashTables () {
        for(int firstLevelIndex=0; firstLevelIndex<this.secondLevelHashTables.length; firstLevelIndex++){
            if(firstLevelHashTable[firstLevelIndex] == 0) continue;
            this.secondLevelHashTables[firstLevelIndex] =  (T[]) new Object[this.firstLevelHashTable[firstLevelIndex]*this.firstLevelHashTable[firstLevelIndex]];
            this.secondLevelHashFunctions[firstLevelIndex] = new UniversalHashing<T>(this.firstLevelHashTable[firstLevelIndex]*this.firstLevelHashTable[firstLevelIndex]);
            this.buildHashTableEntry(firstLevelIndex);
        }
    }

    private void buildHashTableEntry (int firstLevelIndex) {
        while(true){
            boolean[] isUsed = new boolean[this.firstLevelHashTable[firstLevelIndex]*this.firstLevelHashTable[firstLevelIndex]];
            boolean isGood = true;
            for(int j=0; j<this.numberOfKeys; j++){
                T key = keys[j];
                int secondLevelIndex = this.secondLevelHashFunctions[firstLevelIndex].hash(key);
                if(firstLevelHashFunction.hash(key) == firstLevelIndex){
                    if(isUsed[secondLevelIndex]){
                        isGood = false;
                        break;
                    }
                    isUsed[secondLevelIndex] = true;
                    this.secondLevelHashTables[firstLevelIndex][secondLevelIndex] = key;
                }
            }
            if(isGood) break;
            this.rebuildHashTableEntry (firstLevelIndex);
        }
    }

    private void rebuildHashTableEntry (int firstLevelIndex) {
        this.numberOfRehashing++;
        this.secondLevelHashFunctions[firstLevelIndex].regenerateHashFunction();
    }

    public void insert (T key) {
        if(this.contains(key)) return;
        this.numberOfInsertions++;
        T[] newKeys = (T[]) new Object[this.keys.length+1];
        for(int i=0; i<this.keys.length; i++)
            newKeys[i] = this.keys[i];
        newKeys[this.keys.length] = key;
        this.keys = newKeys;
        this.numberOfKeys++;
        this.buildHashTable();
        // int firstLevelIndex = this.firstLevelHashFunction.hash(key);
        // int secondLevelIndex = this.secondLevelHashFunctions[firstLevelIndex].hash(key);
        // if(this.secondLevelHashTables[firstLevelIndex][secondLevelIndex] == null){
        //     this.secondLevelHashTables[firstLevelIndex][secondLevelIndex] = key;
        //     this.numberOfInsertions++;
        // }else{
        //     // this.rebuildHashTableEntry(firstLevelIndex);
        //     // this.insert(key);
        // }
    }

    public void delete (T key) {
        if(!this.contains(key)) return;
        this.numberOfDeletions++;
        T[] newKeys = (T[]) new Object[this.keys.length-1];
        int index = 0;
        for(int i=0; i<this.keys.length; i++){
            if(this.keys[i] != key){
                newKeys[index] = this.keys[i];
                index++;
            }
        }
        this.keys = newKeys;
        this.numberOfKeys--;
        this.buildHashTable();
        // int firstLevelIndex = this.firstLevelHashFunction.hash(key);
        // int secondLevelIndex = this.secondLevelHashFunctions[firstLevelIndex].hash(key);
        // if(this.secondLevelHashTables[firstLevelIndex][secondLevelIndex] == key){
        //     this.secondLevelHashTables[firstLevelIndex][secondLevelIndex] = null;
        //     this.numberOfDeletions++;
        // }
    }

    public boolean contains (T key) {
        try {
            int firstLevelIndex = this.firstLevelHashFunction.hash(key);
            int secondLevelIndex = this.secondLevelHashFunctions[firstLevelIndex].hash(key);
            return key == this.secondLevelHashTables[firstLevelIndex][secondLevelIndex];
        } catch (Exception e) {
            return false;
        }
    }

    public int getNumberOfRehashing () {
        return this.numberOfRehashing;
    }

    public static void main(String[] args) {
        Integer[] keys = {1, 2, 3, 4, 5, 10, 6, 7, 8, 9};
        LinearSpacePerfectHashing<Integer> lsh = new LinearSpacePerfectHashing<>(keys);
        System.out.println(lsh.contains(1)); // 0
        lsh.delete(1);
        System.out.println(lsh.contains(1)); // 0
        System.out.println("-------------------------------------------------"); // 0
        System.out.println(lsh.contains(0)); // 1
        lsh.insert(0);
        System.out.println(lsh.contains(0)); // 1
    }
    
}