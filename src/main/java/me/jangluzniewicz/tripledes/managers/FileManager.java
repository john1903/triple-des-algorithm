package me.jangluzniewicz.tripledes.managers;

import me.jangluzniewicz.tripledes.dao.FileReaderInterface;

import java.util.BitSet;

/**
 * The FileManager class provides methods for reading from and writing to files using a
 * specified FileReaderInterface implementation. It also handles conversion between
 * byte arrays and BitSets.
 */
public class FileManager {
    private final FileReaderInterface fileReader; // File reader interface for file operations

    /**
     * Constructs a FileManager with the given FileReaderInterface implementation.
     *
     * @param fileReader the FileReaderInterface implementation
     */
    public FileManager(FileReaderInterface fileReader) {
        this.fileReader = fileReader;
    }

    /**
     * Reads the content of a file located at the specified path and converts it to a BitSet.
     *
     * @param path the path to the file to be read
     * @return a BitSet representing the file content
     * @throws Exception if an I/O error occurs
     */
    public BitSet read(String path) throws Exception {
        byte[] data = fileReader.read(path);
        return fromByteArray(data);
    }

    /**
     * Writes the given BitSet content to a file located at the specified path.
     *
     * @param path the path to the file to be written
     * @param content the BitSet content to be written to the file
     * @throws Exception if an I/O error occurs
     */
    public void write(String path, BitSet content) throws Exception {
        byte[] data = toByteArray(content);
        fileReader.write(path, data);
    }

    /**
     * Writes the given byte array content to a file located at the specified path.
     *
     * @param path the path to the file to be written
     * @param content the byte array content to be written to the file
     * @throws Exception if an I/O error occurs
     */
    public void write(String path, byte[] content) throws Exception {
        fileReader.write(path, content);
    }

    /**
     * Converts a byte array to a BitSet.
     *
     * @param bytes the byte array to be converted
     * @return a BitSet representing the byte array
     */
    private BitSet fromByteArray(byte[] bytes) {
        BitSet bits = new BitSet(bytes.length * 8);
        int bitIndex = 0;
        for (byte b : bytes) {
            for (int i = 7; i >= 0; i--) {
                bits.set(bitIndex++, (b & (1 << i)) != 0);
            }
        }
        return bits;
    }

    /**
     * Converts a BitSet to a byte array.
     *
     * @param bits the BitSet to be converted
     * @return a byte array representing the BitSet
     */
    private byte[] toByteArray(BitSet bits) {
        int byteLength = (bits.length() + 7) / 8;
        byte[] bytes = new byte[byteLength];
        for (int i = 0; i < bits.length(); i++) {
            if (bits.get(i)) {
                bytes[i / 8] |= (byte) (1 << (7 - (i % 8)));
            }
        }
        return bytes;
    }
}