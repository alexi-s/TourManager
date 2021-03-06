package repository.impl;

import domain.Country;
import domain.Tour;
import domain.TourType;
import repository.TourDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static repository.impl.ConnectionFactory.getInstance;

public class TourDAOH2Impl implements TourDAO {

    private static final String CREATE_TABLE_TOURS = "CREATE TABLE IF NOT EXISTS tours (" +
            "id INT(11) PRIMARY KEY AUTO_INCREMENT, " +
            "name VARCHAR(100) UNIQUE, " +
            "type VARCHAR(100), " +
            "duration INT(11), " +
            "price FLOAT(5.2));";
    private static final String CREATE_TABLE_TOUR_COUNTRIES = "CREATE TABLE IF NOT EXISTS tour_countries (" +
            "id INT(11) PRIMARY KEY AUTO_INCREMENT, " +
            "tour_id INT(11), " +
            "country_id INT(11) REFERENCES countries(id));";

    private static final String INSERT_TOUR = "INSERT INTO tours (name, type, duration, price) VALUES (?, ?, ?, ?)";
    private static final String DELETE_TOUR = "DELETE FROM tours WHERE ID=?";
    private static final String UPDATE_TOUR = "UPDATE tours SET name=?, type=?, duration=?, price=? WHERE id=?";
    private static final String GET_ALL_TOURS = "SELECT * FROM tours";

    private static final String INSERT_TOUR_COUNTRIES = "INSERT INTO tour_countries (tour_id, country_id) VALUES (?, ?)";
    private static final String DELETE_TOUR_COUNTRIES = "DELETE FROM tour_countries WHERE tour_id=?";
    private static final String GET_TOUR_COUNTRIES = "SELECT * FROM tour_countries LEFT JOIN countries ON tour_countries.country_id=countries.id WHERE tour_id=?";
    private static final String GET_TOURS_BY_COUNTRY = "SELECT * FROM tour_countries LEFT JOIN tours ON tour_countries.tour_id=tours.id WHERE country_id=?";
    private static final String GET_TOURS_BY_TYPE = "SELECT * FROM tours WHERE type=?";

    private Connection connection;
    private Statement stmt = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    public TourDAOH2Impl() {
        try {
            connection = getInstance().getConnection();
            stmt = connection.createStatement();
            stmt.execute(CREATE_TABLE_TOURS);
            stmt.execute(CREATE_TABLE_TOUR_COUNTRIES);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getInstance().closeStatement(stmt);
            getInstance().closeConnection(connection);
        }
    }

    @Override
    public int addTour(Tour tour) {
        int id = 0;
        List<Country> countries = tour.getCountries();
        try {
            connection = getInstance().getConnection();
            pst = connection.prepareStatement(INSERT_TOUR, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, tour.getName());
            pst.setString(2, tour.getType().toString());
            pst.setInt(3, tour.getDuration());
            pst.setFloat(4, tour.getPrice());
            pst.executeUpdate();

            rs = pst.getGeneratedKeys();
            rs.next();
            id = rs.getInt("id");

            pst = connection.prepareStatement(INSERT_TOUR_COUNTRIES);
            pst.setInt(1, id);
            Iterator<Country> iterator = countries.iterator();
            while (iterator.hasNext()) {
                Country country = iterator.next();
                pst.setInt(2, country.getId());
                pst.executeUpdate();
            }

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
    public void deleteTour(Tour tour) {
        try {
            connection = getInstance().getConnection();
            pst = connection.prepareStatement(DELETE_TOUR);
            pst.setInt(1, tour.getId());
            pst.executeUpdate();
            pst = connection.prepareStatement(DELETE_TOUR_COUNTRIES);
            pst.setInt(1, tour.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getInstance().closePreparedStatement(pst);
            getInstance().closeConnection(connection);
        }
    }

    @Override
    public void updateTour(Tour tour) {
        List<Country> countries = tour.getCountries();
        try {
            connection = getInstance().getConnection();
//            UPDATE tours SET name=?, type=?, duration=?, price=? WHERE id=?
            pst = connection.prepareStatement(UPDATE_TOUR);
            pst.setString(1, tour.getName());
            pst.setString(2, tour.getType().toString());
            pst.setInt(3, tour.getDuration());
            pst.setFloat(4, tour.getPrice());
            pst.setInt(5, tour.getId());
            pst.executeUpdate();

            int id = tour.getId();
            pst = connection.prepareStatement(DELETE_TOUR_COUNTRIES);
            pst.setInt(1, id);
            pst.executeUpdate();

            pst = connection.prepareStatement(INSERT_TOUR_COUNTRIES);
            pst.setInt(1, id);
            Iterator<Country> iterator = countries.iterator();
            while (iterator.hasNext()) {
                Country country = iterator.next();
                pst.setInt(2, country.getId());
                pst.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getInstance().closePreparedStatement(pst);
            getInstance().closeConnection(connection);
        }
    }

    @Override
    public List<Tour> getAllTours() {
        List<Tour> tours = new ArrayList<>();
        try {
            connection = getInstance().getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(GET_ALL_TOURS);
            if (rs != null) {
                while (rs.next()) {
                    Tour tour = new Tour();
                    tour.setId(rs.getInt("id"));
                    tour.setName(rs.getString("name"));
                    tour.setType(TourType.valueOf(rs.getString("type")));
                    tour.setDuration(rs.getInt("duration"));
                    tour.setPrice(rs.getFloat("price"));
                    tours.add(tour);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getInstance().closeResultSet(rs);
            getInstance().closeStatement(stmt);
            getInstance().closeConnection(connection);
        }
        return tours;
    }

    @Override
    public List<Tour> getToursByCountry(Country country) {
        List<Tour> tours = new ArrayList<>();
        try {
            connection = getInstance().getConnection();
            pst = connection.prepareStatement(GET_TOURS_BY_COUNTRY);
            pst.setInt(1, country.getId());
            rs = pst.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Tour tour = new Tour();
                    tour.setId(rs.getInt(4));
                    tour.setName(rs.getString("name"));
                    tour.setType(TourType.valueOf(rs.getString("type")));
                    tour.setDuration(rs.getInt("duration"));
                    tour.setPrice(rs.getFloat("price"));
                    tours.add(tour);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getInstance().closeResultSet(rs);
            getInstance().closePreparedStatement(pst);
            getInstance().closeConnection(connection);
        }
        return tours;
    }

    @Override
    public List<Tour> getToursByType(TourType type) {
        List<Tour> tours = new ArrayList<>();
        try {
            connection = getInstance().getConnection();
            pst = connection.prepareStatement(GET_TOURS_BY_TYPE);
            pst.setString(1, type.toString());
            rs = pst.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Tour tour = new Tour();
                    tour.setId(rs.getInt("id"));
                    tour.setName(rs.getString("name"));
                    tour.setType(TourType.valueOf(rs.getString("type")));
                    tour.setDuration(rs.getInt("duration"));
                    tour.setPrice(rs.getFloat("price"));

//                    List<Country> countries = getCountriesByTourId(rs.getInt("id"));
//                    tour.setCountries(countries);

                    tours.add(tour);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getInstance().closeResultSet(rs);
            getInstance().closePreparedStatement(pst);
            getInstance().closeConnection(connection);
        }
        return tours;
    }

    public List<Country> getCountriesByTourId(int id) {
        List<Country> countries = new ArrayList<>();
        try {
            connection = getInstance().getConnection();
            pst = connection.prepareStatement(GET_TOUR_COUNTRIES);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Country country = new Country();
                    countries.add(new Country(rs.getInt(4), rs.getString("name")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getInstance().closeResultSet(rs);
            getInstance().closePreparedStatement(pst);
        }
        return countries;
    }
}
