package repository;

import domain.Country;
import exception.CountryExistsException;

import java.util.List;

public interface CountryDAO {

    int addCountry(Country country); // throws CountryExistsException;

    void deleteCountry(Country country);

    void updateCountry(Country country);

    List<Country> getAllCountries();

    List<Country> getSortedCountries();

}
