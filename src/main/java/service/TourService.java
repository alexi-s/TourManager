package service;

import domain.Tour;
import repository.TourDAO;
import repository.impl.TourDAOH2Impl;

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
}
