package src.main;

public class Main {
    public static void main(String[] args) {
        UniversalHashing<Integer> universalHashing = new UniversalHashing<>(10);
        System.out.println(universalHashing.hash(2));
        System.out.println(universalHashing.hash(2));
        universalHashing.regenerateHashFunction();
        System.out.println(universalHashing.hash(2));
        System.out.println(universalHashing.hash(2));
    }
}
