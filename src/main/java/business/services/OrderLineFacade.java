package business.services;

import business.entities.OrderLine;
import business.exceptions.UserException;
import business.persistence.Database;
import business.persistence.OrderLineMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class OrderLineFacade {
    OrderLineMapper orderLineMapper;

    public OrderLineFacade(Database database) {
        this.orderLineMapper = new OrderLineMapper(database);
    }

    public boolean insertOrderLine (OrderLine orderLine) throws UserException, SQLException {
        return orderLineMapper.insertOrderLine(orderLine);
    }

    public TreeMap<Integer, OrderLine> getOrderLinesByOrderId(int orderId) throws UserException {
        return orderLineMapper.getOrderLinesByOrderId(orderId);
    }
    public int deleteOrderLine(List<Integer> deleteIds) throws UserException {
        return orderLineMapper.deleteOrderLine(deleteIds);
    }
}
