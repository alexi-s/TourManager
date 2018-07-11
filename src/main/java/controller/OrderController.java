package controller;

import domain.Order;
import service.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderController {

    private Scanner scanner = new Scanner(System.in);
    private TourController tourController = new TourController();

    public void showOrdersMenu() {
        while (true) {
            System.out.println("\n[Orders] Choose item:");
            System.out.println("1. Add order");
            System.out.println("2. Delete order");
            System.out.println("3. Update order");
            System.out.println("4. View orders");
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
                    addOrder();
                    break;
                }
                case 2: {
                    deleteOrder();
                    break;
                }
                case 3: {
                    updateOrder();
                    break;
                }
                case 4: {
                    List<Order> orders = new ArrayList<>();
                    System.out.println("Orders in database:");
                    Utility.printList(orders);
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

    private void addOrder() {
        System.out.println("Order added.");
    }

    private void deleteOrder() {
        System.out.println("Order deleted.");
    }

    private void updateOrder() {
        System.out.println("Order updated.");
    }

}