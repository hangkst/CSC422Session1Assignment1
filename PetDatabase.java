import java.util.ArrayList;
import java.util.Scanner;

public class PetDatabase {
    public static void main(String[] args) {
        // Scanner for user input
        Scanner scnr = new Scanner(System.in);

        // Pet database and sample pets
        ArrayList<Pet> pets = new ArrayList<Pet>();
        pets.add(new Pet("Sweet", 3));
        pets.add(new Pet("Hyper", 5));
        pets.add(new Pet("Bob", 2));

        int choice = 0;
        while (choice != 7) {
            // Menu display
            System.out.println("\n\n\n");
            System.out.println("What would you like to do?");
            System.out.println("1) View all pets");
            System.out.println("2) Add more pets");
            System.out.println("3) Update an existing pet");
            System.out.println("4) Remove an existing pet");
            System.out.println("5) Search pets by name");
            System.out.println("6) Search pets by age");
            System.out.println("7) Exit program");
            System.out.printf("Your choice: ");
            choice = scnr.nextInt();
            switch (choice) {
                // Testing first
                case 1: {
                    int count = 0; // Row count

                    // Header
                    System.out.println("+-------------------------+");
                    System.out.println("| ID  | Name       | Age  |");
                    System.out.println("+-------------------------+");

                    // Inserting pet data into table
                    for (int i = 0; i < pets.size(); i++) {
                        int id = i, age = pets.get(i).getAge();
                        String name = pets.get(i).getName();
                        System.out.printf("| %-3s | %-10s | %-4s |\n", id, name, age);
                        count++;
                    }
                    System.out.println("+-------------------------+");

                    // Footer with row count
                    System.out.println(count + " rows in set.");
                    break;
                }
                case 2: {
                    scnr.nextLine(); // Clear buffer
                    while (true) {
                        // Prompt for new pet info
                        String nameAgeInput = ""; 
                        System.out.printf("add pet (name, age): ");
                        nameAgeInput = scnr.nextLine();
                        
                        // Exit condition if user puts "done"
                        if (nameAgeInput.equals("done")) {
                            break;
                        }

                        // Take in the input and create new pet
                        String[] newNameAge = nameAgeInput.split(" ");
                        String name = newNameAge[0];
                        int age = Integer.parseInt(newNameAge[1]);
                        pets.add(new Pet(name, age));
                    }
                    break;
                }
                case 3: {
                    System.out.println("Updating an existing pet...");
                    break;
                }
                case 4: {
                    System.out.println("Removing an existing pet...");
                    break;
                }
                case 5: {
                    System.out.println("Searching pets by name...");
                    break;
                }
                case 6: {
                    System.out.println("Searching pets by age...");
                    break;
                }
                case 7: {
                    System.out.println("Exiting program...");
                    break;
                }
            }
        }
        scnr.close();
    }
}