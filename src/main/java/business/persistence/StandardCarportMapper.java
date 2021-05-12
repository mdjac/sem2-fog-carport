package business.persistence;

import business.entities.Carport;
import business.entities.Material;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;

public class StandardCarportMapper {
    private Database database;

    public StandardCarportMapper(Database database) throws SQLException {
        this.database = database;
    }

    public TreeMap<Integer, TreeMap<Integer, Carport>> getStandardCarport() throws SQLException {
        TreeMap<Integer, TreeMap<Integer, Carport>> carportMap = new TreeMap<>();


        try (
                Connection connection = database.connect()) {
            String sql = "SELECT * FROM standard_carport";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String caport_beklædning = rs.getString("carport_beklædning");
                    String carport_bredde = rs.getString("carport_bredde");
                    String carport_højde = rs.getString("carport_højde");
                    String carport_længde = rs.getString("carport_længde");
                    String redskabsskur_beklædning = rs.getString("redskabsskur_beklædning");
                    String redskabsskur_bredde = rs.getString("redskabsskur_bredde");
                    String redskabsskur_længde = rs.getString("redskabsskur_længde");
                    String tag_hældning = rs.getString("tag_hældning");
                    String tag_materiale = rs.getString("tag_materiale");
                    String tag_type = rs.getString("tag_type");
                    Carport carport = new Carport(caport_beklædning, carport_bredde, carport_højde, carport_længde, redskabsskur_beklædning, redskabsskur_bredde, redskabsskur_længde, tag_hældning, tag_materiale, tag_type);
                    carportMap = new TreeMap<>();

                }
            }
        }
        return carportMap;
    }
}