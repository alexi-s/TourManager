package service;

import domain.Country;
import exception.CountryExistsException;
import repository.CountryDAO;
import repository.impl.CountryDAOH2Impl;

import java.util.List;

public class CountryService {

    private CountryDAO countryDAO = new CountryDAOH2Impl();
//    private List<Country> countries = getAllCountries();


    public int addCountry(Country country) {
        return countryDAO.addCountry(country);
    }

    public void deleteCountry(Country country) {
        countryDAO.deleteCountry(country);
    }

    public void updateCountry(Country country) {
        countryDAO.updateCountry(country);
    }

    public List<Country> getAllCountries() {
        return countryDAO.getAllCountries();
    }

    public List<Country> getSortedCountries() {
        return countryDAO.getSortedCountries();
    }
}
