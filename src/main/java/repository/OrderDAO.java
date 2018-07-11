package repository;

import domain.Order;

import java.util.List;

public interface OrderDAO {

    int addOrder(Order order);

    void deleteOrder(Order order); // exception

    void updateOrder(Order order);

    List<Order> getAllOrders();

    List<Order> getOrdersByClientId(int id);

    Order getOrderByTourId(int id);

}
