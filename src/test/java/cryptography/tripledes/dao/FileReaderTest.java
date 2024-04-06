package cryptography.tripledes.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileReaderTest {
    FileReader fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReader();
    }

    @Test
    void testRead() {
        try {
            byte[] content = fileReader.read("src/test/java/cryptography/tripledes/dao/java.png");
            assertNotNull(content);
            assertTrue(content.length > 0);
        } catch (Exception e) {
            fail("Error reading file: " + e.getMessage());
        }
    }

    @Test
    void testWrite() {
        try {
            byte[] content = fileReader.read("src/test/java/cryptography/tripledes/dao/java.png");
            fileReader.write("src/test/java/cryptography/tripledes/dao/java_copy.png", content);
            byte[] contentCopy = fileReader.read("src/test/java/cryptography/tripledes/dao/java_copy.png");
            assertArrayEquals(content, contentCopy);
            File fileCopy = new File("src/test/java/cryptography/tripledes/dao/java_copy.png");
            if (fileCopy.exists()) {
                fileCopy.delete();
            }
        } catch (Exception e) {
            fail("Error writing file: " + e.getMessage());
        }
    }
}