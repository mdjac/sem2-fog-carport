package business.persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

/*
class OrderLineMapperTest {
    private final static String DATABASE = "carport";  // Change this to your own database
    private final static String TESTDATABASE = DATABASE + "_test";
    private final static String USER = "dev";
    private final static String PASSWORD = "DevUser21!";
    private final static String URL = "jdbc:mysql://167.172.176.18:3306/" + TESTDATABASE + "?serverTimezone=CET&useSSL=false";

    private static Database database;
    private static OrderLineMapper orderLineMapper;

    @BeforeAll
    public static void setUpClass() {
        try {
            database = new Database(USER, PASSWORD, URL);
            orderLineMapper = new OrderLineMapper(database);
        } catch (ClassNotFoundException e) {   // kan ikke finde driveren i database klassen
            fail("Database connection failed. Missing jdbc driver");
        }
    }


    @BeforeEach
    void setUp() {
        //reset test database
        try ( Statement stmt = database.connect().createStatement() ) {
            stmt.execute("drop table if exists order_line" );
            stmt.execute("drop table if exists orders" );
            stmt.execute("create table " + TESTDATABASE + ".order_line LIKE " + DATABASE + ".order_line;");
            stmt.execute("create table " + TESTDATABASE + ".orders LIKE " + DATABASE + ".orders;");


            //Inserts some data into users, orders and carport to avoid Foreign Key constraints
            stmt.execute("insert into users values(100000,'test@test.dk','test','customer','testvej','11111111','2500','Valby','Test Person')");
            stmt.execute("insert into orders values (100000,'Forespørgsel',20000,'2021-05-16 12:49:24',100000)");
            stmt.execute("insert into carport values (100000,1,300,200,480,1,100,100,0,3,'Fladt tag',100000,null)");

            stmt.execute("insert into order_line values (1000,10,10000,'stk',19,'Spær, monteres på rem')");

        } catch (SQLException ex) {
            System.out.println( "Could not open connection to database: " + ex.getMessage() );
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void deleteOrderLine() {
    }

    @Test
    void getOrderLinesByOrderId() {
    }

    @Test
    void insertOrderLine() {
    }

    @Test
    void updateOrderline() {
    }
}*/
