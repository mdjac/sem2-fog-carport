package business.persistence;

import business.entities.*;
import business.exceptions.UserException;
import business.utilities.Calculator;

import java.sql.*;
import java.util.TreeMap;

public class OrderLineMapper {
    private Database database;

    public OrderLineMapper(Database database) {
        this.database = database;
    }

    public TreeMap<Integer, OrderLine> getOrderLinesByOrderId(int orderId) throws UserException {
        TreeMap<Integer,OrderLine> BOM = new TreeMap<>();
        try (Connection connection = database.connect())
        {
            String sql = "SELECT * from order_line where orders_id = ?";

            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setInt(1,orderId);
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                    int id = rs.getInt("id");
                    int quantity = rs.getInt("quantity");
                    int orders_id = rs.getInt("orders_id");
                    String unit = rs.getString("unit");
                    int materialsVariantId = rs.getInt("materials_variant_id");
                    String description = rs.getString("description");
                    OrderLine orderLine = new OrderLine(quantity,orders_id,unit, Calculator.getMaterialByMaterialVariantId(materialsVariantId),description);
                    orderLine.setId(id);
                    BOM.put(orderLine.getId(),orderLine);
                }
                return BOM;
            }
            catch (SQLException ex)
            {
                throw new UserException(ex.getMessage());
            }
        }
        catch (SQLException ex)
        {
            throw new UserException("Connection to database could not be established");
        }
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
