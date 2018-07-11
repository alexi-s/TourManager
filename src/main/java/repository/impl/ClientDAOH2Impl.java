package repository.impl;

import domain.Client;
import repository.ClientDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static repository.impl.ConnectionFactory.getInstance;

public class ClientDAOH2Impl implements ClientDAO {

    private static final String CREATE_TABLE_CLIENTS = "CREATE TABLE IF NOT EXISTS clients (" +
            "id INT(11) PRIMARY KEY AUTO_INCREMENT, " +
            "first_name VARCHAR(50), " +
            "last_name VARCHAR(50), " +
            "age INT(3), " +
            "phone VARCHAR(50));";

    private static final String INSERT_CLIENT = "INSERT INTO clients (first_name, last_name, age, phone) VALUES (?, ?, ?, ?)";
    private static final String DELETE_CLIENT = "DELETE FROM clients WHERE id=?";
    private static final String UPDATE_CLIENT = "UPDATE clients SET (first_name=?, last_name=?, age=?, phone=?) WHERE id=?";
    private static final String GET_ALL_CLIENTS = "SELECT * FROM clients";
    private static final String GET_SORTED_CLIENTS = "SELECT * FROM clients ORDER BY last_name";
    private static final String GET_CLIENT_BY_ID = "SELECT * FROM clients WHERE id=?";
    private static final String GET_CLIENT_BY_LAST_NAME = "SELECT * FROM clients WHERE last_name=?";

    private Connection connection;
    private Statement stmt = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    public ClientDAOH2Impl() {
        try {
            connection = getInstance().getConnection();
            stmt = connection.createStatement();
            stmt.execute(CREATE_TABLE_CLIENTS);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getInstance().closeStatement(stmt);
            getInstance().closeConnection(connection);
        }
    }

    @Override
    public int addClient(Client client) {
        int id = 0;
        try {
            connection = getInstance().getConnection();
            pst = connection.prepareStatement(INSERT_CLIENT, Statement.RETURN_GENERATED_KEYS);
//            INSERT INTO clients (first_name, last_name, age, phone) VALUES (?, ?, ?, ?)
            pst.setString(1, client.getFirstName());
            pst.setString(2, client.getLastName());
            pst.setInt(3, client.getAge());
            pst.setString(4, client.getPhone());
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
    public void deleteClient(Client client) {
        try {
            connection = getInstance().getConnection();
            pst = connection.prepareStatement(DELETE_CLIENT);
            pst.setInt(1, client.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getInstance().closePreparedStatement(pst);
            getInstance().closeConnection(connection);
        }
    }

    @Override
    public void updateClient(Client client) {
        try {
            connection = getInstance().getConnection();
            pst = connection.prepareStatement(UPDATE_CLIENT);
//            UPDATE clients SET (first_name=?, last_name=?, age=?, phone=?) WHERE id=?
            pst.setString(1, client.getFirstName());
            pst.setString(2, client.getLastName());
            pst.setInt(3, client.getAge());
            pst.setString(4, client.getPhone());
            pst.setInt(5, client.getId());
            pst.executeUpdate();
            // how about orders?
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getInstance().closePreparedStatement(pst);
            getInstance().closeConnection(connection);
        }
    }

    @Override
    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        try {
            connection = getInstance().getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(GET_ALL_CLIENTS);
            if (rs != null) {
                while (rs.next()) {
                    Client client = new Client();
                    client = client.toBuilder()
                            .setId(rs.getInt("id"))
                            .setFirstName(rs.getString("first_name"))
                            .setLastName(rs.getString("last_name"))
                            .setAge(rs.getInt("age"))
                            .setPhone(rs.getString("phone"))
                            .build();
                    clients.add(client);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getInstance().closeResultSet(rs);
            getInstance().closeStatement(stmt);
            getInstance().closeConnection(connection);
        }
        return clients;
    }

    @Override
    public List<Client> getClientsSortedByLastName() {
        List<Client> clients = new ArrayList<>();
        try {
            connection = getInstance().getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(GET_SORTED_CLIENTS);
            if (rs != null) {
                while (rs.next()) {
                    Client client = new Client();
                    client = client.toBuilder()
                            .setId(rs.getInt("id"))
                            .setFirstName(rs.getString("first_name"))
                            .setLastName(rs.getString("last_name"))
                            .setAge(rs.getInt("age"))
                            .setPhone(rs.getString("phone"))
                            .build();
                    clients.add(client);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getInstance().closeResultSet(rs);
            getInstance().closeStatement(stmt);
            getInstance().closeConnection(connection);
        }
        return clients;
    }

    @Override
    public Client getClientById(int id) {
        Client client = null;
        try {
            connection = getInstance().getConnection();
            pst = connection.prepareStatement(GET_CLIENT_BY_ID);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (rs != null) {
                rs.next();
                client = new Client().toBuilder()
                        .setId(rs.getInt("id"))
                        .setFirstName(rs.getString("first_name"))
                        .setLastName(rs.getString("last_name"))
                        .setAge(rs.getInt("age"))
                        .setPhone(rs.getString("phone"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getInstance().closeResultSet(rs);
            getInstance().closeStatement(pst);
            getInstance().closeConnection(connection);
        }
        return client;
    }


    @Override
    public Client getClientByLastName(String lastName) {
        Client client = null;
        try {
            connection = getInstance().getConnection();
            pst = connection.prepareStatement(GET_CLIENT_BY_LAST_NAME);
            pst.setString(1, lastName);
            rs = pst.executeQuery();
            if (rs != null) {
                rs.next();
                client = new Client().toBuilder()
                        .setId(rs.getInt("id"))
                        .setFirstName(rs.getString("first_name"))
                        .setLastName(rs.getString("last_name"))
                        .setAge(rs.getInt("age"))
                        .setPhone(rs.getString("phone"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getInstance().closeResultSet(rs);
            getInstance().closeStatement(pst);
            getInstance().closeConnection(connection);
        }
        return client;
    }

}
