package cryptography.tripledes.logic;

public class Transformations {
    public static byte[] key8Transformation(byte[] key) {
        byte[] bits = new byte[56];
        int index = 0;
        for (int i = 0; i < 64; i++) {
            if (i % 8 == 7) {
                continue;
            }
            bits[index++] = key[i];
        }
        return bits;
    }
}
