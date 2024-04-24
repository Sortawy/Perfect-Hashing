package src.main;

public interface HashTable<T> {

    void insert(T value);

    void delete(T value);

    boolean contains(T value);

    int getNumberOfCollisions();

    int getNumberOfItems();

    void batchDelete(T[] keys);

    void batchInsert(T[] keys);
}
