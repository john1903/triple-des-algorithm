package me.jangluzniewicz.tripledes.managers;

import me.jangluzniewicz.tripledes.dao.FileReaderInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.BitSet;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FileManagerTest {
    private FileReaderInterface fileReader;
    private FileManager fileManager;

    @BeforeEach
    void setUp() {
        fileReader = new MockFileReader();
        fileManager = new FileManager(fileReader);
    }

    @Test
    void testRead() throws Exception {
        String filePath = "dummyPath";
        byte[] expectedBytes = { (byte) 0b11001100, (byte) 0b10101010 };

        BitSet expectedBits = fromByteArray(expectedBytes);
        BitSet actualBits = fileManager.read(filePath);

        assertEquals(expectedBits, actualBits);
    }

    @Test
    void testWrite() throws Exception {
        String filePath = "dummyPath";
        byte[] expectedBytes = { (byte) 0b11001100, (byte) 0b10101010 };
        BitSet bits = fromByteArray(expectedBytes);

        fileManager.write(filePath, bits);

        byte[] actualBytes = ((MockFileReader) fileReader).getWrittenBytes();
        assertArrayEquals(expectedBytes, actualBytes);
    }

    @Test
    void testWriteByteArray() throws Exception {
        String filePath = "dummyPath";
        byte[] expectedBytes = { (byte) 0b11001100, (byte) 0b10101010 };

        fileManager.write(filePath, expectedBytes);

        byte[] actualBytes = ((MockFileReader) fileReader).getWrittenBytes();
        assertArrayEquals(expectedBytes, actualBytes);
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

    private static class MockFileReader implements FileReaderInterface {
        private byte[] writtenBytes;

        @Override
        public byte[] read(String path) {
            return new byte[] { (byte) 0b11001100, (byte) 0b10101010 };
        }

        @Override
        public void write(String path, byte[] content) {
            this.writtenBytes = content;
        }

        public byte[] getWrittenBytes() {
            return writtenBytes;
        }
    }
}