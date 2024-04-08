package cryptography.tripledes.managers;

import cryptography.tripledes.logic.EncryptionInterface;

import java.util.Arrays;

public class EncryptionManager {
    private final EncryptionInterface encryptor;
    private final int blockSize = 8;

    public EncryptionManager(EncryptionInterface encryptor) {
        this.encryptor = encryptor;
    }

    private void processBlock(byte[] block, byte[] key1, byte[] key2, byte[] key3, boolean isEncrypting) {
        byte[] blockBinary = new byte[blockSize * 8];
        for (int i = 0; i < blockSize; i++) {
            byte b = block[i];
            for (int j = 7; j >= 0; j--) {
                blockBinary[i * 8 + (7 - j)] = (byte) ((b >> j) & 1);
            }
        }
        if (isEncrypting) {
            blockBinary = encryptor.encryption(blockBinary, key1);
            blockBinary = encryptor.encryption(blockBinary, key2);
            blockBinary = encryptor.encryption(blockBinary, key3);
        } else {
            blockBinary = encryptor.decryption(blockBinary, key3);
            blockBinary = encryptor.decryption(blockBinary, key2);
            blockBinary = encryptor.decryption(blockBinary, key1);
        }
        for (int i = 0; i < blockSize; i++) {
            byte b = 0;
            for (int j = 0; j < 8; j++) {
                b |= (byte) (blockBinary[i * 8 + j] << (7 - j));
            }
            block[i] = b;
        }
    }

    public byte[] encrypt(byte[] data, byte[] key1, byte[] key2, byte[] key3) {
        int paddingLength = blockSize - (data.length % blockSize);
        byte[] paddedData = Arrays.copyOf(data, data.length + paddingLength);
        Arrays.fill(paddedData, data.length, paddedData.length, (byte) paddingLength);
        byte[] encryptedData = new byte[paddedData.length];
        int index = 0;
        while (index < paddedData.length) {
            byte[] block = Arrays.copyOfRange(paddedData, index, index + blockSize);
            processBlock(block, key1, key2, key3, true);
            System.arraycopy(block, 0, encryptedData, index, blockSize);
            index += blockSize;
        }
        return encryptedData;
    }

    public byte[] decrypt(byte[] encryptedData, byte[] key1, byte[] key2, byte[] key3) {
        byte[] decryptedData = new byte[encryptedData.length];
        int index = 0;
        while (index < encryptedData.length) {
            byte[] block = Arrays.copyOfRange(encryptedData, index, index + blockSize);
            processBlock(block, key1, key2, key3, false);
            System.arraycopy(block, 0, decryptedData, index, blockSize);
            index += blockSize;
        }
        int paddingLength = decryptedData[decryptedData.length - 1];
        return Arrays.copyOfRange(decryptedData, 0, decryptedData.length - paddingLength);
    }
}