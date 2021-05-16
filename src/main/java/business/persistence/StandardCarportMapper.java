package business.persistence;

import business.entities.*;
import business.exceptions.UserException;
import web.FrontController;

import java.sql.*;
import java.util.TreeMap;

public class StandardCarportMapper {
    private Database database;

    public StandardCarportMapper(Database database){
        this.database = database;
    }

    public boolean insertStandardCarport (Carport carport) throws UserException{
        try (Connection connection = database.connect()) {
            String sql = "INSERT INTO `standard_carports` " +
                    "(carport_material," +
                    "carport_width," +
                    "carport_height," +
                    "carport_length," +
                    "shed_material," +
                    "shed_width," +
                    "shed_length," +
                    "roof_tilt," +
                    "roof_material," +
                    "roof_type) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1,carport.getCarportMaterial().getMaterialsId());
                ps.setInt(2,carport.getCarportWidth());
                ps.setInt(3,carport.getCarportHeight());
                ps.setInt(4,carport.getCarportLength());
                if(carport.getShedMaterial() != null){
                    ps.setInt(5,carport.getShedMaterial().getMaterialsId());
                    ps.setInt(6,carport.getShedWidth());
                    ps.setInt(7,carport.getShedLength());
                }else{
                    ps.setNull(5,java.sql.Types.INTEGER);
                    ps.setNull(6,java.sql.Types.INTEGER);
                    ps.setNull(7,java.sql.Types.INTEGER);
                }
                if(carport.getRoofType().equals(RoofType.Tag_Med_Rejsning)){
                    ps.setInt(8,carport.getRoofTilt());
                }else{
                    ps.setNull(8,java.sql.Types.INTEGER);
                }
                ps.setInt(9,carport.getRoofMaterial().getMaterialsId());
                ps.setString(10,carport.getRoofType().toString());
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1) {
                    return true;
                }
                throw new SQLException("Error while inserting into standard carport");
            } catch (SQLException ex) {
                throw new UserException(ex.getMessage());
            }
        } catch (SQLException ex) {
            throw new UserException(ex.getMessage());
        }
    }


    public TreeMap<Integer, Carport> getStandardCarports() throws UserException {
        TreeMap<Integer, Carport> standardCarports = new TreeMap<>();
        try (Connection connection = database.connect())
        {
            String sql = "SELECT * FROM standard_carports";
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
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
                    int standardCarportID = rs.getInt("id");


                    //Create carport
                    Carport carport = new Carport(Carport.findCarportMaterialFromId(carportMaterialId), carportWidth, carportHeight, carportLength, roofType,Carport.findRoofMaterialFromId(roofMaterialId,roofType));
                    carport.setId(standardCarportID);
                    //Checks which fields must be ignored
                    if(shedMaterialId != 0){
                        carport.setShedMaterial(Carport.findShedMaterialFromId(shedMaterialId));
                        carport.setShedWidth(shedWidth);
                        carport.setShedLength(shedLength);
                    }
                    if(roofType == RoofType.Tag_Med_Rejsning){
                        carport.setRoofTilt(roofTilt);
                    }
                    System.out.println(carport);
                    standardCarports.put(carport.getId(),carport);
                }
                return standardCarports;
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
}