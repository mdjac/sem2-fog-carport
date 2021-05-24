package business.persistence;

import business.entities.User;
import business.exceptions.UserException;
import business.utilities.Encryption;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import web.StaticValues;

import java.sql.SQLException;
import java.sql.Statement;
import static org.junit.jupiter.api.Assertions.*;

public class UserMapperTest {
    private final static String DATABASE = "carport";  // Change this to your own database
    private final static String TESTDATABASE = DATABASE + "_test";
    private final static String USER = "dev";
    private final static String PASSWORD = "DevUser21!";
    private final static String URL = "jdbc:mysql://64.227.113.104:3306/" + TESTDATABASE + "?serverTimezone=CET&useSSL=false";

    private static Database database;
    private static UserMapper userMapper;


    @BeforeAll
    public static void setUpClass() {
        try {
            database = new Database(USER, PASSWORD, URL);
            userMapper = new UserMapper(database);
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
                    "(1,'jens@somewhere.com','jensen','customer','testvej 1','10101010','2500','Valby','Jens Jensen'), " +
                    "(2,'ken@somewhere.com','kensen','customer','testvej 2','80808080','2100','København Ø','Ken Kensen'), " +
                    "(3,'robin@somewhere.com','batman','employee','testvej 3','20202020','2200','København N','Robin Batman')");
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
        User user = userMapper.login( "jens@somewhere.com", "jensen" );
        assertTrue( user != null );
    }

    @Test
    public void testLogin02() throws UserException {
        // We should get an exception if we use the wrong password
        assertThrows(UserException.class, () ->
            {User user = userMapper.login( "jens@somewhere.com", "larsen" ); });

    }

    @Test
    public void testLogin03() throws UserException {
        // Jens is supposed to be a customer
        User user = userMapper.login( "jens@somewhere.com", "jensen" );
        assertEquals( "customer", user.getRole() );
    }


    @Test
    public void testCreateUser01() throws UserException {
        // Can we create a new user - Notice, if login fails, this will fail
        // but so would login01, so this is OK
        User original = new User( "king@kong.com","employee","KingKongVej 2","88888888",2500,"Valby","King Kong");
        String password = "uhahvorhemmeligt";
        userMapper.createUser(original,password);
        User retrieved = userMapper.login( "king@kong.com", "uhahvorhemmeligt" );
        assertEquals( "employee", retrieved.getRole());
        assertEquals("KingKongVej 2",retrieved.getAddress());
    }


    @Test
    public void testCreateUser02() throws UserException {
        // Can we create a new user - Notice, if login fails, this will fail
        // but so would login01, so this is OK
        //With encryption
        User original = new User( "king@kong.com","employee","KingKongVej 2","88888888",2500,"Valby","King Kong");
        String password = "uhahvorhemmeligt";
        String encryptedPassword = Encryption.encryptThisString(password);
        userMapper.createUser(original,encryptedPassword);
        User retrieved = userMapper.login( "king@kong.com", Encryption.encryptThisString(password));
        assertEquals( "employee", retrieved.getRole());
        assertEquals("KingKongVej 2",retrieved.getAddress());
    }
}
