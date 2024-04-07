package cryptography.tripledes.logic;

public class Transformations {
    public static byte[] leftShift(byte[] bits, int shift) {
        byte[] shiftedBits = new byte[bits.length];
        int number = 0;
        for (byte bit : bits) {
            number = (number << 1) | bit;
        }
        number = (number << shift) | (number >> (bits.length - shift));
        for (int i = 0; i < bits.length; i++) {
            shiftedBits[i] = (byte) ((number >> (bits.length - 1 - i)) & 1);
        }
        return shiftedBits;
    }

    public static byte[] arrayCombine(byte[] array1, byte[] array2) {
        byte[] combinedArray = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, combinedArray, 0, array1.length);
        System.arraycopy(array2, 0, combinedArray, array1.length, array2.length);
        return combinedArray;
    }

    public static byte[] xor(byte[] array1, byte[] array2) {
        byte[] result = new byte[array1.length];
        for (int i = 0; i < array1.length; i++) {
            result[i] = (byte) (array1[i] ^ array2[i]);
        }
        return result;
    }
}
