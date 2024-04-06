package cryptography.tripledes.logic;

public class KeyGenerator {
    private static byte[] hexCharToBits(char hexChar) {
        int value = Character.digit(hexChar, 16);
        byte[] bits = new byte[4];
        for (int i = 0; i < 4; i++) {
            bits[i] = (byte) ((value >> i) & 1);
        }
        return bits;
    }

    public static byte[] hexStringToBits(String hexString) {
        byte[] bits = new byte[hexString.length() * 4];
        int index = 0;
        for (int i = 0; i < hexString.length(); i++) {
            char c = hexString.charAt(i);
            byte[] charBits = hexCharToBits(c);
            System.arraycopy(charBits, 0, bits, index, charBits.length);
            index += 4;
        }
        return bits;
    }

    public static String generateKey() {
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            key.append(Integer.toHexString((int) (Math.random() * 16)));
        }
        return key.toString();
    }
}