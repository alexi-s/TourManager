package controller;

import java.util.Scanner;

public class MainController {

    private TourController tourController = new TourController();
    private CountryController countryController = new CountryController();
    private OrderController orderController = new OrderController();
    private ClientController clientController = new ClientController();
    private Scanner scanner = new Scanner(System.in);

    public void doWork() {
        while (true) {
            mainMenu();
        }
    }

    private void mainMenu() {
        System.out.println("\n[Main menu] Choose you want to work with: ");
        System.out.println("1. Tours");
        System.out.println("2. Countries");
        System.out.println("3. Clients");
        System.out.println("4. Orders");
        System.out.println("\n0. Exit");

        int choice;
        try {
            choice = scanner.nextInt();
        } catch (Exception e) {
            scanner.next();
            choice = -1;
        }

        switch (choice) {
            case 1: {
                tourController.showToursMenu();
                break;
            }
            case 2: {
                countryController.showCountriesMenu();
                break;
            }
            case 3: {
                clientController.showClientsMenu();
                break;
            }
            case 4: {
                orderController.showOrdersMenu();
                break;
            }
            case 0: {
                scanner.close();
                System.out.println("Goodbye!");
                System.exit(0);
            }
            default: {
                System.out.println("Wrong choice! Please try again.");
            }
        }
        System.out.println("\n------------------------------------------");
    }
}

