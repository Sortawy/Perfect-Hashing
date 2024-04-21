package src.main;

import java.util.Scanner;

public class Main {
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        System.out.println("What is the type of backend perfect hashing?");
        System.out.println("1. O(N²) - Quadratic Space");
        System.out.println("2. O(N) - Linear Space");
        int choice  =-1;
        while(choice!=1&&choice!=2) {
            System.out.print("\nEnter choice (1-2): ");
            choice=in.nextInt();
        }
        Dictionary dictionary = new Dictionary(choice==1?"N^2":"N");
        while (true){
            printMenu();
            choice = in.nextInt();
            String input;
            switch (choice) {
                case 1: // insert
                    System.out.print("Enter the string you want to add to the dictionary: ");
                    input= in.nextLine();
                    System.out.println();
                    dictionary.insert(input);
                    if (dictionary.getHits()==1){
                        System.out.print("The word is successfully inserted.");
                    }
                    else if (dictionary.getMisses()==1){
                        System.out.print("The word already exists.");
                    }
                    else {
                        System.out.println("Error, hits ==  misses !"); // shouldn't occur
                    }
                    break;
                case 2: // delete
                    System.out.print("Enter the string you want to remove from the dictionary: ");
                    input=in.nextLine();
                    dictionary.delete(input);
                    if (dictionary.getHits()==1){
                        System.out.print("The word is successfully deleted.");
                    }
                    else if (dictionary.getMisses()==1){
                        System.out.print("The word does not exist.");
                    }
                    else {
                        System.out.println("Error, hits ==  misses !"); // shouldn't occur
                    }
                    break;
                case 3: // search
                    System.out.print("Enter the string you want to remove from the dictionary: ");
                    input=in.nextLine();
                    if(dictionary.search(input)){
                        System.out.println("\""+input+"\" is in the dictionary.");
                    }
                    else {
                        System.out.println("\""+input+"\" is not in the dictionary.");
                    }
                    break;
                case 4: // batch insert
                    System.out.println("Enter the path of the file you want to batch insert from: ");
                    input = in.nextLine();
                    dictionary.batchInsert(input);
                    System.out.println("Count of newly added strings: " + dictionary.getHits());
                    System.out.println("Count of already existing strings: " + dictionary.getMisses());
                    break;
                case 5: // batch delete
                    System.out.println("Enter the path of the file you want to batch delete from: ");
                    input = in.nextLine();
                    dictionary.batchDelete(input);
                    // results
                    System.out.println("Count of successfully deleted strings: " + dictionary.getHits());
                    System.out.println("Count of non existing strings: "+ dictionary.getMisses());
                    break;
                case 6: // exit
                    return;
                default:
                    System.out.println("Invalid choice.");
                    return;
            }

        }
    }
    static void printMenu(){
        System.out.println("1. Insert a string");
        System.out.println("2. Delete a string");
        System.out.println("3. Search for a string");
        System.out.println("4. Batch insert strings from a text file");
        System.out.println("5. Batch delete strings from a text file");
        System.out.println("6. Exit");
        System.out.print("Enter choice (1-6): ");
    }
}
