package cryptography.tripledes.logic;

public class Transformations {
    public static byte[] leftShift(byte[] bits, int shift) {
        int num = 0;
        for (byte bit : bits) {
            num = (num << 1) | bit;
        }
        num <<= shift;
        byte[] shiftedBits = new byte[bits.length];
        for (int i = bits.length - 1; i >= 0; i--) {
            shiftedBits[i] = (byte) (num & 1);
            num >>= 1;
        }
        return shiftedBits;
    }

    public static byte[] stringToBits(String input) {
        byte[] bits = new byte[input.length() * 8];
        for (int i = 0; i < input.length(); i++) {
            String charBits = Integer.toBinaryString(input.charAt(i));
            int padding = 8 - charBits.length();
            for (int j = 0; j < padding; j++) {
                bits[i * 8 + j] = 0;
            }
            for (int j = 0; j < charBits.length(); j++) {
                bits[i * 8 + padding + j] = (byte) (charBits.charAt(j) - '0');
            }
        }
        return bits;
    }

    public static byte[] arrayCombine(byte[] array1, byte[] array2) {
        byte[] combinedArray = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, combinedArray, 0, array1.length);
        System.arraycopy(array2, 0, combinedArray, array1.length, array2.length);
        return combinedArray;
    }

    public static byte[][] arrayDeCombine(byte[] array, int length) {
        byte[][] deCombinedArray = new byte[2][length];
        System.arraycopy(array, 0, deCombinedArray[0], 0, length);
        System.arraycopy(array, length, deCombinedArray[1], 0, length);
        return deCombinedArray;
    }
}
