package business.services;

import business.entities.User;
import business.exceptions.UserException;
import business.persistence.Database;
import business.persistence.UserMapper;
import business.utilities.Encryption;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class UserFacadeTest {
    private final static String DATABASE = "carport";  // Change this to your own database
    private final static String TESTDATABASE = DATABASE + "_test";
    private final static String USER = "dev";
    private final static String PASSWORD = "DevUser21!";
    private final static String URL = "jdbc:mysql://64.227.113.104:3306/" + TESTDATABASE + "?serverTimezone=CET&useSSL=false";

    private static Database database;
    private static UserFacade userFacade;


    @BeforeAll
    public static void setUpClass() {
        try {
            database = new Database(USER, PASSWORD, URL);
            userFacade = new UserFacade(database);
        } catch (ClassNotFoundException e) {   // kan ikke finde driveren i database klassen
            fail("Database connection failed. Missing jdbc driver");
        }
    }

    @BeforeEach
    public void setUp() {
        // reset test database
        try ( Statement stmt = database.connect().createStatement() ) {
            stmt.execute("SET FOREIGN_KEY_CHECKS=0");
            stmt.execute("drop table if exists users" );
            stmt.execute("SET FOREIGN_KEY_CHECKS=1");
            stmt.execute("create table " + TESTDATABASE + ".users LIKE " + DATABASE + ".users;" );
            stmt.execute(
                    "insert into users values " +
                            "(1,'jens@somewhere.com','c87f42bd454c031b875b76c76b0412feb4649fe67a17cc9bde2c2f31a74fbaf4','customer','testvej 1','10101010','2500','Valby','Jens Jensen'), " +
                            "(2,'ken@somewhere.com','e8c73fca587f2d0e21fcd8fcadb65c726e9b31ea9abb3b324cd040e5da4f1000','customer','testvej 2','80808080','2100','København Ø','Ken Kensen'), " +
                            "(3,'robin@somewhere.com','1532e76dbe9d43d0dea98c331ca5ae8a65c5e8e8b99d3e2a42ae989356f6242a','employee','testvej 3','20202020','2200','København N','Robin Batman')");
        } catch (SQLException ex) {
            System.out.println( "Could not open connection to database: " + ex.getMessage() );
        }
    }

    @Test
    public void testSetUpOK() {
        // Just check that we have a connection.
        assertNotNull(database);
    }

    @Test
    public void testLogin01() throws UserException {
        // Can we log in
        User user = userFacade.login( "jens@somewhere.com", "jensen" );
        assertTrue( user != null );
    }

    @Test
    public void testLogin02() throws UserException {
        // We should get an exception if we use the wrong password
        assertThrows(UserException.class, () ->
        {User user = userFacade.login( "jens@somewhere.com", "larsen" ); });

    }

    @Test
    public void testLogin03() throws UserException {
        // robin is supposed to be a employee
        User user = userFacade.login( "robin@somewhere.com", "batman");
        assertEquals( "employee", user.getRole());
    }

    @Test
    public void testCreateUser01() throws UserException {
        // Can we create a new user - Notice, if login fails, this will fail
        // but so would login01, so this is OK
        String password = Encryption.encryptThisString("uhahvorhemmeligt");
        userFacade.createUser("king@kong.com",password,"KingKongVej 2","88888888",2500,"Valby","King Kong");
        User retrieved = userFacade.login( "king@kong.com", password );
        assertEquals( "customer", retrieved.getRole());
        assertEquals("KingKongVej 2",retrieved.getAddress());
    }
}