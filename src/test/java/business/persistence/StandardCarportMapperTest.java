package business.persistence;

import business.entities.Carport;
import business.entities.Material;
import business.entities.RoofType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class StandardCarportMapperTest {
    private final static String DATABASE = "carport";  // Change this to your own database
    private final static String TESTDATABASE = DATABASE + "_test";
    private final static String USER = "dev";
    private final static String PASSWORD = "DevUser21!";
    private final static String URL = "jdbc:mysql://167.172.176.18:3306/" + TESTDATABASE + "?serverTimezone=CET&useSSL=false";

    private static Database database;
    private static UserMapper userMapper;
    private static StandardCarportMapper standardCarportMapper;

    @BeforeAll
    public static void setUpClass() {
        try {
            database = new Database(USER, PASSWORD, URL);
            userMapper = new UserMapper(database);
            standardCarportMapper = new StandardCarportMapper(database);
        } catch (ClassNotFoundException e) {   // kan ikke finde driveren i database klassen
            fail("Database connection failed. Missing jdbc driver");
        }
    }

    @BeforeEach
    public void setUp() {
        // reset test database
        try ( Statement stmt = database.connect().createStatement() ) {
            stmt.execute("drop table if exists users" );
            stmt.execute("create table " + TESTDATABASE + ".users LIKE " + DATABASE + ".users;" );
            stmt.execute(
                    "insert into users values " +
                            "(1,'jens@somewhere.com','jensen','customer','testvej 1','10101010','2500','Valby','Jens Jensen'), " +
                            "(2,'ken@somewhere.com','kensen','customer','testvej 2','80808080','2100','København Ø','Ken Kensen'), " +
                            "(3,'robin@somewhere.com','batman','employee','testvej 3','20202020','2200','København N','Robin Batman')");


            stmt.execute("drop table if exists carport" );
            stmt.execute("create table " + TESTDATABASE + ".carport LIKE " + DATABASE + ".carport;" );
            stmt.execute(
                    "insert into carport values " +
                            "(1,'1','300','200','400','1','100','100','0','3','Fladt tag','200','1')");

        } catch (SQLException ex) {
            System.out.println( "Could not open connection to database: " + ex.getMessage() );
        }
    }



    @Test
    void insertStandardCarport() {
        Material material = new Material("Carport Beklædning","Trykimp. Brædt",1,1,1,1,10);

        Carport carport = new Carport(Carport.findCarportMaterialFromId(1),550,250,600,Carport.findRoofMaterialFromId(1,RoofType.Fladt_Tag));
    }
}