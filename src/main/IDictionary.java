package src.main;

public interface IDictionary {

    /**
     * Takes a single string key and tries to insert it
     * @param key : The key to be inserted
     */
    void insert(String key);

    /**
     * Takes a single string key and tries to delete it.
     * @param key : The key to be deleted
     */
    void delete(String key);

    /**
     * Takes a single string key, searches for it, and returns true if it exists and false
     * otherwise.
     * @param key : The looked up key
     * @return
     */
    boolean search(String key);

    /**
     * Takes a path to a text file containing multiple words each in a separate
     * line. And tries to delete all that words from the dictionary.
     * @param file_path : The path to the text file containing the words to be inseted.
     */
    void batchInsert(String file_path);

    /**
     * Takes a path to a text file containing multiple words each in a separate line.
     * And tries to insert all that words into the dictionary
     * @param file_path : The path to the text file containing the words to deleted.
     */
    void batchDelete(String file_path);

    int getRehashCount();

    int getCurrentNumberOfItems();

    int getChangeInSize();
}
