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
            int changes;
            int batch_duplicates;
            switch (choice) {
                case 1: // insert
                    System.out.print("Enter the string you want to add to the dictionary: ");
                    in.nextLine();
                    input= in.nextLine();
                    System.out.println();
                    dictionary.insert(input);
                    changes = dictionary.getChangeInSize();
                    if (changes==1){
                        System.out.println("The word "+"\""+input+"\""+" is successfully inserted.");
                    }
                    else if (changes==0){
                        System.out.println("The word "+"\""+input+"\""+" already exists.");
                    }
                    break;
                case 2: // delete
                    System.out.print("Enter the string you want to remove from the dictionary: ");
                    in.nextLine();
                    input=in.nextLine();
                    dictionary.delete(input);
                    changes=dictionary.getChangeInSize();
                    if (changes==-1){
                        System.out.println("The word "+"\""+input+"\""+" is successfully deleted.");
                    }
                    else if (changes==0){
                        System.out.println("The word "+"\""+input+"\""+" does not exist.");
                    }
                    break;
                case 3: // search
                    System.out.print("Enter the string you want to look up for in the dictionary: ");
                    in.nextLine();
                    input=in.nextLine();
                    if(dictionary.search(input)){
                        System.out.println("\""+input+"\" is in the dictionary.");
                    }
                    else {
                        System.out.println("\""+input+"\" is not in the dictionary.");
                    }
                    break;
                case 4: // batch insert
                    System.out.print("Enter the path of the file you want to batch insert from: ");
                    in.nextLine();
                    input = in.nextLine();
                    dictionary.batchInsert(input);
                    changes= dictionary.getChangeInSize();
                    batch_duplicates= dictionary.getPreviousBatchSize()-changes;
                    System.out.println("Count of newly added strings: " + changes);
                    System.out.println("Count of already existing strings: " + batch_duplicates);
                    break;
                case 5: // batch delete
                    System.out.print("Enter the path of the file you want to batch delete from: ");
                    in.nextLine();
                    input = in.nextLine();
                    dictionary.batchDelete(input);
                    // results
                    changes=dictionary.getChangeInSize();
                    batch_duplicates=dictionary.getPreviousBatchSize()-Math.abs(changes);
                    System.out.println("Count of successfully deleted strings: " + Math.abs(changes));
                    System.out.println("Count of non-existing strings: "+ batch_duplicates);
                    break;
                case 6: // exit
                    in.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
                    in.close();
                    return;
            }

            System.out.println("Number of rehashes due to collisions: " +dictionary.getRehashCount());
            System.out.println("Total number of words in dictionary: " + dictionary.getCurrentNumberOfItems());
        }
    }
    static void printMenu(){
        System.out.println();
        System.out.println("1. Insert a string");
        System.out.println("2. Delete a string");
        System.out.println("3. Search for a string");
        System.out.println("4. Batch insert strings from a text file");
        System.out.println("5. Batch delete strings from a text file");
        System.out.println("6. Exit");
        System.out.print("Enter choice (1-6): ");
    }
}