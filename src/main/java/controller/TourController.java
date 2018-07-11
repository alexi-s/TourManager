package controller;

import domain.Country;
import domain.Tour;
import domain.TourType;
import service.CountryService;
import service.TourService;
import service.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TourController {

    private Scanner scanner = new Scanner(System.in);
    private CountryService countryService = new CountryService();
    private TourService tourService = new TourService();

    public void showToursMenu() {
        while (true) {
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
                    Tour tour = selectTour();
                    if (tour != null) {
                        deleteTour(tour);
                    }
                    break;
                }
                case 3: {
                    Tour tour = selectTour();
                    if (tour != null) {
                        updateTour(tour);
                    }
                    break;
                }
                case 4: {
                    System.out.println("Tours in database:");
                    Utility.printList(getAllTours());
                    break;
                }
                case 5: {
                    Country country = new CountryController().selectCountry();
                    if (country != null) {
                        System.out.println("Tours to " + country.getName() + ":");
                        Utility.printList(getToursByCountry(country));
                    }
                    break;
                }
                case 6: {
                    TourType type = selectTourType();
                    System.out.println("Tours by type " + type + ":");
                    Utility.printList(getToursByType(type));
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

    private void addTour() {
        scanner = scanner.useDelimiter("\n");

        Tour tour = new Tour();
        List<Country> countries = countryService.getAllCountries();
        List<Country> userCountries = new ArrayList<>();
        System.out.print("\nEnter tour name: ");
        tour.setName(scanner.next());
        tour.setType(selectTourType());

        System.out.print("\nEnter country (or countries): ");
        String str = scanner.next();
        for (String s : Utility.stringParser(str)) {
            Country country = countryService.findCountryByName(countries, s);
            if (country == null) {
                country = new Country(s);
                int id = countryService.addCountry(country);
                country.setId(id);
                countries.add(country);
            }
            userCountries.add(country);
        }
        tour.setCountries(userCountries);
        tourService.addTour(tour);
        System.out.println("Tour added.");
    }

    private void deleteTour(Tour tour) {
        tourService.deleteTour(tour);
        System.out.println(tour.getName() + " deleted.");
    }

    private void updateTour(Tour tour) {
        List<Tour> tours = getAllTours();
        tours.remove(tour);
        String str = tour.getName();
        System.out.println("Found: " + str);
        System.out.print("\nEnter new country name: ");
        tour.setName(scanner.next());
        if (tours.contains(tour)) {
            System.out.println(tour.getName() + " already exists.");
        } else {
            tourService.updateTour(tour);
            System.out.println(str + " updated to " + tour.getName());
        }
    }

    private List<Tour> getAllTours() {
        List<Tour> tours = tourService.getAllTours();
        tours.forEach(tour -> {
            tour.setCountries(tourService.getCountriesByTourId(tour.getId()));
        });
        return tours;
    }

    private List<Tour> getToursByCountry(Country country) {
        List<Tour> tours = tourService.getToursByCountry(country);
        tours.forEach(tour -> {
            tour.setCountries(tourService.getCountriesByTourId(tour.getId()));
        });
        return tours;
    }

    private List<Tour> getToursByType(TourType type) {
        List<Tour> tours = tourService.getToursByType(type);
        tours.forEach(tour -> {
            tour.setCountries(tourService.getCountriesByTourId(tour.getId()));
        });
        return tours;
    }

    private Tour selectTour() {
        Tour tour = null;
        List<Tour> tours = getAllTours();
        Utility.printList(tours);
        System.out.print("\nEnter tour name or ID: ");
        String str = scanner.next();
        if (Utility.checkId(str)) {
            tour = tourService.findTourById(tours, Integer.valueOf(str));
        } else {
            tour = tourService.findTourByName(tours, str);
        }
        if (tour == null) {
            System.out.println("Tour not found.");
        }
        return tour;
    }

    private TourType selectTourType() {
        TourType type = null;
        int choice = 0;

        while (choice < 1 || choice > TourType.values().length) {
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
}
