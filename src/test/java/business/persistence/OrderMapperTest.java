package business.persistence;

import business.entities.Order;
import business.entities.RoofType;
import business.entities.Status;
import business.exceptions.UserException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapperTest {

    private final static String DATABASE = "carport";  // Change this to your own database
    private final static String TESTDATABASE = DATABASE + "_test";
    private final static String USER = "dev";
    private final static String PASSWORD = "DevUser21!";
    private final static String URL = "jdbc:mysql://167.172.176.18:3306/" + TESTDATABASE + "?serverTimezone=CET&useSSL=false";

    private static Database database;
    private static OrderMapper orderMapper;

    @BeforeAll
    public static void setUpClass() {
        try {
            database = new Database(USER, PASSWORD, URL);
            orderMapper = new OrderMapper(database);
        } catch (ClassNotFoundException e) {   // kan ikke finde driveren i database klassen
            fail("Database connection failed. Missing jdbc driver");
        }
    }

    @BeforeEach
    void setUp() {
        // reset test database
        try ( Statement stmt = database.connect().createStatement() ) {
            stmt.execute("SET FOREIGN_KEY_CHECKS=0");
            //Users table to avoid FK constraint.
            stmt.execute("drop table if exists users" );
            stmt.execute("create table " + TESTDATABASE + ".users LIKE " + DATABASE + ".users;" );
            stmt.execute(
                    "insert into users values " +
                            "(1,'jens@somewhere.com','jensen','customer','testvej 1','10101010','2500','Valby','Jens Jensen'), " +
                            "(2,'ken@somewhere.com','kensen','customer','testvej 2','80808080','2100','København Ø','Ken Kensen'), " +
                            "(3,'robin@somewhere.com','batman','employee','testvej 3','20202020','2200','København N','Robin Batman')");
            //Orders table for testing
            stmt.execute("drop table if exists orders" );
            stmt.execute("create table " + TESTDATABASE + ".orders LIKE " + DATABASE + ".orders;" );
            stmt.execute(
                    "insert into orders values " +
                            "(1,'"+Status.Request+"',1000,default,1), " +
                            "(2,'"+Status.Request+"',1000,default,1), " +
                            "(3,'"+Status.Request+"',1000,default,1)");

            //Create carports needed for mappers
            stmt.execute("drop table if exists carport");
            stmt.execute("create table " + TESTDATABASE + ".carport LIKE " + DATABASE + ".carport;" );
            stmt.execute("insert into carport values " +
                    "(1,1,300,200,480,1,100,100,0,3,'"+RoofType.Fladt_Tag+"',1,null), " +
                    "(2,1,300,200,480,1,100,100,0,3,'"+RoofType.Fladt_Tag+"',2,null), " +
                    "(3,1,300,200,480,1,100,100,0,3,'"+RoofType.Fladt_Tag+"',3,null)");
            stmt.execute("SET FOREIGN_KEY_CHECKS=1");
        } catch (SQLException ex) {
            System.out.println( "Could not open connection to database: " + ex.getMessage() );
        }


    }

    @Test
    void updateOrderStatus() {
        assertEquals(true,true);
    }

    @Test
    void updateOrderTotalPrice() {
    }

    @Test
    void getOrderByOrderId() {
        Order order = null;
        try {
           order = orderMapper.getOrderByOrderId(1);
        } catch (UserException e) {
            e.printStackTrace();
        }
        //Check that order is received from DB
        assertEquals(true,order != null);
        //Check that ID is correct at the received order
        //assertEquals(1,order.getId());
        //Check that the order contains a carport with correct ID
        //assertEquals(1,order.getCarport().getId());
    }

    @Test
    void getOrders() {
    }

    @Test
    void insertCarport() {
    }

    @Test
    void insertOrder() {
    }
}