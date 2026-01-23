import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PetDatabase {
    public static void main(String[] args) throws IOException {
        // Scanner for user input
        Scanner scnr = new Scanner(System.in);

        // Pet database and sample pets
        ArrayList<Pet> pets = new ArrayList<Pet>();

        // Read from petsData.txt and populate pet database
        FileInputStream in = new FileInputStream("petsData.txt");
        Scanner fileScnr = new Scanner(in);
        while (fileScnr.hasNextLine()) {
            String line = fileScnr.nextLine();

            // Validate and add pet to database
            if (isValidPetData(line)) {
                String[] nameAge = line.split("\\s+"); // Matching white space characters to split
                String name = nameAge[0];
                int age = Integer.parseInt(nameAge[1]);
                pets.add(new Pet(name, age));
            } else {
                System.out.println(line + " is not valid pet data input.");
            }
        }
        fileScnr.close();

        int choice = 0;
        while (choice != 7) {
            // Menu display
            displayMenu();
            // Get user choice
            choice = scnr.nextInt();
            switch (choice) {
                // View all pets
                case 1: {
                    // Display current pets
                    displayPets(pets);
                    break;
                }

                // Add more pets
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

                        // Check if database is full
                        if (pets.size() >= 5) {
                            System.out.println("ERROR: Pet database is full. Cannot add more pets.");
                            continue;
                        }

                        // Validate and add new pet
                        if (isValidPetData(nameAgeInput)) {
                            String[] newNameAge = nameAgeInput.split("\\s+");
                            String name = newNameAge[0];
                            int age = Integer.parseInt(newNameAge[1]);
                            pets.add(new Pet(name, age));
                        } else {
                            System.out.println(nameAgeInput + " is not valid pet data input.");
                        }

                    }
                    break;
                }

                // Update an existing pet
                case 3: {
                    // Clear buffer
                    scnr.nextLine();

                    // Display current pets
                    displayPets(pets);

                    // Prompt for pet ID to update
                    System.out.printf("Enter the ID of the pet to update: ");
                    int updateId = scnr.nextInt();

                    // Validate pet ID
                    if (updateId < 0 || updateId >= pets.size()) {
                        System.out.println("ID " + updateId + " is an invalid pet ID.");
                        break;
                    }

                    scnr.nextLine(); // Clear buffer

                    // Prompt for new name and age
                    System.out.printf("Update with (name, age): ");

                    // Get the new name and age and update pet
                    String updateInput = scnr.nextLine();

                    // Validate new pet data
                    if (isValidPetData(updateInput)) {
                        String[] updateNameAge = updateInput.split("\\s+");
                        String newName = updateNameAge[0];
                        int newAge = Integer.parseInt(updateNameAge[1]);
                        pets.get(updateId).setName(newName);
                        pets.get(updateId).setAge(newAge);
                        System.out.println("Pet updated successfully.");
                    } else {
                        System.out.println(updateInput + " is not valid pet data input.");
                    }
                    break;
                }

                // Remove an existing pet
                case 4: {
                    // Clear buffer
                    scnr.nextLine();

                    // Display current pets
                    displayPets(pets);

                    // Prompt for pet ID to remove and remove pet
                    System.out.printf("Enter the ID of the pet to remove: ");
                    int removeId = scnr.nextInt();
                    if (!isValidPetID(removeId, pets)) {
                        System.out.println("ID " + removeId + " is an invalid pet ID.");
                        continue;
                    }
                    pets.remove(removeId);
                    System.out.println("Pet removed successfully.");
                    break;
                }

                // Search pets by name
                case 5: {
                    // Clear buffer
                    scnr.nextLine();

                    // Prompt for name to search
                    System.out.printf("Enter name to search: ");
                    String searchName = scnr.nextLine();

                    int count = 0; // Row count

                    // Header
                    System.out.println("+-------------------------+");
                    System.out.println("| ID  | Name       | Age  |");
                    System.out.println("+-------------------------+");

                    // Insert matching pet data into table
                    for (int i = 0; i < pets.size(); i++) {
                        if (pets.get(i).getName().equalsIgnoreCase(searchName)) {
                            int id = i, age = pets.get(i).getAge();
                            String name = pets.get(i).getName();
                            System.out.printf("| %-3s | %-10s | %-4s |\n", id, name, age);
                            count++;
                        }
                    }
                    System.out.println("+-------------------------+");
                    System.out.println(count + " rows in set.");
                    break;
                }

                // Search pets by age
                case 6: {
                    // Clear buffer
                    scnr.nextLine();

                    // Prompt for age to search
                    System.out.printf("Enter age to search: ");
                    int searchAge = scnr.nextInt();

                    int count = 0; // Row count

                    // Header
                    System.out.println("+-------------------------+");
                    System.out.println("| ID  | Name       | Age  |");
                    System.out.println("+-------------------------+");

                    // Insert matching pet data into table
                    for (int i = 0; i < pets.size(); i++) {
                        if (pets.get(i).getAge() == searchAge) {
                            int id = i, age = pets.get(i).getAge();
                            String name = pets.get(i).getName();
                            System.out.printf("| %-3s | %-10s | %-4s |\n", id, name, age);
                            count++;
                        }
                    }
                    System.out.println("+-------------------------+");
                    System.out.println(count + " rows in set.");
                    break;
                }

                // Exit program
                case 7: {
                    System.out.println("Exiting program...");
                    break;
                }
            }

        }

        saveToPetsFile(pets);
        scnr.close();
    }

    public static void displayMenu() {
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
    }

    // Display pets in tabular format
    public static void displayPets(ArrayList<Pet> pets) {
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
    }

    // Pet data validator
    public static boolean isValidPetData(String nameAgeInput) {
        // Check for empty input
        if (nameAgeInput == null || nameAgeInput.trim().isEmpty()) {
            return false;
        }

        // Check if name or age is missing
        String[] nameAge = nameAgeInput.trim().split("\\s+");
        if (nameAge.length < 2) {
            return false;
        }

        // Check if age is a valid number
        int age;
        try {
            age = Integer.parseInt(nameAge[1]); // Check age is number
        } catch (NumberFormatException e) {
            return false;
        }

        // Check if age is between 1 and 20
        if (age < 1 || age > 20) {
            return false;
        }

        return true;
    }

    // ID validator
    public static boolean isValidPetID(int id, ArrayList<Pet> pets) {
        if (id < 0 || id >= pets.size()) {
            return false;
        }
        return true;
    }

    // Save into petsData.txt
    public static void saveToPetsFile(ArrayList<Pet> pets) throws IOException {
        FileOutputStream out = new FileOutputStream("petsData.txt");
        for (int i = 0; i < pets.size(); i++) {
            String pet = pets.get(i).getName() + " " + pets.get(i).getAge() + "\n";
            out.write(pet.getBytes());
        }
        out.close();
    }
}