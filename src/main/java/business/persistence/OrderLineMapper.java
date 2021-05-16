package business.persistence;

import business.entities.OrderLine;
import business.exceptions.UserException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderLineMapper {
    private Database database;

    public OrderLineMapper(Database database) {
        this.database = database;
    }

    public boolean insertOrderLine (OrderLine orderLine) throws UserException{
        try (Connection connection = database.connect()) {
            String sql = "INSERT INTO `order_line` " +
                    "(quantity," +
                    "orders_id," +
                    "unit," +
                    "materials_variant_id," +
                    "description) " +
                    "VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1,orderLine.getQuantity());
                ps.setInt(2,orderLine.getOrdersID());
                ps.setString(3,orderLine.getUnit());
                ps.setInt(4,orderLine.getMaterial().getVariantId());
                ps.setString(5,orderLine.getDescription());
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1) {
                    return true;
                }
                throw new SQLException("Error while inserting into order_line");
            } catch (SQLException ex) {
                throw new UserException(ex.getMessage());
            }
        } catch (SQLException ex) {
            throw new UserException(ex.getMessage());
        }
    }
}
