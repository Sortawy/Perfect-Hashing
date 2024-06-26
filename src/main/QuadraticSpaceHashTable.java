package src.main;

import java.util.ArrayList;

public class QuadraticSpaceHashTable<T> implements HashTable<T> {
    private int size;
    private int count;
    private int numberOfCollisions;
    private Object[] hashTable;
    private UniversalHashing<T> universalHashing;

    public QuadraticSpaceHashTable(int size) {
        this.size = size * size;
        this.count = 0;
        this.numberOfCollisions = 0;
        hashTable = new Object[this.size];
        universalHashing = new UniversalHashing<>(this.size);
    }

    public void insert(T value) {
        if(this.contains(value)) return; // ignore duplicates
        if(this.isFull()) {
            // throw new IllegalStateException("Hash Table is full");
            this.rehash(this.getNumberOfItems()*2); // increase capacity and rehash
        }
        int key = universalHashing.hash(value);
        while (hashTable[key] != null) {  // collision
            numberOfCollisions++;
            rehash();
            key = universalHashing.hash(value);
        }
        hashTable[key] = value;
        count++;
    }

    public void delete(T value) {
        int key = universalHashing.hash(value);
        if (this.contains(value)) {
            hashTable[key] = null;
            count--;
        }
    }

    public boolean contains(T value) {
        int key = universalHashing.hash(value);
        return hashTable[key] != null && hashTable[key].equals(value);
    }

    public int getNumberOfCollisions() {
        return numberOfCollisions;
    }

    public void rehash() {
        universalHashing.regenerateHashFunction();
        ArrayList<T> currentValues = new ArrayList<>();

        for (int i = 0;i < this.size; i++) {
            if (hashTable[i] == null) continue;
            currentValues.add((T) hashTable[i]);
            hashTable[i] = null;
        }
        count = 0;
        currentValues.forEach(this::insert);
    }

    public void rehash(int size) {
        this.size = size * size;
        universalHashing = new UniversalHashing<>(this.size);
        ArrayList<T> currentValues = new ArrayList<>();

         for (int i = 0;i < hashTable.length; i++) {
            if (hashTable[i] == null) continue;
            currentValues.add((T) hashTable[i]);
            hashTable[i] = null;
         }

         hashTable = new Object[this.size];
         count = 0;
         currentValues.forEach(this::insert);
    }

    public boolean isFull() {
        return count == Math.sqrt(size); // sqrt to get the real size
    }

    @Override
    public String toString() {
        ArrayList<T> values = new ArrayList<>();
        for (int i = 0; i < this.size; i++) {
            if (hashTable[i] != null) {
                values.add((T) hashTable[i]);
            }
        }
        return values.toString();
    }

    public Object[] getHashTable() {
        return hashTable;
    }

    public int getNumberOfItems() {
        return count;
    }

    @Override
    public void batchDelete(T[] keys) {
        for (T obj : keys) {
            this.delete(obj);
        }
    }

    @Override
    public void batchInsert(T[] keys) {
        this.rehash(this.getNumberOfItems()+keys.length);
        for (T obj : keys) {
            this.insert(obj);
        }
    }
}
