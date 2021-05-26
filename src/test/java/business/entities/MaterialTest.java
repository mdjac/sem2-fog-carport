package business.entities;

import business.exceptions.UserException;
import business.persistence.Database;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import web.StaticValues;

import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class MaterialTest {
    private final static String DATABASE = "carport";  // Change this to your own database
    private final static String TESTDATABASE = DATABASE + "_test";
    private final static String USER = "dev";
    private final static String PASSWORD = "DevUser21!";
    private final static String URL = "jdbc:mysql://64.227.113.104:3306/" + TESTDATABASE + "?serverTimezone=CET&useSSL=false";

    private static Database database;


    @BeforeAll
    public static void setUpClass() throws UserException {
        try {
            database = new Database(USER, PASSWORD, URL);
            new StaticValues().setGlobalValues(database);
        } catch (ClassNotFoundException e) {   // kan ikke finde driveren i database klassen
            fail("Database connection failed. Missing jdbc driver");
        }
    }

    @Test
    void getMaterialVariantsFromMaterialId() {
        TreeMap<Integer, Material> materialVariantMap;
        materialVariantMap = Material.getMaterialVariantsFromMaterialId(10);
        Material material = materialVariantMap.get(20);
        assertEquals("Trykimp stolpe",material.getMaterialName());
        assertEquals(true,materialVariantMap != null);
    }
}