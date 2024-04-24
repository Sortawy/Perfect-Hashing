package src.main;

public class LinearSpacePerfectHashing <T> {
    private UniversalHashing<T> firstLevelHashFunction;
    private UniversalHashing<T>[] secondLevelHashFunctions;
    // private T[] keys;
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
        // this.keys = keys;
        this.buildHashTable(keys);
    }

    private void buildHashTable (T[] keys) {
        this.firstLevelHashTable = new int[this.numberOfKeys];
        this.secondLevelHashTables = (T[][]) new Object[this.numberOfKeys][];
        this.firstLevelHashFunction =  this.numberOfKeys == 0 ? null : new UniversalHashing<T>(this.numberOfKeys);
        this.secondLevelHashFunctions = this.numberOfKeys == 0 ? null : (UniversalHashing<T>[]) new UniversalHashing<?>[this.numberOfKeys];
        this.buildFirstLevelHashTable(keys);
        this.buildSecondLevelHashTables(keys);
    }

    private void buildFirstLevelHashTable (T[] keys) {
        for(int i=0; i<this.numberOfKeys; i++){
            // if(keys[i] == null || contains(keys[i])) continue;
            T key = keys[i];
            int firstLevelIndex = this.firstLevelHashFunction.hash(key);
            this.firstLevelHashTable[firstLevelIndex]++;
        }
    }

    private void buildSecondLevelHashTables (T[] keys) {
        for(int firstLevelIndex=0; firstLevelIndex<this.secondLevelHashTables.length; firstLevelIndex++){
            if(firstLevelHashTable[firstLevelIndex] == 0) continue;
            this.secondLevelHashTables[firstLevelIndex] =  (T[]) new Object[this.firstLevelHashTable[firstLevelIndex]*this.firstLevelHashTable[firstLevelIndex]];
            this.secondLevelHashFunctions[firstLevelIndex] = new UniversalHashing<T>(this.firstLevelHashTable[firstLevelIndex]*this.firstLevelHashTable[firstLevelIndex]);
            this.buildHashTableEntry(keys, firstLevelIndex);
        }
    }

    private void buildHashTableEntry (T[] keys, int firstLevelIndex) {
        while(true){
            boolean[] isUsed = new boolean[this.firstLevelHashTable[firstLevelIndex]*this.firstLevelHashTable[firstLevelIndex]];
            boolean isGood = true;
            for(int j=0; j<this.numberOfKeys; j++){
                T key = keys[j];
                if(this.contains(key)) continue;
                int secondLevelIndex = this.secondLevelHashFunctions[firstLevelIndex].hash(key);
                // for(T k : keys){
                for(T[] secondLevelHashTable : this.secondLevelHashTables){
                    if(secondLevelHashTable == null) continue;
                    for(T k : secondLevelHashTable){
                        if(k == null) continue;
                        if(k.equals(key)){
                            debug();
                            System.out.println(k + " bsbs " + key);
                        } 
                    }
                }
                // }
                if(this.firstLevelHashFunction.hash(key) == firstLevelIndex && !this.contains(key)){
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
        if(key == null || this.contains(key)) return;
        this.numberOfInsertions++;
        T[] keys = (T[]) new Object[this.numberOfKeys+1];
        int indx = 0;
        for(T[] secondLevelHashTable : this.secondLevelHashTables){
            if(secondLevelHashTable == null) continue;
            for(T k : secondLevelHashTable){
                if(k == null) continue;
                keys[indx++] = k;
            }
        }
        // if(indx < this.numberOfKeys) this.debug();
        // if(indx == this.numberOfKeys+1) this.debug();
        for(int i=0; i<this.numberOfKeys+1; i++){
            System.out.print(keys[i] + " ");
        }
        System.out.println();
        System.out.println("----------------");
        System.out.println(indx + " " + (this.numberOfKeys+1));
        System.out.println("----------------");


        keys[indx] = key;
        this.numberOfKeys++;
        this.buildHashTable(keys);
    }

    void debug() {

    }

    public void delete (T key) {
        if(!this.contains(key)) return;
        this.numberOfDeletions++;
        int firstLevelIndex = this.firstLevelHashFunction.hash(key);
        int secondLevelIndex = this.secondLevelHashFunctions[firstLevelIndex].hash(key);
        this.secondLevelHashTables[firstLevelIndex][secondLevelIndex] = null;
        this.numberOfKeys--;
    }

    public boolean contains (T key) {
        try {
            int firstLevelIndex = this.firstLevelHashFunction.hash(key);
            int secondLevelIndex = this.secondLevelHashFunctions[firstLevelIndex].hash(key);
            return key.equals(this.secondLevelHashTables[firstLevelIndex][secondLevelIndex]);
        } catch (Exception e) {
            return false;
        }
    }

    public int getNumberOfRehashing () {
        return this.numberOfRehashing;
    }

    public static void main(String[] args) {
        Integer[] keys = {};
        LinearSpacePerfectHashing<Integer> lsh = new LinearSpacePerfectHashing<>(keys);
        lsh.insert(1);
        lsh.insert(2);
        lsh.insert(3);
        lsh.insert(4);
        lsh.insert(5);
        lsh.insert(6);
        lsh.insert(7);
        lsh.insert(8);

        System.out.println(lsh.contains(1)); // 0
        lsh.delete(1);
        lsh.delete(2);
        lsh.delete(3);
        lsh.delete(4);
        lsh.delete(5);
        lsh.delete(6);
        lsh.delete(7);
        System.out.println(lsh.contains(1)); // 0
        System.out.println("-------------------------------------------------"); // 0
        System.out.println(lsh.contains(0)); // 1
        lsh.insert(0);
        System.out.println(lsh.contains(0)); // 1
    }
    
}