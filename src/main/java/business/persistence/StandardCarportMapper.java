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
                    RoofType tag_type = RoofType.valueOf(rs.getString("tag_type"));
                    int standardCarportID = rs.getInt("id");

                    System.out.println("line 41"+tag_type.toString());
                    //Find materiale navn ud fra materiale id
                    String carport_beklædning = FrontController.categoryFormOptions.get(1).get(carport_beklædningId).getMaterialName();
                    String redskabsskur_beklædning = FrontController.categoryFormOptions.get(3).get(redskabsskur_beklædningId).getMaterialName();
                    //Tag beklædning skal findes i forskelligt materiale ID efter om det er fladt eller skrå tag
                    String tag_materiale;
                    if(tag_type == RoofType.Fladt_Tag){
                        //Fladt tag materiale category id == 2
                        tag_materiale = FrontController.categoryFormOptions.get(2).get(tag_materialeId).getMaterialName();
                    }else
                    {
                        //Tag med rejsning materiale category id == 4
                        tag_materiale = FrontController.categoryFormOptions.get(4).get(tag_materialeId).getMaterialName();
                    }
                    Carport carport = new Carport(carport_beklædning, carport_bredde, carport_højde, carport_længde, redskabsskur_beklædning, redskabsskur_bredde, redskabsskur_længde, tag_hældning, tag_materiale, tag_type);
                    carport.setId(standardCarportID);
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