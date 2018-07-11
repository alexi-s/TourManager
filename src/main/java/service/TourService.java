package service;

import domain.Country;
import domain.Tour;
import domain.TourType;
import repository.TourDAO;
import repository.impl.TourDAOH2Impl;

import java.util.Iterator;
import java.util.List;

public class TourService {

    private TourDAO tourDAO = new TourDAOH2Impl();

    public int addTour(Tour tour) {
        return tourDAO.addTour(tour);
    }

    public void deleteTour(Tour tour) {
        tourDAO.deleteTour(tour);
    }

    public void updateTour(Tour tour) {
        tourDAO.updateTour(tour);
    }

    public List<Tour> getAllTours() {
        return tourDAO.getAllTours();
    }

    public List<Tour> getToursByCountry(Country country) {
        return tourDAO.getToursByCountry(country);
    }

    public List<Tour> getToursByType(TourType type) {
        return tourDAO.getToursByType(type);
    }

    public List<Country> getCountriesByTourId(int id){
        return tourDAO.getCountriesByTourId(id);
    }

    public Tour findTourByName(List<Tour> tours, String name) {
        Tour tour = null;
        Iterator<Tour> iterator = tours.iterator();
        while (iterator.hasNext()) {
            tour = iterator.next();
            if (tour.getName().equals(name)) {
                break;
            }
            tour = null;
        }
        return tour;
    }

    public Tour findTourById(List<Tour> tours, int id) {
        Tour tour = null;
        Iterator<Tour> iterator = tours.iterator();
        while (iterator.hasNext()) {
            tour = iterator.next();
            if (tour.getId() == id) {
                break;
            }
            tour = null;
        }
        return tour;
    }
}
