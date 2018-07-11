package repository.impl;

import domain.Order;
import repository.OrderDAO;

import java.sql.*;
import java.util.List;

import static repository.impl.ConnectionFactory.getInstance;

public class OrderDAOH2Impl implements OrderDAO {

    private static final String CREATE_TABLE_ORDERS = "CREATE TABLE IF NOT EXISTS orders (" +
            "id INT(11) PRIMARY KEY AUTO_INCREMENT, " +
            "client_id INT(11) REFERENCES clients(id)" +
            "tour_id INT(11) REFERENCES tours(id));";

    private static final String INSERT_CLIENT_ORDER = "INSERT INTO orders (client_id, order_id) VALUES (?, ?)";
    private static final String DELETE_ORDER = "DELETE FROM orders WHERE id=?";
    private static final String GET_ALL_ORDERS = "SELECT * FROM orders";

    private Connection connection;
    private Statement stmt = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    public OrderDAOH2Impl() {
        try {
            connection = getInstance().getConnection();
            stmt = connection.createStatement();
            stmt.execute(CREATE_TABLE_ORDERS);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getInstance().closeStatement(stmt);
            getInstance().closeConnection(connection);
        }

    }

    @Override
    public int addOrder(Order order) {
        return 0;
    }

    @Override
    public void deleteOrder(Order order) {
        try {
            connection = getInstance().getConnection();
            pst = connection.prepareStatement(DELETE_ORDER);
            pst.setInt(1, order.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getInstance().closePreparedStatement(pst);
            getInstance().closeConnection(connection);
        }
    }

    @Override
    public void updateOrder(Order order) {

    }

    @Override
    public List<Order> getAllOrders() {
        return null;
    }

    @Override
    public List<Order> getOrdersByClientId(int id) {
        return null;
    }

    @Override
    public Order getOrderByTourId(int id) {
        return null;
    }
}
