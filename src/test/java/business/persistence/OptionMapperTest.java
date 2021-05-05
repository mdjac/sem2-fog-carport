package business.persistence;

import business.entities.Option;
import business.exceptions.UserException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class OptionMapperTest {
    private final static String DATABASE = "carport";  // Change this to your own database
    private final static String TESTDATABASE = DATABASE + "_test";
    private final static String USER = "dev";
    private final static String PASSWORD = "DevUser21!";
    private final static String URL = "jdbc:mysql://167.172.176.18:3306/" + TESTDATABASE + "?serverTimezone=CET&useSSL=false";

    private static Database database;
    private static OptionMapper optionMapper;

    @BeforeAll
    public static void setUpClass() {
        try {
            database = new Database(USER, PASSWORD, URL);
            optionMapper = new OptionMapper(database);
        } catch (ClassNotFoundException e) {   // kan ikke finde driveren i database klassen
            fail("Database connection failed. Missing jdbc driver");
        }
    }

    @BeforeEach
    void setUp() {
        // reset test database
        try ( Statement stmt = database.connect().createStatement() ) {
            stmt.execute("drop table if exists options" );
            stmt.execute("drop table if exists options_variant" );
            stmt.execute("create table " + TESTDATABASE + ".options LIKE " + DATABASE + ".options;" );
            stmt.execute("create table " + TESTDATABASE + ".options_variant LIKE " + DATABASE + ".options_variant;" );

            stmt.execute("INSERT INTO `options` (`name`) VALUES ('Tag type');");
            stmt.execute("INSERT INTO `options_variant` (`name`, `options_id`) VALUES ('Fladt tag', '1');");
            stmt.execute("INSERT INTO `options_variant` (`name`, `options_id`) VALUES ('Tag med rejsning', '1');");

        } catch (SQLException ex) {
            System.out.println( "Could not open connection to database: " + ex.getMessage() );
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllOptions() {
        TreeMap<Integer, Option> options = new TreeMap<>();
        try {
            options = optionMapper.getAllOptions();
        } catch (UserException e) {
            e.printStackTrace();
        }

        //Checks if the option itself is correct
        String expectedOption = options.get(1).getName();
        String actualOption = "Tag type";
        assertEquals(expectedOption,actualOption);

        //Checks if the variants of the option is correct
        String expectedOptionVariant1 = options.get(1).getValues().get(1);
        String actualOptionVariant1 = "Fladt tag";
        assertEquals(expectedOptionVariant1,actualOptionVariant1);

        String expectedOptionVariant2 = options.get(1).getValues().get(2);
        String actualOptionVariant2 = "Tag med rejsning";
        assertEquals(expectedOptionVariant2,actualOptionVariant2);
    }
}