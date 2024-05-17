package me.jangluzniewicz.tripledes.managers;

import me.jangluzniewicz.tripledes.logic.EncryptionInterface;

import java.util.BitSet;

public class EncryptionManager {
    private final EncryptionInterface encryptor;
    private final int blockSize = 64;

    public EncryptionManager(EncryptionInterface encryptor) {
        this.encryptor = encryptor;
    }

    private BitSet processBlock(BitSet block, BitSet key1, BitSet key2, BitSet key3, boolean isEncrypting) {
        if (isEncrypting) {
            block = encryptor.encryption(block, key1);
            block = encryptor.encryption(block, key2);
            block = encryptor.encryption(block, key3);
        } else {
            block = encryptor.decryption(block, key3);
            block = encryptor.decryption(block, key2);
            block = encryptor.decryption(block, key1);
        }
        return block;
    }

    public BitSet encrypt(BitSet data, BitSet key1, BitSet key2, BitSet key3) {
        int length = data.length();
        int paddingLength = blockSize - (length % blockSize);
        if (paddingLength == blockSize) {
            paddingLength = 0; // No padding needed if already aligned
        }
        BitSet paddedData = (BitSet) data.clone();
        // Dopełnianie zerami
        for (int i = 0; i < paddingLength; i++) {
            paddedData.clear(length + i);
        }

        BitSet encryptedData = new BitSet(paddedData.length());
        for (int index = 0; index < paddedData.length(); index += blockSize) {
            BitSet block = paddedData.get(index, index + blockSize);
            block = processBlock(block, key1, key2, key3, true);
            for (int i = 0; i < blockSize; i++) {
                if (block.get(i)) {
                    encryptedData.set(index + i);
                } else {
                    encryptedData.clear(index + i);
                }
            }
        }
        return encryptedData;
    }

    public BitSet decrypt(BitSet encryptedData, BitSet key1, BitSet key2, BitSet key3) {
        BitSet decryptedData = new BitSet(encryptedData.length());
        for (int index = 0; index < encryptedData.length(); index += blockSize) {
            BitSet block = encryptedData.get(index, index + blockSize);
            block = processBlock(block, key1, key2, key3, false);
            for (int i = 0; i < blockSize; i++) {
                if (block.get(i)) {
                    decryptedData.set(index + i);
                } else {
                    decryptedData.clear(index + i);
                }
            }
        }
        int originalLength = decryptedData.length();
        while (originalLength > 0 && !decryptedData.get(originalLength - 1)) {
            originalLength--;
        }
        return decryptedData.get(0, originalLength);
    }
}