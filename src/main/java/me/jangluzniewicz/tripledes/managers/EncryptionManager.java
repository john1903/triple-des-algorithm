package me.jangluzniewicz.tripledes.managers;

import me.jangluzniewicz.tripledes.logic.EncryptionInterface;

import java.util.BitSet;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * The EncryptionManager class manages the encryption and decryption processes
 * using the provided EncryptionInterface implementation.
 */
public class EncryptionManager {
    private final EncryptionInterface encryptor; // Encryption algorithm implementation
    private final int blockSize = 64; // Block size in bits
    final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()); // Thread pool for parallel processing

    /**
     * Constructs an EncryptionManager with the given EncryptionInterface implementation.
     *
     * @param encryptor the EncryptionInterface implementation
     */
    public EncryptionManager(EncryptionInterface encryptor) {
        this.encryptor = encryptor;
    }

    /**
     * Processes a single block of data using the given keys.
     *
     * @param block the block of data to be processed
     * @param key1 the first key
     * @param key2 the second key
     * @param key3 the third key
     * @param isEncrypting true if encrypting, false if decrypting
     * @return the processed block of data
     */
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

    /**
     * Encrypts the given data using the provided keys.
     *
     * @param data the data to be encrypted
     * @param key1 the first key
     * @param key2 the second key
     * @param key3 the third key
     * @return the encrypted data
     * @throws InterruptedException if the encryption process is interrupted
     * @throws ExecutionException if the encryption process fails
     */
    public BitSet encrypt(BitSet data, BitSet key1, BitSet key2, BitSet key3) throws InterruptedException, ExecutionException {
        int length = data.length();
        int paddingLength = (blockSize - (length % blockSize)) % blockSize;
        BitSet paddedData = (BitSet) data.clone();

        // Add padding to the data
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

    /**
     * Decrypts the given encrypted data using the provided keys.
     *
     * @param encryptedData the encrypted data to be decrypted
     * @param key1 the first key
     * @param key2 the second key
     * @param key3 the third key
     * @return the decrypted data
     * @throws InterruptedException if the decryption process is interrupted
     * @throws ExecutionException if the decryption process fails
     */
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

        // Remove padding
        int originalLength = decryptedData.length();
        while (originalLength > 0 && !decryptedData.get(originalLength - 1)) {
            originalLength--;
        }
        return decryptedData.get(0, originalLength);
    }

    /**
     * Shuts down the executor service gracefully, waiting up to 60 seconds for termination.
     */
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