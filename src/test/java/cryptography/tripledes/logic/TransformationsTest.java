package cryptography.tripledes.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransformationsTest {
    @Test
    void key8Transformation() {
        byte[] key = new byte[64];
        for (int i = 0; i < 64; i++) {
            key[i] = (byte) (1);
            if (i % 8 == 7) {
                key[i] = (byte) (0);
            }
        }
        byte[][] bits = Transformations.key8Transformation(key);
        assertNotNull(bits);
        assertEquals(28, bits[0].length);
        assertEquals(28, bits[1].length);
        for (int i = 0; i < 28; i++) {
            assertEquals(1, bits[0][i]);
            assertEquals(1, bits[1][i]);
        }
    }
}