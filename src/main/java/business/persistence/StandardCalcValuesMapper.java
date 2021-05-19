package business.persistence;

import business.entities.MinMax;
import business.exceptions.UserException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;

public class StandardCalcValuesMapper {
    private Database database;

    public StandardCalcValuesMapper(Database database) {
        this.database = database;
    }

    public TreeMap<String, MinMax> getPriceCalculatorValues() throws UserException {
        TreeMap<String, MinMax> priceCalculatorValues = new TreeMap<>();
        try (Connection connection = database.connect())
        {
            String sql = "SELECT * FROM standard_calc_values where category = 'PrisBeregner'";
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                    String name = rs.getString("name");
                    Double min = rs.getDouble("min");
                    Double max = rs.getDouble("max");
                    priceCalculatorValues.put(name,new MinMax(min,max));
                }
                return priceCalculatorValues;
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


    public TreeMap<String, MinMax> getRaftersDistance() throws UserException {
        TreeMap<String, MinMax> raftersDistances = new TreeMap<>();
        try (Connection connection = database.connect())
        {
            String sql = "SELECT * FROM standard_calc_values where category = 'SpærAfstande'";
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                    String name = rs.getString("name");
                    Double min = rs.getDouble("min");
                    Double max = rs.getDouble("max");
                    raftersDistances.put(name,new MinMax(min,max));
                }
                return raftersDistances;
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


    public TreeMap<String, MinMax> getAllowedMeasurements() throws UserException {
        TreeMap<String, MinMax> allowedMeasurements = new TreeMap<>();
        try (Connection connection = database.connect())
        {
            String sql = "SELECT * FROM standard_calc_values where category = 'TilladteMålValgmuligheder'";
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                  String name = rs.getString("name");
                  Double min = rs.getDouble("min");
                  Double max = rs.getDouble("max");
                  allowedMeasurements.put(name,new MinMax(min,max));
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

    public TreeMap<String, MinMax> getCalculatorRequiredMaterialWidth() throws UserException {
        TreeMap<String, MinMax> calculatorRequiredMaterialWidth = new TreeMap<>();
        try (Connection connection = database.connect())
        {
            String sql = "SELECT * FROM standard_calc_values where category = 'NødvendigMaterialeBredde'";
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                    String name = rs.getString("name");
                    Double min = rs.getDouble("min");
                    Double max = rs.getDouble("max");
                    calculatorRequiredMaterialWidth.put(name,new MinMax(min,max));
                }
                return calculatorRequiredMaterialWidth;
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

    public TreeMap<String, MinMax> getPostDistances() throws UserException {
        TreeMap<String, MinMax> postDistances = new TreeMap<>();
        try (Connection connection = database.connect())
        {
            String sql = "SELECT * FROM standard_calc_values where category = 'StolpeAfstande'";
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                    String name = rs.getString("name");
                    Double min = rs.getDouble("min");
                    Double max = rs.getDouble("max");
                    postDistances.put(name,new MinMax(min,max));
                }
                return postDistances;
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
