package business.services;

import business.entities.Carport;
import business.entities.Order;
import business.exceptions.UserException;
import business.persistence.Database;
import business.persistence.OrderMapper;

import java.util.TreeMap;

public class OrderFacade {
    private OrderMapper orderMapper;

    public OrderFacade(Database database) {
        this.orderMapper = new OrderMapper(database);
    }

    public void insertOrder(Order order, Carport carport) throws UserException {
        orderMapper.insertOrder(order,carport);
    }
}
