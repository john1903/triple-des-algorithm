package me.jangluzniewicz.tripledes.dao;

import java.io.*;

public class FileReader implements FileReaderInterface {
    @Override
    public byte[] read(String path) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (InputStream inputStream = new FileInputStream(path)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new IOException("Error reading file: " + e.getMessage());
        }
        return outputStream.toByteArray();
    }

    @Override
    public void write(String path, byte[] content) throws IOException {
        try (OutputStream outputStream = new FileOutputStream(path)) {
            outputStream.write(content);
        } catch (IOException e) {
            throw new IOException("Error writing file: " + e.getMessage());
        }
    }
}