package repository;

import domain.Country;
import domain.Tour;
import domain.TourType;

import java.util.List;

public interface TourDAO {

    int addTour(Tour tour);

    void deleteTour(Tour tour);

    void updateTour(Tour tour);

    List<Tour> getAllTours();

    List<Tour> getToursByCountry(Country country);

    List<Tour> getToursByType(TourType type);

}
