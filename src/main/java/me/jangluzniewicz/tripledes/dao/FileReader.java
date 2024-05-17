package me.jangluzniewicz.tripledes.dao;

import java.io.*;

/**
 * The FileReader class implements the FileReaderInterface and provides methods for
 * reading from and writing to files.
 */
public class FileReader implements FileReaderInterface {
    private static final int BUFFER_SIZE = 4096;  // Buffer size for reading and writing operations

    /**
     * Reads the content of a file located at the specified path.
     *
     * @param path the path to the file to be read
     * @return a byte array containing the content of the file
     * @throws IOException if an I/O error occurs
     */
    @Override
    public byte[] read(String path) throws IOException {
        // Use try-with-resources to ensure streams are closed properly
        try (InputStream inputStream = new FileInputStream(path);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            // Read the file content into the buffer and write it to the output stream
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            // Return the byte array containing the file content
            return outputStream.toByteArray();
        }
    }

    /**
     * Writes the given byte array content to a file located at the specified path.
     *
     * @param path the path to the file to be written
     * @param content the byte array content to be written to the file
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void write(String path, byte[] content) throws IOException {
        // Use try-with-resources to ensure the output stream is closed properly
        try (OutputStream outputStream = new FileOutputStream(path)) {
            // Write the byte array content to the file
            outputStream.write(content);
        }
    }
}