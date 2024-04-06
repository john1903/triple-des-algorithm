package cryptography.tripledes.logic;

public class Transformations {
    public static byte[][] key8Transformation(byte[] key) {
        byte[] bits = new byte[56];
        int index = 0;
        for (int i = 0; i < 64; i++) {
            if (i % 8 == 7) {
                continue;
            }
            bits[index++] = key[i];
        }
        byte[][] bitsArray = new byte[2][28];
        for (int i = 0; i < 28; i++) {
            bitsArray[0][i] = bits[i];
            bitsArray[1][i] = bits[i + 28];
        }
        return bitsArray;
    }

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
}
