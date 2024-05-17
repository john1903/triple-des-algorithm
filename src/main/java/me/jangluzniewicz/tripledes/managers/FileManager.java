package me.jangluzniewicz.tripledes.managers;

import me.jangluzniewicz.tripledes.dao.FileReaderInterface;

import java.util.BitSet;

public class FileManager {
    private final FileReaderInterface fileReader;

    public FileManager(FileReaderInterface fileReader) {
        this.fileReader = fileReader;
    }

    public BitSet read(String path) throws Exception {
        byte[] data = fileReader.read(path);
        return fromByteArray(data);
    }

    public void write(String path, BitSet content) throws Exception {
        byte[] data = toByteArray(content);
        fileReader.write(path, data);
    }

    public void write(String path, byte[] content) throws Exception {
        fileReader.write(path, content);
    }

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
