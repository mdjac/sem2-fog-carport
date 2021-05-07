package business.persistence;


import business.entities.Carport;
import business.entities.Material;
import business.entities.Order;
import business.entities.Status;
import business.exceptions.UserException;
import web.FrontController;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class MaterialMapper {
    private Database database;

    public MaterialMapper(Database database)
    {
        this.database = database;
    }

    public List<Material> getAllMaterials() throws UserException {
        List<Material> materialList = new ArrayList<>();
        try (Connection connection = database.connect())
        {
            String sql = "SELECT \n" +
                    "materials_category.name as category_name, \n" +
                    "materials.name as material_name, \n" +
                    "materials.id as materials_id,\n" +
                    "materials_category_id,\n" +
                    "materials_variant.id as variant_id,\n" +
                    "materials_variant.quantity,\n" +
                    "materials_variant.depth,\n" +
                    "materials_variant.length,\n" +
                    "materials_variant.height,\n" +
                    "materials_variant.price\n" +
                    "FROM materials INNER JOIN materials_link_category ON materials.id = materials_link_category.materials_id \n" +
                    "INNER JOIN materials_category on materials_link_category.materials_category_id = materials_category.id \n" +
                    "INNER JOIN materials_variant on materials.id = materials_variant.materials_id;";

            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String categoryName = rs.getString("category_name");
                    String materialName = rs.getString("material_name");
                    int materialsId = rs.getInt("materials_id");
                    int materialsCategoryId = rs.getInt("materials_category_id");
                    int variantId = rs.getInt("variant_id");
                    int quantity = rs.getInt("quantity");
                    double price = rs.getDouble("price");
                    double depth = rs.getDouble("depth");
                    double length = rs.getDouble("length");
                    double height = rs.getDouble("height");
                    Material material = new Material(categoryName,materialName,materialsId,materialsCategoryId,variantId,quantity,price);
                    material.setDepth(depth);
                    material.setLength(length);
                    material.setHeight(height);
                    materialList.add(material);
                }
                return materialList;
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
