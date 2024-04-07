package cryptography.tripledes.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransformationsTest {
    @Test
    void stringToBits() {
        byte[] bits = Transformations.stringToBits("Hello");
        assertNotNull(bits);
        assertEquals(40, bits.length);
        assertEquals("Hello", Transformations.bitsToString(bits));
    }

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

    @Test
    void arrayDeCombine() {
        byte[] array = new byte[16];
        for (int i = 0; i < 8; i++) {
            array[i] = (byte) (1);
        }
        for (int i = 8; i < 16; i++) {
            array[i] = (byte) (0);
        }
        byte[][] deCombinedArray = Transformations.arrayDeCombine(array, 8);
        assertNotNull(deCombinedArray);
        assertEquals(2, deCombinedArray.length);
        assertEquals(8, deCombinedArray[0].length);
        assertEquals(8, deCombinedArray[1].length);
        for (int i = 0; i < 8; i++) {
            assertEquals(1, deCombinedArray[0][i]);
            assertEquals(0, deCombinedArray[1][i]);
        }
    }

    @Test
    void xor() {
        byte[] array1 = new byte[8];
        byte[] array2 = new byte[8];
        for (int i = 0; i < 8; i++) {
            array1[i] = (byte) (1);
            array2[i] = (byte) (0);
        }
        byte[] result = Transformations.xor(array1, array2);
        assertNotNull(result);
        assertEquals(8, result.length);
        for (int i = 0; i < 8; i++) {
            assertEquals(1, result[i]);
        }
    }
}