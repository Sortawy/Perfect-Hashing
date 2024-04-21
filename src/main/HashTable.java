package src.main;

import java.util.ArrayList;
import java.util.Arrays;

public class HashTable<T> {
    private int size;
     int count = 0;
    private Object[] hashTable;
    private UniversalHashing<T> universalHashing;

    public HashTable(int size) {
        this.size = size * size;
        this.count = 0;
        hashTable = new Object[this.size];
        universalHashing = new UniversalHashing<>(this.size);
    }

    public void insert(T value) {
        int key = universalHashing.hash(value);

        if(this.contains(value)) return; // value already exists
        if(this.isFull()) {
            throw new IllegalStateException("Hash Table is full");
        }
        while (hashTable[key] != null) {  // collision
            universalHashing.regenerateHashFunction();
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
        return hashTable[key] == value;
    }

    private void rehash() {
        ArrayList<T> currentValues = new ArrayList<>();

        for (int i = 0;i < this.size; i++) {
            if (hashTable[i] == null) continue;
            currentValues.add((T) hashTable[i]);
            hashTable[i] = null;
        }
        count = 0;
        currentValues.forEach(this::insert);
    }
    private boolean isFull() {
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
}
