package me.jangluzniewicz.tripledes.logic;

public class KeyGenerator implements KeyGeneratorInterface {
    private static byte[] hexCharToBits(char hexChar) {
        int value = Character.digit(hexChar, 16);
        byte[] bits = new byte[4];
        for (int i = 0; i < 4; i++) {
            bits[i] = (byte) ((value >> i) & 1);
        }
        return bits;
    }

    @Override
    public byte[] convertKeyToBits(String hex) {
        byte[] bits = new byte[hex.length() * 4];
        int index = 0;
        for (int i = 0; i < hex.length(); i++) {
            char c = hex.charAt(i);
            byte[] charBits = hexCharToBits(c);
            System.arraycopy(charBits, 0, bits, index, charBits.length);
            index += 4;
        }
        return bits;
    }

    @Override
    public String generateKey() {
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            key.append(Integer.toHexString((int) (Math.random() * 16)));
        }
        return key.toString();
    }
}