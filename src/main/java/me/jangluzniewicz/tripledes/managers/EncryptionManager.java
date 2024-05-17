package me.jangluzniewicz.tripledes.managers;

import me.jangluzniewicz.tripledes.logic.EncryptionInterface;

import java.util.BitSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class EncryptionManager {
    private final EncryptionInterface encryptor;
    private final int blockSize = 64;
    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

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

    public BitSet encrypt(BitSet data, BitSet key1, BitSet key2, BitSet key3) throws
            InterruptedException, ExecutionException {
        int length = data.length();
        int paddingLength = blockSize - (length % blockSize);
        if (paddingLength == blockSize) {
            paddingLength = 0;
        }
        BitSet paddedData = (BitSet) data.clone();
        for (int i = 0; i < paddingLength; i++) {
            paddedData.clear(length + i);
        }

        List<Future<BitSet>> futures = new ArrayList<>();
        for (int index = 0; index < paddedData.length(); index += blockSize) {
            final int blockIndex = index;
            Future<BitSet> future = executor.submit(() -> {
                BitSet block = paddedData.get(blockIndex, blockIndex + blockSize);
                return processBlock(block, key1, key2, key3, true);
            });
            futures.add(future);
        }

        BitSet encryptedData = new BitSet(paddedData.length());
        for (int i = 0; i < futures.size(); i++) {
            BitSet block = futures.get(i).get();
            int blockIndex = i * blockSize;
            for (int j = 0; j < blockSize; j++) {
                if (block.get(j)) {
                    encryptedData.set(blockIndex + j);
                } else {
                    encryptedData.clear(blockIndex + j);
                }
            }
        }
        return encryptedData;
    }

    public BitSet decrypt(BitSet encryptedData, BitSet key1, BitSet key2, BitSet key3) throws
            InterruptedException, ExecutionException {
        List<Future<BitSet>> futures = new ArrayList<>();
        for (int index = 0; index < encryptedData.length(); index += blockSize) {
            final int blockIndex = index;
            Future<BitSet> future = executor.submit(() -> {
                BitSet block = encryptedData.get(blockIndex, blockIndex + blockSize);
                return processBlock(block, key1, key2, key3, false);
            });
            futures.add(future);
        }

        BitSet decryptedData = new BitSet(encryptedData.length());
        for (int i = 0; i < futures.size(); i++) {
            BitSet block = futures.get(i).get();
            int blockIndex = i * blockSize;
            for (int j = 0; j < blockSize; j++) {
                if (block.get(j)) {
                    decryptedData.set(blockIndex + j);
                } else {
                    decryptedData.clear(blockIndex + j);
                }
            }
        }
        int originalLength = decryptedData.length();
        while (originalLength > 0 && !decryptedData.get(originalLength - 1)) {
            originalLength--;
        }
        return decryptedData.get(0, originalLength);
    }

    public void shutdown() {
        executor.shutdown();
    }
}