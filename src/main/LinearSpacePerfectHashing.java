package src.main;

public class LinearSpacePerfectHashing <T> {
    private UniversalHashing<T> firstLevelHashFunction;
    private UniversalHashing<T>[] secondLevelHashFunctions;
    private T[] keys;
    private int numberOfKeys;
    private int numberOfInsertions;
    private int numberOfDeletions;
    private int numberOfCollisions;
    private int[] firstLevelHashTable;
    private T[][] secondLevelHashTables;

    public LinearSpacePerfectHashing () {
        this.numberOfInsertions = 0;
        this.numberOfDeletions = 0;
        this.numberOfCollisions = 0;
        this.numberOfKeys = 0;
        this.keys = (T[]) new Object[0];
    }

    public LinearSpacePerfectHashing (T[] keys) {
        this.numberOfInsertions = 0;
        this.numberOfDeletions = 0;
        this.numberOfCollisions = 0;
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
                if(this.contains(key)) continue;
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
        this.numberOfCollisions++;
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
        if(numberOfKeys == 0){
            int firstLevelIndex = this.firstLevelHashFunction.hash(key);
            int secondLevelIndex = this.secondLevelHashFunctions[firstLevelIndex].hash(key);
            this.secondLevelHashTables[firstLevelIndex][secondLevelIndex] = null;
        }
        else this.buildHashTable();
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

    public int getNumberOfCollisions () {
        return this.numberOfCollisions;
    }

    public static void main(String[] args) {
        // Integer[] keys = {1, 2, 3, 4, 5, 10, 6, 7, 8, 9};
        Integer[] keys = {33633, 18286, 71324, 67343, 45380, 56124, 56874, 34693, 74593, 13253, 46083, 44228, 59024, 45623, 39599, 4428, 2042, 22233, 52256, 13448, 21049, 9, 32088, 95876, 78461, 32309, 81455, 63377, 11058, 56799, 96095, 32662, 39485, 3543, 26026, 51132, 67103, 85748, 81239, 21583, 31322, 47329, 53, 5734, 52611, 23475, 99122, 5030, 26618, 6003, 47308, 78382, 85356, 5910, 2642, 94623, 55674, 99577, 20705, 60532, 61457, 73719, 69777, 89850, 44802008, 89021, 17056, 48589, 19503, 66915, 79356, 27726, 22519, 12178, 9097, 46083, 47786, 34261, 77866, 36459, 95638, 89072, 85882, 50704, 18306,416, 81428, 39169, 18053, 32427, 41736, 72083, 6067, 56950, 76800, 6552, 18193, 76447, 66365, 2455, 37080, 9714, 17447};
        //sort the keys
        // Arrays.sort(keys);
        // for(int i=0; i<keys.length; i++)
        //     System.out.println(keys[i]);
        LinearSpacePerfectHashing<Integer> lsh = new LinearSpacePerfectHashing<>();
        for(int i=0; i<keys.length; i++)
            lsh.insert(keys[i]);
        // String a = "ahmed";
        // String b = "ahmed";
        // lsh.insert(a);
        // lsh.delete(b);
        // System.out.println(lsh.contains(a));
        // lsh.insert(a);
        // System.out.println(lsh.contains(a));
        // lsh.delete(b);
        // System.out.println(lsh.contains(a));
        
    }
    
}