package controller;

import domain.Client;
import domain.Order;
import service.ClientService;
import service.Utility;

import java.util.List;
import java.util.Scanner;

public class ClientController {

    private Scanner scanner = new Scanner(System.in);
    private ClientService clientService = new ClientService();

    public void showClientsMenu() {
        while (true) {
            System.out.println("\n[Clients] Choose item:");
            System.out.println("1. Add client");
            System.out.println("2. Delete client");
            System.out.println("3. Update client");
            System.out.println("4. View clients");
            System.out.println("5. Sort clients by last name");
            System.out.println("6. Find client by last name");
            System.out.println("\n0. Return to main menu");

            int choice;
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                scanner.next();
                choice = -1;
            }

            switch (choice) {
                case 1: {
                    addClient();
                    break;
                }
                case 2: {
                    deleteClient();
                    break;
                }
                case 3: {
                    updateClient();
                    break;
                }
                case 4: {
                    List<Client> clients = clientService.getAllClients();
                    System.out.println("Clients in database:");
                    Utility.printList(clients);
                    break;
                }
                case 5: {
                    getSortedClients();
                    break;
                }
                case 6: {
                    findClient();
                    break;
                }
                case 0: {
                    return;
                }
                default: {
                    System.out.println("Wrong choice! Please try again.");
                }
            }
        }
    }

    private void addClient() {
        Client client = new Client();
        List<Client> clients = clientService.getAllClients();
        System.out.print("\nEnter first name: ");
        client.setFirstName(scanner.next());
        System.out.print("Enter last name: ");
        client.setLastName(scanner.next());
        System.out.print("Enter age: ");
        client.setAge(scanner.nextInt()); // exception
        System.out.print("Enter phone number: ");
        client.setPhone(scanner.next());
        if (clients.contains(client)) {
            System.out.println(client.getFirstName() + " " + client.getLastName() + " already exists.");
        } else {
            clientService.addClient(client);
            System.out.println(client.getFirstName() + " " + client.getLastName() + " added.");
            clients.add(client);
        }
    }

    private void deleteClient() {
        System.out.println("Client deleted.");
    }

    private void updateClient() {
        System.out.println("Client updated.");
    }

    private void getSortedClients(){
        List<Client> clients = clientService.getSortedClients();
        System.out.println("Clients sorted by last name:");
        Utility.printList(clients);
    }

    private void findClient() {

    }
}
