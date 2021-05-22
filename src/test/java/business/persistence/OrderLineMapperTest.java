package business.persistence;

import business.entities.Material;
import business.entities.OrderLine;
import business.entities.RoofType;
import business.entities.Status;
import business.exceptions.UserException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import web.StaticValues;

import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;


class OrderLineMapperTest {
    private final static String DATABASE = "carport";  // Change this to your own database
    private final static String TESTDATABASE = DATABASE + "_test";
    private final static String USER = "dev";
    private final static String PASSWORD = "DevUser21!";
    private final static String URL = "jdbc:mysql://167.172.176.18:3306/" + TESTDATABASE + "?serverTimezone=CET&useSSL=false";

    private static Database database;
    private static OrderLineMapper orderLineMapper;
    public static Material material1;
    public static OrderLine orderLine1;

    @BeforeAll
    public static void setUpClass() {
        try {
            database = new Database(USER, PASSWORD, URL);
            orderLineMapper = new OrderLineMapper(database);
            new StaticValues().setGlobalValues(database);


                material1 = new Material("test","test",1,1,1,1,10);
                orderLine1 = new OrderLine(10,1,"stk",material1,"test");

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
                            "(1,'"+ Status.Request+"',1000,default,1), " +
                            "(2,'"+Status.Request+"',1000,default,1), " +
                            "(3,'"+Status.Request+"',1000,default,2)");

            //Create carports needed for mappers
            stmt.execute("drop table if exists carport");
            stmt.execute("create table " + TESTDATABASE + ".carport LIKE " + DATABASE + ".carport;" );
            stmt.execute("insert into carport values " +
                    "(1,1,300,200,480,1,100,100,0,3,'"+ RoofType.Fladt_Tag+"',1,null), " +
                    "(2,1,300,200,480,1,100,100,0,3,'"+RoofType.Fladt_Tag+"',2,null), " +
                    "(3,1,300,200,480,1,100,100,0,3,'"+RoofType.Fladt_Tag+"',3,null)");
            stmt.execute("SET FOREIGN_KEY_CHECKS=1");
        } catch (SQLException ex) {
            System.out.println( "Could not open connection to database: " + ex.getMessage() );
        }
    }


    @AfterEach
    void tearDown() {
    }

    @Test
    void deleteOrderLine() {
        /*//Constructor calls CalculateAccumulatedPrice
        int deleteOrder = orderLine1.getId();
        orderLineMapper.deleteOrderLine(deleteOrder);
        assertEquals(0,orderLine1.getId());*/
    }

    @Test
    void getOrderLinesByOrderId() {

        //Constructor calls CalculateAccumulatedPrice
        assertEquals(1,orderLine1.getOrdersID());
    }

    @Test
    void insertOrderLine() throws UserException {

        OrderLine orderLine = new OrderLine(5,3,"stk",material1,"stor orderline");
        orderLineMapper.insertOrderLine(orderLine);

        assertEquals(3,orderLine.getOrdersID());
    }

    @Test
    void updateOrderline() throws UserException {
        orderLine1.setId(3);
        orderLineMapper.updateOrderline(orderLine1);
        assertEquals(3,orderLine1.getId());
    }
}
