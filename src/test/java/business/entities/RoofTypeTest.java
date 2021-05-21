package business.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoofTypeTest {

    @Test
    void fromString() {
        String inputString = RoofType.Fladt_Tag.toString();
        RoofType expected = RoofType.fromString(inputString);
        assertEquals(expected,RoofType.Fladt_Tag);

        String failInputString = "test";
        RoofType failExpected = RoofType.fromString(failInputString);
        assertTrue(failExpected == null);
    }
}