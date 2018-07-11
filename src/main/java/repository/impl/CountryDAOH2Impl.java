package repository.impl;

import repository.CountryDAO;
import domain.Country;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static repository.impl.ConnectionFactory.getInstance;

public class CountryDAOH2Impl implements CountryDAO {

    private static final String CREATE_TABLE_COUNTRIES = "CREATE TABLE IF NOT EXISTS countries (" +
            "id INT(11) PRIMARY KEY AUTO_INCREMENT, " +
            "name VARCHAR(50) UNIQUE);";
    private static final String INSERT_COUNTRY = "INSERT INTO countries (name) VALUES (?)";
    private static final String DELETE_COUNTRY = "DELETE FROM countries WHERE id=?";
    private static final String UPDATE_COUNTRY = "UPDATE countries SET name=? WHERE id=?";
    private static final String GET_ALL_COUNTRIES = "SELECT * FROM countries";
    private static final String GET_SORTED_COUNTRIES = "SELECT * FROM countries ORDER BY name";
    private static final String GET_COUNTRY_BY_ID = "SELECT * FROM countries WHERE id=?";

    private Connection connection;
    private Statement stmt = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    public CountryDAOH2Impl() {
        try {
            connection = getInstance().getConnection();
            stmt = connection.createStatement();
            stmt.execute(CREATE_TABLE_COUNTRIES);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getInstance().closeStatement(stmt);
            getInstance().closeConnection(connection);
        }
    }

    @Override
    public int addCountry(Country country){ //throws CountryExistsException {
        int id = 0;
        try {
            connection = getInstance().getConnection();
            pst = connection.prepareStatement(INSERT_COUNTRY, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, country.getName());
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            rs.next();
            id = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getInstance().closeResultSet(rs);
            getInstance().closePreparedStatement(pst);
            getInstance().closeConnection(connection);
        }
        return id;
    }

    @Override
    public void deleteCountry(Country country) {
        try {
            connection = getInstance().getConnection();
            pst = connection.prepareStatement(DELETE_COUNTRY);
            pst.setInt(1, country.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getInstance().closePreparedStatement(pst);
            getInstance().closeConnection(connection);
        }
    }

    @Override
    public void updateCountry(Country country) {
        try {
            connection = getInstance().getConnection();
            pst = connection.prepareStatement(UPDATE_COUNTRY);
            pst.setString(1, country.getName());
            pst.setInt(2, country.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getInstance().closeStatement(pst);
            getInstance().closeConnection(connection);
        }
    }

    @Override
    public List<Country> getAllCountries() {
        List<Country> countries = new ArrayList<>();
        try {
            connection = getInstance().getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(GET_ALL_COUNTRIES);
            if (rs != null) {
                while (rs.next()) {
                    countries.add(new Country(rs.getInt("id"), rs.getString("name")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getInstance().closeResultSet(rs);
            getInstance().closeStatement(stmt);
            getInstance().closeConnection(connection);
        }
        return countries;
    }

    @Override
    public List<Country> getSortedCountries() {
        List<Country> countries = new ArrayList<>();
        try {
            connection = getInstance().getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(GET_SORTED_COUNTRIES);
            if (rs != null) {
                while (rs.next()) {
                    countries.add(new Country(rs.getInt("id"), rs.getString("name")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getInstance().closeResultSet(rs);
            getInstance().closeStatement(stmt);
            getInstance().closeConnection(connection);
        }
        return countries;
    }

}
