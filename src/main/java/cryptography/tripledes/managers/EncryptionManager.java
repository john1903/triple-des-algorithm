package cryptography.tripledes.managers;

import cryptography.tripledes.logic.EncryptionInterface;

public class EncryptionManager {
    private final EncryptionInterface encryptor;

    public EncryptionManager(EncryptionInterface encryptor) {
        this.encryptor = encryptor;
    }

    public byte[] encryptData(byte[] data, byte[] key1, byte[] key2, byte[] key3) {
        byte[] encryptedData = new byte[data.length];
        int index = 0;
        while (index < data.length) {
            byte[] block = new byte[8];
            int length = Math.min(8, data.length - index);
            System.arraycopy(data, index, block, 0, length);
            byte[] blockBinary = new byte[64];
            for (int i = 0; i < 8; i++) {
                byte b = block[i];
                for (int j = 7; j >= 0; j--) {
                    blockBinary[i * 8 + (7 - j)] = (byte) ((b >> j) & 1);
                }
            }
            blockBinary = encryptor.encryption(blockBinary, key1);
            blockBinary = encryptor.encryption(blockBinary, key2);
            blockBinary = encryptor.encryption(blockBinary, key3);
            for (int i = 0; i < 8; i++) {
                byte b = 0;
                for (int j = 0; j < 8; j++) {
                    b |= (byte) (blockBinary[i * 8 + j] << (7 - j));
                }
                block[i] = b;
            }
            System.arraycopy(block, 0, encryptedData, index, length);
            index += 8;
        }
        return encryptedData;
    }

    public byte[] decryptData(byte[] encryptedData, byte[] key1, byte[] key2, byte[] key3) {
        byte[] decryptedData = new byte[encryptedData.length];
        int index = 0;
        while (index < encryptedData.length) {
            byte[] block = new byte[8];
            int length = Math.min(8, encryptedData.length - index);
            System.arraycopy(encryptedData, index, block, 0, length);
            byte[] blockBinary = new byte[64];
            for (int i = 0; i < 8; i++) {
                byte b = block[i];
                for (int j = 7; j >= 0; j--) {
                    blockBinary[i * 8 + (7 - j)] = (byte) ((b >> j) & 1);
                }
            }
            blockBinary = encryptor.decryption(blockBinary, key3);
            blockBinary = encryptor.decryption(blockBinary, key2);
            blockBinary = encryptor.decryption(blockBinary, key1);
            for (int i = 0; i < 8; i++) {
                byte b = 0;
                for (int j = 0; j < 8; j++) {
                    b |= (byte) (blockBinary[i * 8 + j] << (7 - j));
                }
                block[i] = b;
            }
            System.arraycopy(block, 0, decryptedData, index, length);
            index += 8;
        }
        return decryptedData;
    }
}
