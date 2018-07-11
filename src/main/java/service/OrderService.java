package service;

import domain.Order;
import repository.OrderDAO;
import repository.impl.OrderDAOH2Impl;

import java.util.List;

public class OrderService {

    private OrderDAO orderDAO = new OrderDAOH2Impl();

    public int addOrder(Order order) {
        return orderDAO.addOrder(order);
    }

    public void deleteOrder(Order order) {
        orderDAO.deleteOrder(order);
    }

    public void updateOrder(Order order) {
        orderDAO.updateOrder(order);
    }

    public List<Order> getAllOrders() {
        return orderDAO.getAllOrders();
    }

    public List<Order> getOrdersByClientId(int id) {
        return orderDAO.getOrdersByClientId(id);
    }

    public Order getOrderByTourId(int id) {
        return orderDAO.getOrderByTourId(id);
    }
}
