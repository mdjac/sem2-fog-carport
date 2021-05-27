package business.services;

import business.entities.*;
import business.exceptions.UserException;
import business.persistence.Database;
import business.persistence.OrderMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import web.StaticValues;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class OrderFacadeTest {
    private final static String DATABASE = "carport";  // Change this to your own database
    private final static String TESTDATABASE = DATABASE + "_test";
    private final static String USER = "dev";
    private final static String PASSWORD = "DevUser21!";
    private final static String URL = "jdbc:mysql://64.227.113.104:3306/" + TESTDATABASE + "?serverTimezone=CET&useSSL=false";

    private static Database database;
    private static OrderFacade orderFacade;

    @BeforeAll
    public static void setUpClass() throws UserException {
        try {
            database = new Database(USER, PASSWORD, URL);
            orderFacade = new OrderFacade(database);
            new StaticValues().setGlobalValues(database);
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

    @Test
    void updateOrderStatus() throws UserException {
        //Fetches order, check that start status is something, then try to changes status and then fetches again and validates new status
        Order order;
        order = orderFacade.getOrderByOrderId(1);
        Status expected = Status.Request;
        assertEquals(expected,order.getStatus());


        orderFacade.updateOrderStatus(order.getId(),Status.OfferSent);

        order = orderFacade.getOrderByOrderId(order.getId());
        expected = Status.OfferSent;
        assertEquals(expected,order.getStatus());
    }

    @Test
    void updateOrderTotalPrice() throws UserException {
        //Fetches order, check that start totalprice is something, then try to changes totalprice and then fetches again and validates new totalprice
        Order order;
        order = orderFacade.getOrderByOrderId(1);
        double expected = 1000;
        assertEquals(expected,order.getTotalPrice());


        orderFacade.updateOrderTotalPrice(order.getId(),100);

        order = orderFacade.getOrderByOrderId(order.getId());
        expected = 100;
        assertEquals(expected,order.getTotalPrice());
    }


    @Test
    void getOrderByOrderId() {
        Order order = null;
        try {
            order = orderFacade.getOrderByOrderId(1);
        } catch (UserException e) {
            e.printStackTrace();
        }
        //Check that order is received from DB
        assertEquals(true,order != null);
        //Check that ID is correct at the received order
        assertEquals(1,order.getId());
        //Check that the order contains a carport with correct ID
        assertEquals(1,order.getCarport().getId());

    }

    @Test
    void getOrdersEmployee() throws UserException {
        TreeMap<Integer,Order> orders = new TreeMap<>();
        //Creates employee user
        User user = new User("test","employee","testvej","10101010",2500,"valby","test person");
        user.setId(3);
        orders = orderFacade.getOrders(user);
        //Employee should receive all orders, so it should be 3 orders
        assertEquals(3,orders.size());
    }

    @Test
    void getOrdersCustomer() throws UserException {
        TreeMap<Integer,Order> orders = new TreeMap<>();
        //Creates Customer user
        User user = new User("test","customer","testvej","10101010",2500,"valby","test person");
        user.setId(1);
        orders = orderFacade.getOrders(user);
        //Customer should only receive his own orders, so it should be 2
        assertEquals(2,orders.size());
    }

    @Test
    void insertOrder() throws UserException {
        //Creates and order and checks that orderID is correct and that the correct carport is associated
        Carport carport = new Carport(Carport.findCarportMaterialFromId(1),350,250,500,RoofType.Fladt_Tag,Carport.findRoofMaterialFromId(3,RoofType.Fladt_Tag));
        Order order = new Order(Status.Request,1,carport);
        order = orderFacade.insertOrder(order,carport);

        assertEquals(false,order == null);
        assertEquals(4,order.getId());
        assertEquals(carport.getCarportHeight(),order.getCarport().getCarportHeight());
        assertEquals(carport.getCarportLength(),order.getCarport().getCarportLength());
        assertEquals(carport.getCarportWidth(),order.getCarport().getCarportWidth());
    }

}