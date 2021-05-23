package business.persistence;

import business.entities.Carport;
import business.entities.Material;
import business.entities.Order;
import business.entities.RoofType;
import business.exceptions.UserException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import web.StaticValues;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class StandardCarportMapperTest {
    private final static String DATABASE = "carport";  // Change this to your own database
    private final static String TESTDATABASE = DATABASE + "_test";
    private final static String USER = "dev";
    private final static String PASSWORD = "DevUser21!";
    private final static String URL = "jdbc:mysql://64.227.113.104:3306/" + TESTDATABASE + "?serverTimezone=CET&useSSL=false";

    private static Database database;
    private static StandardCarportMapper standardCarportMapper;


    @BeforeAll
    public static void setUpClass() {
        try {
            database = new Database(USER, PASSWORD, URL);
            standardCarportMapper = new StandardCarportMapper(database);
            new StaticValues().setGlobalValues(database);
        } catch (ClassNotFoundException e) {   // kan ikke finde driveren i database klassen
            fail("Database connection failed. Missing jdbc driver");
        }
    }

    @BeforeEach
    public void setUp() {
        // reset test database
        try ( Statement stmt = database.connect().createStatement() ) {

            stmt.execute("drop table if exists carport" );
            stmt.execute("create table " + TESTDATABASE + ".carport LIKE " + DATABASE + ".carport;" );
            stmt.execute("insert into carport values " +
                            "(1,1,300,200,480,1,100,100,null,3,'"+RoofType.Fladt_Tag+"',null,1), " +
                            "(2,1,300,200,480,null,null,null,null,3,'"+RoofType.Fladt_Tag+"',null,2), " +
                            "(3,1,300,200,480,1,100,100,5,4,'"+RoofType.Tag_Med_Rejsning+"',null,3)");

        } catch (SQLException ex) {
            System.out.println( "Could not open connection to database: " + ex.getMessage() );
        }
    }

    @Test
    void insertStandardCarport() throws UserException {
        //Checks that standardCarport size is 3 before insert
        TreeMap<Integer, Carport> standardCarports = standardCarportMapper.getStandardCarports();
        assertEquals(3,standardCarports.size());

        //Creates carport and inserts, then checks if size is 4 afterwards.
        Carport carport = new Carport(Carport.findCarportMaterialFromId(1),300,250,500,RoofType.Fladt_Tag,Carport.findRoofMaterialFromId(3,RoofType.Fladt_Tag));
        carport.setStandardCarportId(4);

        standardCarportMapper.insertStandardCarport(carport);

        standardCarports = standardCarportMapper.getStandardCarports();
        assertEquals(4,standardCarports.size());
    }

    @Test
    void getStandardCarports() throws UserException {
        TreeMap<Integer, Carport> standardCarports = standardCarportMapper.getStandardCarports();
        assertEquals(3,standardCarports.size());
        assertEquals(1,standardCarports.get(1).getStandardCarportId());
    }
}