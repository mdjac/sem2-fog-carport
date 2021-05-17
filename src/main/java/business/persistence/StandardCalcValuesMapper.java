package business.persistence;

import business.entities.AllowedMinMax;
import business.exceptions.UserException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.TreeMap;

public class StandardCalcValuesMapper {
    private Database database;

    public StandardCalcValuesMapper(Database database) {
        this.database = database;
    }

    public TreeMap<String, AllowedMinMax> getAllowedMeasurements() throws UserException {
        TreeMap<String, AllowedMinMax> allowedMeasurements = new TreeMap<>();
        try (Connection connection = database.connect())
        {
            String sql = "SELECT * FROM standard_calc_values where id = 1";
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                  String name = rs.getString("name");
                  Integer min = rs.getInt("min");
                  Integer max = rs.getInt("max");
                  allowedMeasurements.put(name,new AllowedMinMax(min,max));
                }
                return allowedMeasurements;
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
