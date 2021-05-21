package business.entities;

import business.persistence.Database;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import web.StaticValues;

import static org.junit.jupiter.api.Assertions.*;

class MinMaxTest {


    @Test
    void between() {
        double min = 10;
        double max = 20;
        MinMax minMax = new MinMax(min,max);
        int testValue = 15;
        assertEquals(true,minMax.between(testValue));
        testValue = 9;
        assertEquals(false,minMax.between(testValue));
        testValue = 10;
        assertTrue(minMax.between(testValue));
        testValue = 20;
        assertTrue(minMax.between(testValue));
        testValue = 21;
        assertTrue(!minMax.between(testValue));
    }

    @Test
    void checkThatValueIsNullIfMinMaxIsDifferent() {
        double min = 10;
        double max = 20;
        MinMax minMax = new MinMax(min,max);
        assertEquals(null,minMax.getValue());
    }

    @Test
    void checkThatValueIsNotNullIfMinMaxIsSame() {
        double min = 10;
        double max = 10;
        MinMax minMax = new MinMax(min,max);
        assertEquals(10,minMax.getValue());
    }

    @Test
    void testToString() {
        double min = 10;
        double max = 20;
        MinMax minMax = new MinMax(min,max);
        String expected = "(Min: 10 - Max: 20 Tilladt)";
        assertEquals(expected,minMax.toString());
    }
}