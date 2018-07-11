package service;

import domain.Country;
import exception.CountryExistsException;
import repository.CountryDAO;
import repository.impl.CountryDAOH2Impl;

import java.util.Iterator;
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

    public Country findCountryByName(List<Country> countries, String name) {
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

    public Country findCountryById(List<Country> countries, int id) {
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
}