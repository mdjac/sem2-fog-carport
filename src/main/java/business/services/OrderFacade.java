package business.services;

import business.entities.Carport;
import business.entities.Order;
import business.entities.User;
import business.exceptions.UserException;
import business.persistence.Database;
import business.persistence.OrderMapper;

import java.util.TreeMap;

public class OrderFacade {
    private OrderMapper orderMapper;

    public OrderFacade(Database database) {
        this.orderMapper = new OrderMapper(database);
    }

    public Order insertOrder(Order order, Carport carport) throws UserException {
        return orderMapper.insertOrder(order,carport);
    }

    public TreeMap<Integer,Order> getOrders(User user) throws UserException {
        return orderMapper.getOrders(user);
    }

    public Order getOrderByOrderId(int inputOrderId) throws UserException {
        return orderMapper.getOrderByOrderId(inputOrderId);
    }

    public int updateOrderTotalPrice(int orderId, double totalPrice) throws UserException {
        return orderMapper.updateOrderTotalPrice(orderId,totalPrice);
    }
}
