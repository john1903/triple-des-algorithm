package cryptography.tripledes.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransformationsTest {
    Transformations transformations;

    @BeforeEach
    void setUp() {
        transformations = new Transformations();
    }

    @Test
    void key8Transformation() {
        byte[] key = new byte[64];
        for (int i = 0; i < 64; i++) {
            key[i] = (byte) (1);
            if (i % 8 == 7) {
                key[i] = (byte) (0);
            }
        }
        byte[] bits = transformations.key8Transformation(key);
        assertNotNull(bits);
        assertEquals(56, bits.length);
        for (int i = 0; i < 56; i++) {
            assertEquals(1, bits[i]);
        }
    }
}