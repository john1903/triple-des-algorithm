package cryptography.tripledes.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DesEncryptionTest {
    private DesEncryption desEncryption;

    @BeforeEach
    void setUp() {
        desEncryption = new DesEncryption();
    }

    @Test
    void encryption() {
        String keyString = "0123456789ABCDEF";
        byte[] key = KeyGenerator.hexStringToBits(keyString);
        byte[] text = Transformations.stringToBits("01234567");
        byte[] encrypted = desEncryption.encryption(text, key);
        assertNotNull(encrypted);
        assertEquals(64, encrypted.length);
        System.out.println();
        byte[] decrypted = desEncryption.decryption(encrypted, key);
        assertNotNull(decrypted);
        assertEquals(64, decrypted.length);
        assertEquals("01234567", Transformations.bitsToString(decrypted));
    }
}