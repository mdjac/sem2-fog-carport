package business.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusTest {

    @Test
    void fromString() {
        String inputString = Status.Request.toString();
        Status expected = Status.fromString(inputString);
        assertEquals(expected,Status.Request);

        String failInputString = "test";
        Status failExpected = Status.fromString(failInputString);
        assertTrue(failExpected == null);
    }
}