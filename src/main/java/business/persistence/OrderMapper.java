package business.persistence;


import business.entities.Carport;
import business.entities.Order;
import business.exceptions.UserException;

import java.sql.*;

public class OrderMapper {
    private Database database;

    public OrderMapper(Database database)
    {
        this.database = database;
    }

    public boolean insertCarport (Carport carport, int orderId) throws UserException, SQLException {
        try (Connection connection = database.connect()) {
            String sql = "INSERT INTO `carport` " +
                    "(roof_type," +
                    "roof_material," +
                    "roof_tilt," +
                    "carport_material," +
                    "carport_width," +
                    "carport_height," +
                    "carport_length," +
                    "shed_material," +
                    "shed_width," +
                    "shed_length," +
                    "orders_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1,carport.getRoofType());
                ps.setString(2,carport.getRoofMaterial());
                if(carport.getRoofTilt() != null){
                    ps.setInt(3,carport.getRoofTilt());
                }else{
                    ps.setNull(3,java.sql.Types.INTEGER);
                }
                ps.setString(4,carport.getCarportMaterial());
                ps.setInt(5,carport.getCarportWidth());
                ps.setInt(6,carport.getCarportHeight());
                ps.setInt(7,carport.getCarportLength());
                if(carport.getShedMaterial() != null){
                    ps.setString(8,carport.getShedMaterial());
                    ps.setInt(9,carport.getShedWidth());
                    ps.setInt(10,carport.getShedLength());
                }else{
                    ps.setNull(8, Types.VARCHAR);
                    ps.setNull(9,java.sql.Types.INTEGER);
                    ps.setNull(10,java.sql.Types.INTEGER);
                }
                ps.setInt(11,orderId);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1) {
                    return true;
                }
                throw new SQLException("Error while inserting into carport");
            } catch (SQLException ex) {
                throw new UserException(ex.getMessage());
            }
        } catch (SQLException ex) {
            throw new UserException(ex.getMessage());
        }
    }


    public Order insertOrder(Order order, Carport carport) throws UserException {
        try (Connection connection = database.connect()) {
            String sql = "INSERT INTO orders (status,totalprice,users_id) VALUES (?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1,order.getStatus().toString());
                ps.setDouble(2,order.getTotalPrice());
                ps.setInt(3,order.getUserId());
                ps.executeUpdate();
                ResultSet ids = ps.getGeneratedKeys();
                ids.next();
                int id = ids.getInt(1);
                if (id <= 0) {
                    throw new UserException("Error when inserting order");
                }
                order.setId(id);
                try {
                    insertCarport(carport,order.getId());
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    //TODO: Delete entry in orders and entries in order_Details for the order
                    //deleteOrderDetails(orderId);
                    //deleteOrder(orderId);
                    throw new UserException(ex.getMessage());
                }
                //TODO: Skal indsætte orderline
                return order;
            } catch (SQLException ex) {
                throw new UserException(ex.getMessage());
            }
        } catch (SQLException ex) {
            throw new UserException(ex.getMessage());
        }
    }


}
