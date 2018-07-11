package controller;

import domain.Country;
import service.CountryService;
import service.Utility;

import java.util.List;
import java.util.Scanner;

public class CountryController {

    private Scanner scanner = new Scanner(System.in);
    private CountryService countryService = new CountryService();

    public void showCountriesMenu() {
        while (true) {
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
                    Country country = selectCountry();
                    if (country != null) {
                        deleteCountry(country);
                    }
                    break;
                }
                case 3: {
                    Country country = selectCountry();
                    if (country != null) {
                        updateCountry(country);
                    }
                    break;
                }
                case 4: {
                    System.out.println("Countries in database:");
                    Utility.printList(countryService.getAllCountries());
                    break;
                }
                case 5: {
                    System.out.println("Countries sorted by name:");
                    Utility.printList(countryService.getSortedCountries());
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

    private void addCountry() {
        Country country = new Country();
        List<Country> countries = countryService.getAllCountries();
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

    private void deleteCountry(Country country) {
        countryService.deleteCountry(country);
        System.out.println(country.getName() + " deleted.");
    }

    private void updateCountry(Country country) {
        List<Country> countries  = countryService.getAllCountries();
        countries.remove(country);
        String str = country.getName();
        System.out.println("Found: " + str);
        System.out.print("\nEnter new country name: ");
        country.setName(scanner.next());
        if (countries.contains(country)) {
            System.out.println(country.getName() + " already exists.");
        } else {
            countryService.updateCountry(country);
            System.out.println(str + " updated to " + country.getName());
        }
    }

    public Country selectCountry() {
        Country country;
        List<Country> countries  = countryService.getAllCountries();
        Utility.printList(countries);
        System.out.print("\nEnter country name or ID: ");
        String str = scanner.next().trim();
        if (Utility.checkId(str)) {
            country = countryService.findCountryById(countries, Integer.valueOf(str));
        } else {
            country = countryService.findCountryByName(countries, str);
        }
        if (country == null) {
            System.out.println("Country not found.");
        }
        return country;
    }
}
