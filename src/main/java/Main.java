import controller.MainController;
import domain.*;
import service.CountryService;
import service.TourService;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("Welcome to the Tour Manager!");

        MainController mainController = new MainController();
        mainController.doWork();


//        CountryService countryService = new CountryService();
//        TourService tourService = new TourService();
//
//
//        countryService.addCountry(new Country(1,"Ukraine"));
//        List<Country> countries = countryService.getAllCountries();
//        countries.forEach(System.out::println);
////
//        List<Country> listCountries =  new ArrayList<>();
//        listCountries.add(new Country(1,"Ukraine"));
//        listCountries.add(new Country(2,"USA"));
//
//        Tour t1 = new Tour(1, "Odessa", TourType.BEACH, listCountries, null, 7, 3599.99f);
//        tourService.addTour(t1);
//
//        System.out.println(tourService.getAllTours());
//
//        tourService.updateTour(null);
    }
}
