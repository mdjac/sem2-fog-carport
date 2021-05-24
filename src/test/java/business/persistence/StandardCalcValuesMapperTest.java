package business.persistence;

import business.entities.MinMax;
import business.exceptions.UserException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class StandardCalcValuesMapperTest {
    private final static String DATABASE = "carport";  // Change this to your own database
    private final static String TESTDATABASE = DATABASE + "_test";
    private final static String USER = "dev";
    private final static String PASSWORD = "DevUser21!";
    private final static String URL = "jdbc:mysql://64.227.113.104:3306/" + TESTDATABASE + "?serverTimezone=CET&useSSL=false";

    private static Database database;
    private static StandardCalcValuesMapper standardCalcValuesMapper;


    @BeforeAll
    public static void setUpClass() {
        try {
            database = new Database(USER, PASSWORD, URL);
            standardCalcValuesMapper = new StandardCalcValuesMapper(database);
        } catch (ClassNotFoundException e) {   // kan ikke finde driveren i database klassen
            fail("Database connection failed. Missing jdbc driver");
        }
    }


    @Test
    void getPriceCalculatorValues() throws UserException {
        TreeMap<String, MinMax> values = new TreeMap<>();
        values = standardCalcValuesMapper.getPriceCalculatorValues();
        assertEquals(1,values.size());
        assertNotNull(values.get("ordreAvance").getValue());
    }

    @Test
    void getRaftersDistance() throws UserException {
        TreeMap<String, MinMax> raftersDistances = new TreeMap<>();
        raftersDistances = standardCalcValuesMapper.getRaftersDistance();
        assertEquals(3,raftersDistances.size());
        assertNotEquals(0,raftersDistances.get("fladtTag").getMin());
        assertEquals(null,raftersDistances.get("fladtTag").getValue());
    }

    @Test
    void getAllowedMeasurements() throws UserException {
        TreeMap<String, MinMax> allowedMeasurements = new TreeMap<>();
        allowedMeasurements = standardCalcValuesMapper.getAllowedMeasurements();
        assertEquals(6,allowedMeasurements.size());
        assertNotEquals(0,allowedMeasurements.get("tagHældning").getMin());
        assertEquals(null,allowedMeasurements.get("tagHældning").getValue());
    }

    @Test
    void getCalculatorRequiredMaterialWidth() throws UserException {
        TreeMap<String, MinMax> calculatorRequiredMaterialWidth = new TreeMap<>();
        calculatorRequiredMaterialWidth = standardCalcValuesMapper.getCalculatorRequiredMaterialWidth();
        assertEquals(6,calculatorRequiredMaterialWidth.size());
        assertNotNull(calculatorRequiredMaterialWidth.get("spær").getValue());
    }

    @Test
    void getPostDistances() throws UserException {
        TreeMap<String, MinMax> postDistances = new TreeMap<>();
        postDistances = standardCalcValuesMapper.getPostDistances();
        assertEquals(4,postDistances.size());
        assertNotNull(postDistances.get("stolpeNedgravning").getValue());
        assertNull(postDistances.get("afstandMellemStolper").getValue());
    }
}