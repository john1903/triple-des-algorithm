package me.jangluzniewicz.tripledes.managers;

import me.jangluzniewicz.tripledes.logic.EncryptionInterface;

import java.util.BitSet;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class EncryptionManager {
    private final EncryptionInterface encryptor;
    private final int blockSize = 64;
    final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

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

    public BitSet encrypt(BitSet data, BitSet key1, BitSet key2, BitSet key3) throws InterruptedException, ExecutionException {
        int length = data.length();
        int paddingLength = (blockSize - (length % blockSize)) % blockSize;
        BitSet paddedData = (BitSet) data.clone();

        for (int i = 0; i < paddingLength; i++) {
            paddedData.set(length + i, false);
        }

        int totalBlocks = (paddedData.length() + blockSize - 1) / blockSize;
        List<Future<BitSet>> futures = IntStream.range(0, totalBlocks).mapToObj(index -> executor.submit(() -> {
            int blockIndex = index * blockSize;
            BitSet block = paddedData.get(blockIndex, blockIndex + blockSize);
            return processBlock(block, key1, key2, key3, true);
        })).toList();

        BitSet encryptedData = new BitSet(totalBlocks * blockSize);
        for (int i = 0; i < futures.size(); i++) {
            BitSet block = futures.get(i).get();
            int blockIndex = i * blockSize;
            for (int j = 0; j < blockSize; j++) {
                encryptedData.set(blockIndex + j, block.get(j));
            }
        }

        return encryptedData;
    }

    public BitSet decrypt(BitSet encryptedData, BitSet key1, BitSet key2, BitSet key3) throws InterruptedException, ExecutionException {
        int totalBlocks = (encryptedData.length() + blockSize - 1) / blockSize;
        List<Future<BitSet>> futures = IntStream.range(0, totalBlocks).mapToObj(index -> executor.submit(() -> {
            int blockIndex = index * blockSize;
            BitSet block = encryptedData.get(blockIndex, blockIndex + blockSize);
            return processBlock(block, key1, key2, key3, false);
        })).toList();

        BitSet decryptedData = new BitSet(totalBlocks * blockSize);
        for (int i = 0; i < futures.size(); i++) {
            BitSet block = futures.get(i).get();
            int blockIndex = i * blockSize;
            for (int j = 0; j < blockSize; j++) {
                decryptedData.set(blockIndex + j, block.get(j));
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
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}