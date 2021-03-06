package business.services;

import business.entities.Material;
import business.exceptions.UserException;
import business.persistence.Database;
import business.persistence.MaterialMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class MaterialFacadeTest {
    private final static String DATABASE = "carport";  // Change this to your own database
    private final static String TESTDATABASE = DATABASE + "_test";
    private final static String USER = "dev";
    private final static String PASSWORD = "DevUser21!";
    private final static String URL = "jdbc:mysql://64.227.113.104:3306/" + TESTDATABASE + "?serverTimezone=CET&useSSL=false";

    private static Database database;
    private static MaterialFacade materialFacade;

    @BeforeAll
    public static void setUpClass() {
        try {
            database = new Database(USER, PASSWORD, URL);
            materialFacade = new MaterialFacade(database);
        } catch (ClassNotFoundException e) {   // kan ikke finde driveren i database klassen
            fail("Database connection failed. Missing jdbc driver");
        }
    }

    @Test
    void getAllMaterials() {
        TreeMap<Integer, TreeMap<Integer, Material>> materials = new TreeMap<>();
        try {
            materials = materialFacade.getAllMaterials();
        } catch (UserException e) {
            e.printStackTrace();
        }
        boolean expected = true;
        //Checks that materials Map is not size 0
        assertEquals(expected,materials.size() != 0);
    }

    @Test
    void checkMaterialIsNotNull() {
        TreeMap<Integer, TreeMap<Integer, Material>> materials = new TreeMap<>();
        try {
            materials = materialFacade.getAllMaterials();
        } catch (UserException e) {
            e.printStackTrace();
        }
        boolean expected = true;
        //Checks that byggeMaterial with variantId 1 is not null
        Material material = materials.get(5).get(1);
        assertEquals(expected,material != null);
    }

    @Test
    void checkMaterialName() {
        TreeMap<Integer, TreeMap<Integer, Material>> materials = new TreeMap<>();
        try {
            materials = materialFacade.getAllMaterials();
        } catch (UserException e) {
            e.printStackTrace();
        }
        String expectedName = "Trykimp. Br??dt";
        String expectedCategoryName = "Carport bekl??dning";
        //Checks that Trykimp. Br??dt can be selected as carport bekl??dning
        Material material = materials.get(1).get(1);
        assertEquals(expectedName,material.getMaterialName());
        assertEquals(expectedCategoryName,material.getCategoryName());
    }

}