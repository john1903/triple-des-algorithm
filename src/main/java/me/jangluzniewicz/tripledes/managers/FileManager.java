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

    private BitSet fromByteArray(byte[] bytes) {
        BitSet bits = new BitSet(bytes.length * 8);
        for (int i = 0; i < bytes.length * 8; i++) {
            if ((bytes[i / 8] & (1 << (7 - (i % 8)))) > 0) {
                bits.set(i);
            }
        }
        return bits;
    }

    private byte[] toByteArray(BitSet bits) {
        byte[] bytes = new byte[bits.length() / 8 + (bits.length() % 8 == 0 ? 0 : 1)];
        for (int i = 0; i < bits.length(); i++) {
            if (bits.get(i)) {
                bytes[i / 8] |= (byte) (1 << (7 - (i % 8)));
            }
        }
        return bytes;
    }
}
