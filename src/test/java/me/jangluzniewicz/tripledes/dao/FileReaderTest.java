package me.jangluzniewicz.tripledes.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileReaderTest {
    private FileReader fileReader;
    private File tempFile;

    @BeforeEach
    void setUp() throws IOException {
        fileReader = new FileReader();
        tempFile = File.createTempFile("testfile", ".tmp");
    }

    @AfterEach
    void tearDown() {
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    void testRead() throws IOException {
        byte[] expectedContent = "Hello, World!".getBytes();
        Files.write(tempFile.toPath(), expectedContent);

        byte[] actualContent = fileReader.read(tempFile.getAbsolutePath());

        assertArrayEquals(expectedContent, actualContent);
    }

    @Test
    void testWrite() throws IOException {
        byte[] content = "Hello, World!".getBytes();

        fileReader.write(tempFile.getAbsolutePath(), content);

        byte[] actualContent = Files.readAllBytes(tempFile.toPath());

        assertArrayEquals(content, actualContent);
    }

    @Test
    void testReadNonExistentFile() {
        assertThrows(IOException.class, () -> {
            fileReader.read("non_existent_file.tmp");
        });
    }

    @Test
    void testWriteToNonWritableLocation() {
        String path = "/non_writable_location/testfile.tmp";
        byte[] content = "Hello, World!".getBytes();

        assertThrows(IOException.class, () -> {
            fileReader.write(path, content);
        });
    }
}