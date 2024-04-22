package src.main;

public interface HashTable<T> {

    void insert(T value);

    void delete(T value);

    boolean contains(T value);

    int getNumberOfCollisions();

    void rehash(int size);

    boolean isFull();

    int getNumberOfItems();
}
