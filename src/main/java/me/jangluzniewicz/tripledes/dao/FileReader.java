package me.jangluzniewicz.tripledes.dao;

import java.io.*;

public class FileReader implements FileReaderInterface {
    private static final int BUFFER_SIZE = 4096;

    @Override
    public byte[] read(String path) throws IOException {
        try (InputStream inputStream = new FileInputStream(path);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return outputStream.toByteArray();
        }
    }

    @Override
    public void write(String path, byte[] content) throws IOException {
        try (OutputStream outputStream = new FileOutputStream(path)) {
            outputStream.write(content);
        }
    }
}
