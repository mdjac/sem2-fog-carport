package business.persistence;


import business.entities.*;
import business.exceptions.UserException;
import com.sun.org.apache.xpath.internal.operations.Or;
import web.FrontController;

import java.sql.*;
import java.util.TreeMap;

public class OrderMapper {
    private Database database;

    public OrderMapper(Database database)
    {
        this.database = database;
    }

    public TreeMap<Integer,Order> getOrders(User user) throws UserException {
        TreeMap<Integer,Order> orders = new TreeMap<>();
        try (Connection connection = database.connect())
        {
            String sql = "SELECT orders.id as order_id," +
                    "status," +
                    "totalprice," +
                    "time," +
                    "users_id," +
                    "carport.id as carport_id," +
                    "carport_material," +
                    "carport_width," +
                    "carport_height," +
                    "carport_length," +
                    "shed_material," +
                    "shed_width," +
                    "shed_length," +
                    "roof_tilt," +
                    "roof_material," +
                    "roof_type\n" +
                    "FROM orders\n" +
                    "INNER JOIN carport ON orders.id = carport.orders_id";

            if(user.getRole().equals("customer")){
                sql += " WHERE users_id = ?";
            }

            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                if(user.getRole().equals("customer")){
                    ps.setInt(1,user.getId());
                }
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                    Order order = null;
                    Carport carport = null;
                    int orderId = rs.getInt("order_id");
                    Status status = Status.fromString(rs.getString("status"));
                    double totalPrice = rs.getDouble("totalprice");
                    Timestamp time = rs.getTimestamp("time");
                    int userId = rs.getInt("users_id");
                    int carportId = rs.getInt("carport_id");
                    int carportMaterialId = rs.getInt("carport_material");
                    int carportWidth = rs.getInt("carport_width");
                    int carportHeight = rs.getInt("carport_height");
                    int carportLength = rs.getInt("carport_length");
                    int shedMaterialId = rs.getInt("shed_material");
                    int shedWidth = rs.getInt("shed_width");
                    int shedLength = rs.getInt("shed_length");
                    int roofTilt = rs.getInt("roof_tilt");
                    int roofMaterialId = rs.getInt("roof_material");
                    RoofType roofType = RoofType.fromString(rs.getString("roof_type"));

                    //Create carport
                    carport = new Carport(Carport.findCarportMaterialFromId(carportMaterialId),carportWidth,carportHeight,carportLength,roofType,Carport.findRoofMaterialFromId(roofMaterialId,roofType));
                    carport.setId(carportId);
                    //Checks which fields must be ignored
                    if(shedMaterialId != 0){
                        carport.setShedMaterial(Carport.findShedMaterialFromId(shedMaterialId));
                        carport.setShedWidth(shedWidth);
                        carport.setShedLength(shedLength);
                    }
                    if(roofType == RoofType.Tag_Med_Rejsning){
                        carport.setRoofTilt(roofTilt);
                    }
                    order = new Order(status,totalPrice,userId,carport);
                    order.setId(orderId);
                    order.setTime(time);
                    orders.put(order.getId(),order);
                }
                return orders;
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
                ps.setInt(2,carport.getRoofMaterial().getMaterialsId());
                if(carport.getRoofTilt() != null){
                    ps.setInt(3,carport.getRoofTilt());
                }else{
                    ps.setNull(3,java.sql.Types.INTEGER);
                }
                ps.setInt(4,carport.getCarportMaterial().getMaterialsId());
                ps.setInt(5,carport.getCarportWidth());
                ps.setInt(6,carport.getCarportHeight());
                ps.setInt(7,carport.getCarportLength());
                if(carport.getShedMaterial() != null){
                    ps.setInt(8,carport.getShedMaterial().getMaterialsId());
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
                    ex.printStackTrace();
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
