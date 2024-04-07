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

//    @Test
//    void getKeysArray() {
//        byte[] key =
//                { 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1,
//                        0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1 };
//        byte[] expectedFirst = {0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
//                0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0};
//        byte[] expectedLast = {1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1,
//                1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1};
//        byte[][] keyArray = Encryption.getKeysArray(key);
//        for (int i = 0; i < keyArray[0].length; i++) {
//            assertEquals(keyArray[0][i], expectedFirst[i]);
//            assertEquals(keyArray[15][i], expectedLast[i]);
//        }
//    }
}