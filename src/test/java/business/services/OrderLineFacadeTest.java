package business.services;

import business.entities.Material;
import business.entities.OrderLine;
import business.entities.RoofType;
import business.entities.Status;
import business.exceptions.UserException;
import business.persistence.Database;
import business.persistence.OrderLineMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import web.StaticValues;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class OrderLineFacadeTest {
    private final static String DATABASE = "carport";  // Change this to your own database
    private final static String TESTDATABASE = DATABASE + "_test";
    private final static String USER = "dev";
    private final static String PASSWORD = "DevUser21!";
    private final static String URL = "jdbc:mysql://64.227.113.104:3306/" + TESTDATABASE + "?serverTimezone=CET&useSSL=false";

    private static Database database;
    private static OrderLineFacade orderLineFacade;
    public static Material material1;
    public static OrderLine orderLine1;

    @BeforeAll
    public static void setUpClass() {
        try {
            database = new Database(USER, PASSWORD, URL);
            orderLineFacade = new OrderLineFacade(database);
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

            //OrderLine table for testing
            stmt.execute("drop table if exists order_line");
            stmt.execute("create table " + TESTDATABASE + ".order_line LIKE " + DATABASE + ".order_line;" );
            stmt.execute("insert into order_line values " +
                    "(1,10,1,'stk',1,'test'), " +
                    "(2,100,1,'stk',2,'test'), " +
                    "(3,50,1,'stk',3,'test');");
            stmt.execute("SET FOREIGN_KEY_CHECKS=1");
        } catch (SQLException ex) {
            System.out.println( "Could not open connection to database: " + ex.getMessage() );
        }
    }


    @Test
    void deleteOrderLine() throws UserException {
        //Fetches orderLines and check that size is as expected, then delete one and check size is new expectation
        TreeMap<Integer, OrderLine> orderLineTreeMap = new TreeMap<>();
        int orderId = 1;
        orderLineTreeMap = orderLineFacade.getOrderLinesByOrderId(orderId);
        assertEquals(3,orderLineTreeMap.size());

        List<Integer> deleteIds = new ArrayList<>();
        deleteIds.add(1);
        deleteIds.add(2);

        orderLineFacade.deleteOrderLine(deleteIds);
        orderLineTreeMap = orderLineFacade.getOrderLinesByOrderId(orderId);
        assertEquals(1,orderLineTreeMap.size());
    }

    @Test
    void getOrderLinesByOrderId() throws UserException {
        //Check that the orderlineTreemap size is the same size as we insert in beforeEach and that the orderLines got correct OrderID
        TreeMap<Integer, OrderLine> orderLineTreeMap = new TreeMap<>();
        int orderId = 1;
        orderLineTreeMap = orderLineFacade.getOrderLinesByOrderId(orderId);
        assertEquals(3,orderLineTreeMap.size());
        assertEquals(orderId,orderLineTreeMap.get(1).getOrdersID());
    }

    @Test
    void insertOrderLine() throws UserException, SQLException {
        //Check that the orderlineTreemap size is the same size as we insert in beforeEach and then we insert another one and check size is 1 more
        TreeMap<Integer, OrderLine> orderLineTreeMap = new TreeMap<>();
        int orderId = 1;
        orderLineTreeMap = orderLineFacade.getOrderLinesByOrderId(orderId);
        assertEquals(3,orderLineTreeMap.size());
        orderLineFacade.insertOrderLine(orderLine1);
        orderLineTreeMap = orderLineFacade.getOrderLinesByOrderId(orderId);
        assertEquals(4,orderLineTreeMap.size());
    }

    @Test
    void updateOrderline() throws UserException {
        //Fetches orderLine from DB and check that quantity is correct, updates quantity to 100 and sets object to null, fetches object again and checks that quantity is now 100.
        TreeMap<Integer, OrderLine> orderLineTreeMap = new TreeMap<>();
        int orderId = 1;
        orderLineTreeMap = orderLineFacade.getOrderLinesByOrderId(orderId);
        OrderLine orderLineTest = orderLineTreeMap.get(1);
        assertEquals(10,orderLineTest.getQuantity());

        orderLineTest.setQuantity(100);
        orderLineFacade.updateOrderline(orderLineTest);
        orderLineTest = null;
        assertEquals(null, orderLineTest);

        orderLineTreeMap = orderLineFacade.getOrderLinesByOrderId(orderId);
        orderLineTest = orderLineTreeMap.get(1);
        assertEquals(100,orderLineTest.getQuantity());
    }

}