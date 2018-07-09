package controller;

import domain.*;
import service.CountryService;
import service.TourService;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainController {

    private final static String REGEX_ID = "(.*)(\\d+)";

    private CountryService countryService = new CountryService();
    private TourService tourService = new TourService();

    private List<Country> countries;
    private List<Tour> tours;
    private List<Client> clients;
    private List<Order> orders;

    private Scanner scanner = new Scanner(System.in);
    private boolean isMenuRepeat;

    public void doWork() {
        while (true) {
            mainMenu();
        }
    }

    private void mainMenu() {
        isMenuRepeat = true;
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
                while (isMenuRepeat) {
                    showToursMenu();
                }
                break;
            }
            case 2: {
                while (isMenuRepeat) {
                    showCountriesMenu();
                }
                break;
            }
            case 3: {
                while (isMenuRepeat) {
                    showClientsMenu();
                }
                break;
            }
            case 4: {
                while (isMenuRepeat) {
                    showOrdersMenu();
                }
                break;
            }
            case 0: {
                System.out.println("Goodbye!");
                System.exit(0);
            }
            default: {
                System.out.println("Wrong choice! Please try again.");
            }
        }
        System.out.println("\n------------------------------------------");
    }
    // ----- Tours menu -----------------------------------------------

    private void showToursMenu() {
        isMenuRepeat = true;
        System.out.println("\n[Tours] Choose item:");
        System.out.println("1. Add tour");
        System.out.println("2. Delete tour");
        System.out.println("3. Update tour");
        System.out.println("4. View tours");
        System.out.println("5. Find tour by country");
        System.out.println("6. Find tour by type");
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
                addTour();
                break;
            }
            case 2: {
                break;
            }
            case 3: {
                break;
            }
            case 4: {
                System.out.println("Tours in database:");
                printList(tourService.getAllTours());
                break;
            }
            case 5: {
                break;
            }
            case 6: {
                printList(tourService.getToursByType(selectTourType()));
                break;
            }
            case 0: {
                isMenuRepeat = false;
                return;
            }
            default: {
                System.out.println("Wrong choice! Please try again.");
            }
        }
    }
    // ----- Countries menu -------------------------------------------

    private void showCountriesMenu() {
        isMenuRepeat = true;
        System.out.println("\n[Countries] Choose item:");
        System.out.println("1. Add country");
        System.out.println("2. Delete country");
        System.out.println("3. Update country");
        System.out.println("4. View countries");
        System.out.println("5. Sort countries by name");
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
                addCountry();
                break;
            }
            case 2: {
                deleteCountry();
                break;
            }
            case 3: {
                updateCountry();
                break;
            }
            case 4: {
                System.out.println("Countries in database:");
                printList(countryService.getAllCountries());
                break;
            }
            case 5: {
                System.out.println("Countries sorted by name:");
                printList(countryService.getSortedCountries());
                break;
            }
            case 0: {
                isMenuRepeat = false;
                return;
            }
            default: {
                System.out.println("Wrong choice! Please try again.");
            }
        }
    }
    // ----- Clients menu ---------------------------------------------

    private void showClientsMenu() {
        isMenuRepeat = true;
        System.out.println("\n[Clients] Choose item:");
        System.out.println("1. Add client");
        System.out.println("2. Delete client");
        System.out.println("3. Update client");
        System.out.println("4. View clients");
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
                break;
            }
            case 2: {
                break;
            }
            case 3: {
                break;
            }
            case 4: {
                break;
            }
            case 0: {
                isMenuRepeat = false;
                return;
            }
            default: {
                System.out.println("Wrong choice! Please try again.");
            }
        }
    }
    // ----- Orders menu ----------------------------------------------

    private void showOrdersMenu() {
        isMenuRepeat = true;
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
                break;
            }
            case 2: {
                break;
            }
            case 3: {
                break;
            }
            case 4: {
                break;
            }
            case 0: {
                isMenuRepeat = false;
                return;
            }
            default: {
                System.out.println("Wrong choice! Please try again.");
            }
        }
    }
    // ----- Countries ------------------------------------------------

    private void addCountry() {
        Country country = new Country();
        countries = countryService.getAllCountries();
        System.out.print("\nEnter country name: ");
        country.setName(scanner.next());
        if (countries.contains(country)) {
            System.out.println(country.getName() + " already exists.");
        } else {
            countryService.addCountry(country);
            System.out.println(country.getName() + " added.");
            countries.add(country);
        }
    }

    private void deleteCountry() {
        Country country;
        countries = countryService.getAllCountries();
        System.out.print("\nEnter country name or ID to delete: ");
        String str = scanner.next();
        if (checkId(str)) {
            country = findById(countries, Integer.valueOf(str));
        } else {
            country = findByName(countries, str);
        }
        if (country == null) {
            System.out.println("Country not found.");
        } else {
            countryService.deleteCountry(country);
            System.out.println(country.getName() + " deleted.");
            countries.remove(country);
        }
    }

    private void updateCountry() {
        Country country;
        countries = countryService.getAllCountries();
        System.out.print("\nEnter country name or ID to update: ");
        String str = scanner.next();
        if (checkId(str)) {
            country = findById(countries, Integer.valueOf(str));
        } else {
            country = findByName(countries, str);
        }
        if (country == null) {
            System.out.println("Country not found.");
        } else {
            countries.remove(country);
            str = country.getName();
            System.out.println("\nFound: " + str);
            System.out.print("Enter new country name: ");
            country.setName(scanner.next());
            if(countries.contains(country)) {
                System.out.println(country.getName() + " already exists.");
            } else {
                countryService.updateCountry(country);
                System.out.println(str + " updated to " + country.getName());
            }
        }
    }

    private Country findByName(List<Country> countries, String name) {
        Country country = null;
        Iterator<Country> iterator = countries.iterator();
        while (iterator.hasNext()) {
            country = iterator.next();
            if (country.getName().equals(name)) {
                break;
            }
            country = null;
        }
        return country;
    }

    private Country findById(List<Country> countries, int id) {
        Country country = null;
        Iterator<Country> iterator = countries.iterator();
        while (iterator.hasNext()) {
            country = iterator.next();
            if (country.getId() == id) {
                break;
            }
            country = null;
        }
        return country;
    }
    // ----- Tours ---------- -----------------------------------------

    private void addTour() {
        Tour tour = new Tour();
        List<Country> userCountries = new ArrayList<>();
        List<Country> newCountries = new ArrayList<>();
        countries = countryService.getAllCountries();
        System.out.print("\nEnter tour name: ");
        tour.setName(scanner.next());
        tour.setType(selectTourType());
        System.out.print("\nEnter country: ");
        String str = scanner.next();

        Country country = findByName(countries, str);
        if(country == null) {
            country = new Country(str);
            int id = countryService.addCountry(country);
            country.setId(id);
        }

        userCountries.add(country);
        tour.setCountries(userCountries);
        tourService.addTour(tour);
        System.out.println("Tour added.");
    }

    private TourType selectTourType() {
        TourType type = null;
        int choice = 0;

        while (choice < 1 || choice > 6){
            int i = 1;
            System.out.println("\nSelect tour type: ");
            for (TourType tourType : TourType.values()) {
                System.out.println((i++) + ". " + tourType);
            }
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                scanner.next();
                choice = -1;
            }
            switch (choice) {
                case 1: {
                    type = TourType.BEACH;
                    break;
                }
                case 2: {
                    type = TourType.CRUISE;
                    break;
                }
                case 3: {
                    type = TourType.EXCURSION;
                    break;
                }
                case 4: {
                    type = TourType.HOLIDAYS;
                    break;
                }
                case 5: {
                    type = TourType.WEDDING;
                    break;
                }
                case 6: {
                    type = TourType.EXTREME;
                    break;
                }
                default: {
                    System.out.println("Wrong choice! Please try again.");
                }
            }
        }
        return type;
    }

    // ----- Common utility methods -----------------------------------

    private boolean checkId(String str) {
        Pattern r = Pattern.compile(REGEX_ID);
        Matcher m = r.matcher(str);
        return m.matches();
    }

    private <T> void printList(T collection) {
        List list = (List) collection;
        if(list.size() == 0){
            System.out.println("No records.");
        } else {
            for (Object item : list) {
                System.out.println("\t" + item);
            }
        }
    }
}
