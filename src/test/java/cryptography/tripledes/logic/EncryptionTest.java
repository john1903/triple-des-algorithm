package cryptography.tripledes.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EncryptionTest {
    @Test
    void encryption() {
        String keyString = "0123456789ABCDEF";
        byte[] key = KeyGenerator.hexStringToBits(keyString);
        byte[] text = Transformations.stringToBits("01234567");
        byte[] encrypted = Encryption.encryption(text, key, 0);
        assertNotNull(encrypted);
        assertEquals(64, encrypted.length);
        System.out.println();
        byte[] decrypted = Encryption.encryption(encrypted, key, 1);
        assertNotNull(decrypted);
        assertEquals(64, decrypted.length);
        assertEquals("01234567", Transformations.bitsToString(decrypted));
    }
}