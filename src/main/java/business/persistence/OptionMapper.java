package business.persistence;

import business.entities.Option;
import business.exceptions.UserException;
import sun.reflect.generics.tree.Tree;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.TreeMap;

public class OptionMapper {
    private Database database;

    public OptionMapper(Database database)
    {
        this.database = database;
    }

    public TreeMap<Integer, Option> getAllOptions() throws UserException {
        TreeMap<Integer, Option> options = new TreeMap<>();

        try (Connection connection = database.connect())
        {
            String sql = "SELECT options.id, options.name, options_variant.id as variant_id, options_variant.name as variant_name from options INNER JOIN options_variant on options.id = options_variant.options_id;";

            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                    int optionId = rs.getInt("id");
                    String optionName = rs.getString("name");
                    int variantId = rs.getInt("variant_id");
                    String variantName = rs.getString("variant_name");

                    //Check if option is already in map
                    Option option = new Option(optionId,optionName);
                    //add to map if not
                    if(!options.containsKey(optionId)){
                       option.addValue(variantId,variantName);
                       options.put(optionId,option);
                    }
                    //Check if variant is already options map
                    //add to map if not
                    if (!options.get(optionId).getValues().containsKey(variantId)){
                        options.get(optionId).addValue(variantId,variantName);
                    }

                }
                return options;
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
