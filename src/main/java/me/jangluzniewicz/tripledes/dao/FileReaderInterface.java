package me.jangluzniewicz.tripledes.dao;

import java.io.IOException;

/**
 * The FileReaderInterface defines the methods for reading from and writing to files.
 */
public interface FileReaderInterface {

    /**
     * Reads the content of a file located at the specified path.
     *
     * @param path the path to the file to be read
     * @return a byte array containing the content of the file
     * @throws IOException if an I/O error occurs
     */
    byte[] read(String path) throws IOException;

    /**
     * Writes the given byte array content to a file located at the specified path.
     *
     * @param path the path to the file to be written
     * @param content the byte array content to be written to the file
     * @throws IOException if an I/O error occurs
     */
    void write(String path, byte[] content) throws IOException;
}