package cryptography.tripledes.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransformationsTest {
    @Test
    void leftShifts() {
        byte[] bits = new byte[28];
        for (int i = 0; i < 28; i++) {
            bits[i] = (byte) (1);
        }
        byte[] shifted = Transformations.leftShift(bits, 16);
        assertNotNull(shifted);
        assertEquals(28, shifted.length);
        assertEquals(0, shifted[27]);
    }

    @Test
    void arrayCombine() {
        byte[] array1 = new byte[8];
        byte[] array2 = new byte[8];
        for (int i = 0; i < 8; i++) {
            array1[i] = (byte) (1);
            array2[i] = (byte) (0);
        }
        byte[] combinedArray = Transformations.arrayCombine(array1, array2);
        assertNotNull(combinedArray);
        assertEquals(16, combinedArray.length);
        for (int i = 0; i < 8; i++) {
            assertEquals(1, combinedArray[i]);
            assertEquals(0, combinedArray[i + 8]);
        }
    }
}