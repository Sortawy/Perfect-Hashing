package src.main;

public class LinearSpacePerfectHashing <T> {
    private UniversalHashing<T> firstLevelHashFunction;
    private UniversalHashing<T>[] secondLevelHashFunctions;
    private int numberOfKeys;
    private int numberOfRebuild;
    private int[] numberOfCollisions;
    private T[][] secondLevelHashTables;

    public LinearSpacePerfectHashing (T[] keys) {
        this.numberOfRebuild = 0;
        this.numberOfKeys = keys.length;
        this.numberOfCollisions = new int[numberOfKeys];
        this.secondLevelHashTables = (T[][]) new Object[numberOfKeys][];
        this.firstLevelHashFunction = new UniversalHashing<T>(this.numberOfKeys);
        this.secondLevelHashFunctions = (UniversalHashing<T>[]) new UniversalHashing<?>[this.numberOfKeys];
        this.buildHashTable(keys);
    }

    private void buildHashTable (T[] keys) {
        this.buildNumberOfCollisions(keys);
        this.buildSecondLevelHashTables(keys);
    }

    private void buildNumberOfCollisions (T[] keys) {
        for(int i=0; i<this.numberOfKeys; i++){
            T key = keys[i];
            int hashValue = this.firstLevelHashFunction.hash(key);
            this.numberOfCollisions[hashValue]++;
        }
    }

    private void buildSecondLevelHashTables (T[] keys) {
        for(int i=0; i<this.secondLevelHashTables.length; i++){
            if(numberOfCollisions[i] == 0) continue;
            this.secondLevelHashTables[i] =  (T[]) new Object[this.numberOfCollisions[i]*this.numberOfCollisions[i]];
            this.secondLevelHashFunctions[i] = new UniversalHashing<T>(this.numberOfCollisions[i]*this.numberOfCollisions[i]);
            this.buildHashTable(keys, i);
        }
    }

    private void buildHashTable (T[] keys, int i) {
        while(true){
            boolean[] isUsed = new boolean[this.numberOfCollisions[i]*this.numberOfCollisions[i]];
            boolean isGood = true;
            for(int j=0; j<this.numberOfKeys; j++){
                T key = keys[j];
                int hashValue = this.secondLevelHashFunctions[i].hash(key);
                if(firstLevelHashFunction.hash(key) == i){
                    if(isUsed[hashValue]){
                        isGood = false;
                        break;
                    }
                    isUsed[hashValue] = true;
                    this.secondLevelHashTables[i][hashValue] = key;
                }
            }
            if(isGood) break;
            this.rebuildHashTable(i);
        }
    }

    private void rebuildHashTable (int i) {
        this.numberOfRebuild++;
        this.secondLevelHashFunctions[i].regenerateHashFunction();
    }

    public boolean lookUp (T key) {
        try {
            int hashValue = this.firstLevelHashFunction.hash(key);
            int secondLevelHashValue = this.secondLevelHashFunctions[hashValue].hash(key);
            return key == this.secondLevelHashTables[hashValue][secondLevelHashValue];
        } catch (Exception e) {
            return false;
        }
    }

    public int getNumberOfRebuild () {
        return this.numberOfRebuild;
    }

    public static void main(String[] args) {
        Integer[] keys = {1, 2, 3, 4, 5, 10, 6, 7, 8, 9};
        LinearSpacePerfectHashing<Integer> lsh = new LinearSpacePerfectHashing<>(keys);
        System.out.println(lsh.lookUp(1)); // 0
        System.out.println(lsh.lookUp(10)); // 1
        System.out.println(lsh.lookUp(7)); // 6
        System.out.println(lsh.lookUp(0)); // 1
        System.out.println(lsh.lookUp(9)); // 8
    }
    
}