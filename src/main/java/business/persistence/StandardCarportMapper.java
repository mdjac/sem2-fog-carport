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
                    "(carport_beklædning," +
                    "carport_bredde," +
                    "carport_højde," +
                    "carport_længde," +
                    "redskabsskur_beklædning," +
                    "redskabsskur_bredde," +
                    "redskabsskur_længde," +
                    "tag_hældning," +
                    "tag_materiale," +
                    "tag_type) " +
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
                    int carport_beklædningId = rs.getInt("carport_beklædning");
                    int carport_bredde = rs.getInt("carport_bredde");
                    int carport_højde = rs.getInt("carport_højde");
                    int carport_længde = rs.getInt("carport_længde");
                    int redskabsskur_beklædningId = rs.getInt("redskabsskur_beklædning");
                    int redskabsskur_bredde = rs.getInt("redskabsskur_bredde");
                    int redskabsskur_længde = rs.getInt("redskabsskur_længde");
                    int tag_hældning = rs.getInt("tag_hældning");
                    int tag_materialeId = rs.getInt("tag_materiale");
                    RoofType tag_type = RoofType.fromString(rs.getString("tag_type"));
                    int standardCarportID = rs.getInt("id");


                    //Find material name based on id
                    String carport_beklædning = FrontController.categoryFormOptions.get(1).get(carport_beklædningId).getMaterialName();

                    //Create carport
                    Carport carport = new Carport(carport_beklædning, carport_bredde, carport_højde, carport_længde, tag_type);
                    carport.setId(standardCarportID);
                    carport.setCarportBeklædningId(carport_beklædningId);
                    carport.setTagMaterialeId(tag_materialeId);
                    //Checks which fields must be ignored
                    if(redskabsskur_beklædningId != 0){
                        String redskabsskur_beklædning = FrontController.categoryFormOptions.get(3).get(redskabsskur_beklædningId).getMaterialName();
                        carport.setRedskabsskurBeklædning(redskabsskur_beklædning);
                        carport.setRedskabsskurBredde(redskabsskur_bredde);
                        carport.setRedskabsskurLængde(redskabsskur_længde);
                        carport.setRedskabsskurBeklædningId(redskabsskur_beklædningId);
                    }

                    //Tag beklædning skal findes i forskelligt materiale ID efter om det er fladt eller skrå tag
                    String tag_materiale;
                    if(tag_type == RoofType.Fladt_Tag){
                        //Fladt tag materiale category id == 2
                        tag_materiale = FrontController.categoryFormOptions.get(2).get(tag_materialeId).getMaterialName();
                    }else
                    {
                        //Tag med rejsning materiale category id == 4
                        tag_materiale = FrontController.categoryFormOptions.get(4).get(tag_materialeId).getMaterialName();
                        carport.setTagHældning(tag_hældning);
                    }
                    carport.setTagMateriale(tag_materiale);
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