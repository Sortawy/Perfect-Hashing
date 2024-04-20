package src.main;

public class Main {
    public static void main(String[] args) {
        UniversalHashing universalHashing = new UniversalHashing(10);
        System.out.println(universalHashing.hash(5));
        System.out.println(universalHashing.hash(5));
        universalHashing.regenerateHashFunction();
        System.out.println(universalHashing.hash(5));
        System.out.println(universalHashing.hash(5));
    }
}
