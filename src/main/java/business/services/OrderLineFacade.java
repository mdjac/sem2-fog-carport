package business.services;

import business.entities.OrderLine;
import business.exceptions.UserException;
import business.persistence.Database;
import business.persistence.OrderLineMapper;

import java.sql.SQLException;

public class OrderLineFacade {
    OrderLineMapper orderLineMapper;

    public OrderLineFacade(Database database) {
        this.orderLineMapper = new OrderLineMapper(database);
    }

    public boolean insertOrderLine (OrderLine orderLine) throws UserException, SQLException {
        return orderLineMapper.insertOrderLine(orderLine);
    }
}
