package src.main;

public class ExampleQuadraticSpace {
    public static void main(String[] args) {
       HashTable<Integer> hashTable = new QuadraticSpaceHashTable<>(10);

       for (int i = 0; i < 10; i++) {
           hashTable.insert(i);
       }
       hashTable.delete(9);
       hashTable.delete(8);
       hashTable.insert(11);
       hashTable.insert(11);
       System.out.println(hashTable);
       for (int i = 0; i < 10; i++) {
           if (!hashTable.contains(i)) {
               System.out.println("Error" + i);
           }
       }
       for (int i = 10; i < 20; i++) {
           if (hashTable.contains(i)) {
               System.out.println("Error" + i);
           }
       }
    }
}
