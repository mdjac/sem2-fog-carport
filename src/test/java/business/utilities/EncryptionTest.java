package business.utilities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EncryptionTest {

    @Test
    void encryptThisString() {
        String before = "test";
        String after = Encryption.encryptThisString(before);
        String expected = "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08";
        assertEquals(expected,after);
    }
}