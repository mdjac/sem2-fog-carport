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

    public boolean insertStandardCarport (RoofType roofType, int roofMaterialId, int carportMaterialId, int carportLength, int carportWidth, int carportHeight, Integer roofTilt,  Integer shedMaterialId, Integer shedLength, Integer shedWidth) throws UserException{
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
                ps.setInt(1,carportMaterialId);
                ps.setInt(2,carportWidth);
                ps.setInt(3,carportHeight);
                ps.setInt(4,carportLength);
                if(shedMaterialId != null){
                    ps.setInt(5,shedMaterialId);
                    ps.setInt(6,shedWidth);
                    ps.setInt(7,shedLength);
                }else{
                    ps.setNull(5,java.sql.Types.INTEGER);
                    ps.setNull(6,java.sql.Types.INTEGER);
                    ps.setNull(7,java.sql.Types.INTEGER);
                }
                if(roofType.equals(RoofType.Tag_Med_Rejsning)){
                    ps.setInt(8,roofTilt);
                }else{
                    ps.setNull(8,java.sql.Types.INTEGER);
                }
                ps.setInt(9,roofMaterialId);
                ps.setString(10,roofType.toString());
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


                    //Find material name based on id
                    String carport_beklædning = FrontController.categoryFormOptions.get(1).get(carportMaterialId).getMaterialName();

                    //Create carport
                    Carport carport = new Carport(carport_beklædning, carportWidth, carportHeight, carportLength, roofType);
                    carport.setId(standardCarportID);
                    carport.setCarportMaterialId(carportMaterialId);
                    carport.setRoofMaterialId(roofMaterialId);
                    //Checks which fields must be ignored
                    if(shedMaterialId != 0){
                        String redskabsskur_beklædning = FrontController.categoryFormOptions.get(3).get(shedMaterialId).getMaterialName();
                        carport.setShedMaterial(redskabsskur_beklædning);
                        carport.setShedWidth(shedWidth);
                        carport.setShedLength(shedLength);
                        carport.setShedMaterialId(shedMaterialId);
                    }

                    //Tag beklædning skal findes i forskelligt materiale ID efter om det er fladt eller skrå tag
                    String tag_materiale;
                    if(roofType == RoofType.Fladt_Tag){
                        //Fladt tag materiale category id == 2
                        tag_materiale = FrontController.categoryFormOptions.get(2).get(roofMaterialId).getMaterialName();
                    }else
                    {
                        //Tag med rejsning materiale category id == 4
                        tag_materiale = FrontController.categoryFormOptions.get(4).get(roofMaterialId).getMaterialName();
                        carport.setRoofTilt(roofTilt);
                    }
                    carport.setRoofMaterial(tag_materiale);
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