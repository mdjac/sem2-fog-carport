package business.entities;

import business.exceptions.UserException;
import business.persistence.Database;
import business.persistence.OrderMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import web.StaticValues;

import static org.junit.jupiter.api.Assertions.*;

class CarportTest {
    private final static String DATABASE = "carport";  // Change this to your own database
    private final static String TESTDATABASE = DATABASE + "_test";
    private final static String USER = "dev";
    private final static String PASSWORD = "DevUser21!";
    private final static String URL = "jdbc:mysql://64.227.113.104:3306/" + TESTDATABASE + "?serverTimezone=CET&useSSL=false";

    private static Database database;


    @BeforeAll
    public static void setUpClass() {
        try {
            database = new Database(USER, PASSWORD, URL);
            new StaticValues().setGlobalValues(database);
        } catch (ClassNotFoundException e) {   // kan ikke finde driveren i database klassen
            fail("Database connection failed. Missing jdbc driver");
        }
    }


    @Test
    void acceptableMeasurementsCarportMeasure() throws UserException {
        //Checking outer values of carportLength
        Carport carport = new Carport(Carport.findCarportMaterialFromId(1),350,250,781,RoofType.Fladt_Tag,Carport.findRoofMaterialFromId(3,RoofType.Fladt_Tag));
        assertEquals(false,carport.acceptableMeasurements());
        carport.setCarportLength(780);
        assertEquals(true,carport.acceptableMeasurements());
        carport.setCarportLength(479);
        assertEquals(false,carport.acceptableMeasurements());
        carport.setCarportLength(480);
        assertEquals(true,carport.acceptableMeasurements());
    }

    @Test
    void acceptableMeasurementsShedMeasure() throws UserException {
        //Checking outer values of shedWidth
        Carport carport = new Carport(Carport.findCarportMaterialFromId(1),350,250,780,RoofType.Fladt_Tag,Carport.findRoofMaterialFromId(3,RoofType.Fladt_Tag));
        carport.setShedMaterial(Carport.findShedMaterialFromId(1));
        carport.setShedLength(100);
        carport.setShedWidth(100);
        assertEquals(true,carport.acceptableMeasurements());
        carport.setShedWidth(99);
        assertEquals(false,carport.acceptableMeasurements());
        carport.setShedWidth(399);
        assertEquals(true,carport.acceptableMeasurements());
        carport.setShedWidth(400);
        assertEquals(true,carport.acceptableMeasurements());
        carport.setShedWidth(401);
        assertEquals(false,carport.acceptableMeasurements());
    }

    @Test
    void acceptableMeasurementsRoofTiltMeasure() throws UserException {
        Carport carport = new Carport(Carport.findCarportMaterialFromId(1),350,250,780,RoofType.Tag_Med_Rejsning,Carport.findRoofMaterialFromId(4,RoofType.Tag_Med_Rejsning));
        carport.setRoofTilt(2);
        assertEquals(false,carport.acceptableMeasurements());
        carport.setRoofTilt(3);
        assertEquals(true,carport.acceptableMeasurements());
        carport.setRoofTilt(11);
        assertEquals(false,carport.acceptableMeasurements());
        carport.setRoofTilt(10);
        assertEquals(true,carport.acceptableMeasurements());
    }
}